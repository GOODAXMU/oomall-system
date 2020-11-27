package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.external.service.*;
import cn.edu.xmu.oomall.external.service.IShipmentService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import org.apache.commons.lang3.concurrent.Computable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Service
public class NormalOrderService {

	@Autowired
	private ServiceFactory serviceFactory;

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private ICustomerService customerService;
	private IShopService shopService;
	private IInventoryService inventoryService;
	private IFreightService freightService;
	private IDiscountService discountService;
	private IRebateService rebateService;
	private IShareService shareService;
	private IActivityService activityService;

	private final static String TOPIC = "order";

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		inventoryService = (IInventoryService) serviceFactory.get(IInventoryService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		discountService = (IDiscountService) serviceFactory.get(IDiscountService.class);
		rebateService = (IRebateService) serviceFactory.get(IRebateService.class);
		shareService = (IShareService) serviceFactory.get(IShareService.class);
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
	}

	public Order createOrder(Order order) throws OrderModuleException, ExecutionException, InterruptedException {
		// 校验优惠活动与优惠卷
		CompletableFuture<Map<Long, Long>> activity
				= activityService.validateActivityAsynchronous(order.getOrderItems(), order.getCouponId());

		// 扣库存
		List<OrderItem> orderItems = inventoryService.modifyInventory(order.getOrderItems());
		if (orderItems.size() == 0) {
			throw new OrderModuleException(ResponseStatus.OUT_OF_STOCK);
		}
		order.setOrderItems(orderItems);

		// 获取可用的优惠活动并设置
		Map<Long, Long> sku2Activity = activity.get();
		for (OrderItem oi : order.getOrderItems()) {
			Long act = sku2Activity.get(oi.getSkuId());
			oi.setCouponActivityId(act);
		}

		// 异步计算折扣
		CompletableFuture<Map<Long, Long>> discount
				= discountService.calcDiscountAsynchronous(
				order.getOrderItems());

		// 设置订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			throw new OrderModuleException(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer);

		// 添加分享记录
		for (OrderItem oi : order.getOrderItems()) {
			Long beSharedId = shareService.getBeSharedId(order.getCustomer().getId(), oi.getSkuId());
			if (beSharedId != null) {
				shareService.sendShareMessage(beSharedId, oi.getId());
				oi.setBeShareId(beSharedId);
			}
		}

		// 获取并设置折扣
		Map<Long, Long> sku2Discount = discount.get();
		for (OrderItem oi : order.getOrderItems()) {
			oi.setDiscount(sku2Discount.get(oi.getSkuId()));
		}

		// 根据sku所属商铺划分orderItem并创建子订单
		Map<Shop, List<OrderItem>> shop2OrderItems =
				shopService.classifySku(order.getOrderItems());
		for (Map.Entry<Shop, List<OrderItem>> e : shop2OrderItems.entrySet()) {
			order.createAndAddSubOrder(e.getKey(), e.getValue());
		}

		// 计算价格
		order.calcAndSetSubOrdersOriginPrice();
		order.calcAndSetParentOrderOriginPrice();

		// 异步计算运费
		Map<Order, CompletableFuture<Long>> freights = new HashMap<>(order.getSubOrders().size() + 1);
		for (Order subOrder : order.getSubOrders()) {
			CompletableFuture<Long> cf = freightService.calcFreightPriceAsynchronous(subOrder.getOrderItems());
			freights.put(subOrder, cf);
		}

		// 使用返点
		Integer rebate = order.calcAndGetRebate();
		rebate = rebateService.useRebate(order.getCustomer().getId(), rebate);
		order.setRebateNum(rebate);

		// 获取并设置运费
		for (Order subOrder : order.getSubOrders()) {
			Long f = freights.get(order).get();
			subOrder.setFreightPrice(f);
		}

		// 设置订单状态
		order.setOrderStatus(OrderStatus.NEW, true);

		// 分配订单流水号
		for (Order subOrder : order.getSubOrders()) {
			String orderSn = subOrder.createAndGetOrderSn();
		}

		// 订单写入消息队列
		sender.sendAsynchronous(order, TOPIC);

		return order;
	}
}

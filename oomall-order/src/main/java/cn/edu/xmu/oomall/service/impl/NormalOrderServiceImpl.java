package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.external.service.*;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.service.IOrderService;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NormalOrderServiceImpl implements IOrderService {

	@Autowired
	private ServiceFactory serviceFactory;

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private ICustomerService customerService;
	private IShopService shopService;
	private IInventoryService inventoryService;
	private IFreightService freightService;
	private IDiscountService discountService;
	private IActivityService activityService;

	private final static String TOPIC = "order";

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		inventoryService = (IInventoryService) serviceFactory.get(IInventoryService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		discountService = (IDiscountService) serviceFactory.get(IDiscountService.class);
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
	}

	@Override
	public Reply<Order> createOrder(Order order) throws ExecutionException, InterruptedException {
		// 获取订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		// 扣库存
		List<OrderItem> orderItems = inventoryService.modifyInventory(order.getOrderItems());
		if (orderItems.size() == 0) {
			return new Reply<>(ResponseStatus.OUT_OF_STOCK);
		}
		order.setOrderItems(orderItems);

		// 异步校验优惠活动与优惠卷
		CompletableFuture<Map<Long, Long>> activity
				= activityService.validateActivityAsynchronous(order.getOrderItems(), order.getCouponId());

		// 根据sku所属商铺划分orderItem并创建子订单, 设置orderItem的price字段
		Map<Shop, List<OrderItem>> shop2OrderItems =
				shopService.classifySku(order.getOrderItems());
		for (Map.Entry<Shop, List<OrderItem>> e : shop2OrderItems.entrySet()) {
			order.createAndAddSubOrder(e.getKey(), e.getValue());
		}

		// 设置订单客户
		order.setCustomer(customer, true);

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

		// 分配订单流水号
		order.createAndGetOrderSn();
		for (Order subOrder : order.getSubOrders()) {
			subOrder.createAndGetOrderSn();
		}

		// 异步计算运费
		Map<String, CompletableFuture<Long>> freights = new HashMap<>(order.getSubOrders().size() + 1);
		for (Order subOrder : order.getSubOrders()) {
			CompletableFuture<Long> cf = freightService.calcFreightPriceAsynchronous(subOrder.getOrderItems(), subOrder.getRegionId(), false);
			freights.put(subOrder.getOrderSn(), cf);
		}

		// 获取并设置折扣
		Map<Long, Long> sku2Discount = discount.get();
		for (OrderItem oi : order.getOrderItems()) {
			oi.setDiscount(sku2Discount.get(oi.getSkuId()));
		}
		order.calcAndSetSubOrderDiscountPrice();
		order.calcAndSetParentDiscountPrice();

		// 计算价格
		order.calcAndSetSubOrdersOriginPrice();
		order.calcAndSetParentOrderOriginPrice();

		// 设置订单状态和类型
		order.setOrderStatus(OrderStatus.NEW, true);
		order.setOrderType(OrderType.NORMAL, true);

		// 获取并设置运费
		for (Order subOrder : order.getSubOrders()) {
			Long f = freights.get(subOrder.getOrderSn()).get();
			subOrder.setFreightPrice(f);
		}

		// 单店不拆单
		if (order.getSubOrders().size() == 1) {
			order = order.getSubOrders().get(0);
			order.setPid(0L);
		}

		// 订单写入消息队列
		sender.sendAsynchronous(order.toOrderDto(), TOPIC);

		return new Reply<>(order);
	}
}

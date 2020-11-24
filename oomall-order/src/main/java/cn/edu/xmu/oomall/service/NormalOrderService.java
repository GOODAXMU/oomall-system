package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.OrderModuleStatus;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.external.service.*;
import cn.edu.xmu.oomall.external.service.IShipmentService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
	private OrderDao orderDao;

	@Autowired
	private ServiceFactory serviceFactory;

	private ICustomerService customerService;
	private IShopService shopService;
	private IInventoryService inventoryService;
	private IFreightService freightService;
	private IDiscountService discountService;
	private IRebateService rebateService;
	private IShipmentService shipmentService;
	private IShareService shareService;

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		inventoryService = (IInventoryService) serviceFactory.get(IInventoryService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		discountService = (IDiscountService) serviceFactory.get(IDiscountService.class);
		rebateService = (IRebateService) serviceFactory.get(IRebateService.class);
		shipmentService = (IShipmentService) serviceFactory.get(IShipmentService.class);
		shareService = (IShareService) serviceFactory.get(IShareService.class);
	}

	public Order createOrder(Order order) throws OrderModuleException, ExecutionException, InterruptedException {
		// 扣库存
		List<OrderItem> orderItems = inventoryService.modifyInventory(order.getOrderItems());
		if (orderItems.size() == 0) {
			throw new OrderModuleException(OrderModuleStatus.OUT_OF_STOCK);
		}
		order.setOrderItems(orderItems);

		// 异步计算折扣
		CompletableFuture<List<OrderItem>> discount
				= discountService.calcDiscountAsynchronous(
				order.getOrderItems());

		// 设置订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			throw new OrderModuleException(OrderModuleStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer);

		// 添加分享记录
		for (OrderItem oi : order.getOrderItems()) {
			Long beSharedId = shareService.getBeSharedId(order.getCustomer().getId(), oi.getSkuId());
			if (beSharedId != null) {
				shareService.sendShareMessage(beSharedId, oi.getId());
				oi.setBeSharedId(beSharedId);
			}
		}

		// 获取并设置折扣
		order.setOrderItems(discount.get());

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

		// 创建并设置快递消息
		for (Order subOrder : order.getSubOrders()) {
			// 分配订单流水号
			String orderSn = subOrder.createAndGetOrderSn();
			Map<String, Object> shipment = shipmentService.createShipment(orderSn);
			subOrder.setShipmentSn(shipmentService.getShipmentSn(shipment));
			subOrder.setConfirmTime(shipmentService.getConfirmTime(shipment));
		}

		// 订单写入数据库
		order = orderDao.saveOrder(order);

		return order;
	}
}

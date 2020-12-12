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
import cn.edu.xmu.oomall.util.OrderSnGenerator;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
	private IGoodsService goodsService;
	private IShopService shopService;
	private IInventoryService inventoryService;
	private IFreightService freightService;
	private IDiscountService discountService;
	private IActivityService activityService;

	private final static String TOPIC = "order";

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		goodsService = (IGoodsService) serviceFactory.get(IGoodsService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		inventoryService = (IInventoryService) serviceFactory.get(IInventoryService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		discountService = (IDiscountService) serviceFactory.get(IDiscountService.class);
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
	}

	@Override
	public Reply<String> createOrder(Order order) throws ExecutionException, InterruptedException {
		// 扣库存
		List<OrderItem> orderItems = inventoryService.modifyInventory(order.getOrderItems(), OrderType.NORMAL.value());
		if (orderItems.size() < order.getOrderItems().size()) {
			return new Reply<>(ResponseStatus.OUT_OF_STOCK);
		}

		// 异步校验优惠活动与优惠卷
		CompletableFuture<Map<Long, Long>> activity
				= activityService.validateActivityAsynchronous(order.getOrderItems(), order.getCouponId());

		// 异步计算运费
		CompletableFuture<Long> cf = freightService.calcFreightPriceAsynchronous(order.getOrderItems(), order.getRegionId(), false);

		// 设置订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer, false);

		// 设置商铺
		Shop shop = shopService.getShop(order.getOrderItems().get(0).getSkuId());
		if (shop == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setShop(shop);

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
		order.setOrderSn(OrderSnGenerator.createAndGetOrderSn());

		// 设置orderItem的sku信息
		goodsService.setSkuInformation(order.getOrderItems(), OrderType.NORMAL.value());

		// 计算价格
		order.calcAndSetOriginPrice();

		// 设置订单状态和类型
		order.setOrderStatus(OrderStatus.NEW, false);
		order.setOrderType(OrderType.NORMAL, false);

		// 获取并设置折扣
		Map<Long, Long> sku2Discount = discount.get();
		for (OrderItem oi : order.getOrderItems()) {
			oi.setDiscount(sku2Discount.get(oi.getSkuId()));
		}
		order.calcAndSetDiscountPrice();

		// 获取并设置运费
		order.setFreightPrice(cf.get());

		// 订单写入消息队列
		sender.sendAsynchronous(order.toOrderDto(), TOPIC);

		return new Reply<>(order.getOrderSn());
	}
}

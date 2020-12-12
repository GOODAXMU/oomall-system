package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.external.service.*;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.service.IOrderService;
import cn.edu.xmu.oomall.util.OrderSnGenerator;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Service
public class PresaleOrderServiceImpl implements IOrderService {


	@Autowired
	private ServiceFactory serviceFactory;

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private IInventoryService inventoryService;
	private ICustomerService customerService;
	private IShopService shopService;
	private IFreightService freightService;
	private IGoodsService goodsService;

	private static final String TOPIC = "order";

	@PostConstruct
	public void init() {
		inventoryService = (IInventoryService) serviceFactory.get(IInventoryService.class);
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		goodsService = (IGoodsService) serviceFactory.get(IGoodsService.class);
	}

	@Override
	public Reply<String> createOrder(Order order) throws ExecutionException, InterruptedException {
		// 扣库存
		List<OrderItem> r = inventoryService.modifyInventory(order.getOrderItems(), OrderType.PRESALE.value());
		if (r == null || r.isEmpty()) {
			return new Reply<>(ResponseStatus.OUT_OF_STOCK);
		}

		// 设置订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer);

		// 设置商铺
		Shop shop = shopService.getShop(order.getOrderItems().get(0).getSkuId());
		if (shop == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setShop(shop);

		// 异步计算运费
		CompletableFuture<Long> freights = freightService.calcFreightPriceAsynchronous(order.getOrderItems(), order.getRegionId(), false);

		// 设置价格和名称
		goodsService.setSkuInformation(order.getOrderItems(), OrderType.PRESALE.value());

		// 设置订单流水号
		order.setOrderSn(OrderSnGenerator.createAndGetOrderSn());

		// 设置价格
		order.calcAndSetOriginPrice();

		// 设置订单状态和类型
		order.setOrderStatus(OrderStatus.NEW, false);
		order.setOrderType(OrderType.PRESALE, false);

		// 获取并设置运费
		order.setFreightPrice(freights.get());

		sender.sendAsynchronous(order.toOrderDto(), TOPIC);

		return new Reply<>(order.getOrderSn());
	}
}

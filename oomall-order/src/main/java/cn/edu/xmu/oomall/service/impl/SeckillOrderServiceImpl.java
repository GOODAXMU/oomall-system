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
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Service
public class SeckillOrderServiceImpl implements IOrderService {

	@Autowired
	private ServiceFactory serviceFactory;

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private ISeckillService seckillService;
	private ICustomerService customerService;
	private IShopService shopService;
	private IFreightService freightService;
	private IFlashSaleService flashSaleService;

	private static final String TOPIC = "order";

	@PostConstruct
	public void init() {
		seckillService = (ISeckillService) serviceFactory.get(ISeckillService.class);
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		freightService = (IFreightService) serviceFactory.get(IFreightService.class);
		flashSaleService = (IFlashSaleService) serviceFactory.get(IFlashSaleService.class);
	}

	@Override
	public Reply<Order> createOrder(Order order) throws ExecutionException, InterruptedException {
		OrderItem orderItem = order.getOrderItems().get(0);

		// 设置订单的客户
		Long customerId = order.getCustomer().getId();
		Customer customer = customerService.getCustomer(customerId);
		if (customer == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer);

		// 设置商铺
		Shop shop = shopService.getShop(orderItem.getSkuId());
		if (shop == null) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}
		order.setCustomer(customer);

		// 扣库存
		Boolean r = seckillService.deductInventory(orderItem.getSkuId(), orderItem.getQuantity());
		if (!r) {
			return new Reply<>(ResponseStatus.OUT_OF_STOCK);
		}

		// 异步计算运费
		CompletableFuture<Long> freights = freightService.calcFreightPriceAsynchronous(order.getOrderItems());

		// 获取价格
		Long price = flashSaleService.getPrice(orderItem.getSkuId());
		orderItem.setPrice(price);

		// 设置订单流水号
		order.createAndGetOrderSn();

		// 计算价格
		order.calcAndSetParentOrderOriginPrice();

		// 设置订单状态和类型
		order.setOrderStatus(OrderStatus.NEW, false);
		order.setOrderType(OrderType.NORMAL, false);

		// 获取并设置运费
		order.setFreightPrice(freights.get());

		sender.sendAsynchronous(order.toOrderDto(), TOPIC);

		return new Reply<>(order);
	}
}

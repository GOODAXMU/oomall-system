package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.external.service.IFlashSaleService;
import cn.edu.xmu.oomall.external.service.IShareService;
import cn.edu.xmu.oomall.external.service.IShopService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-25
 */
@Service
public class CustomerOrderService {

	@Autowired
	private ServiceFactory serviceFactory;

	@Autowired
	private OrderDao orderDao;

	private ICustomerService customerService;
	private IShopService shopService;
	private IFlashSaleService flashSaleService;
	private IShareService shareService;

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		flashSaleService = (IFlashSaleService) serviceFactory.get(IFlashSaleService.class);
		shareService = (IShareService) serviceFactory.get(IShareService.class);
	}

	public Reply<List<Order>> getOrders(
			Long customerId, String orderSn, Integer state,
			LocalDateTime beginTime,
			LocalDateTime endTime,
			PageInfo pageInfo, Boolean withParent) {
		return orderDao.getOrders(customerId, orderSn, state, beginTime, endTime, pageInfo, withParent);
	}

	public Reply<Order> getOrderByIdAndCustomerId(Long id, Long customerId) {
		Reply<Order> r = orderDao.getOrderByIdAndCustomerId(id, customerId);
		Order o = r.getData();
		if (o == null) {
			return r;
		}

		// 设置用户和商铺
		Customer customer = customerService.getCustomer(o.getCustomer().getId());
		Shop shop = shopService.getShop(o.getShop().getId());
		o.setCustomer(customer);
		o.setShop(shop);

		return new Reply<>(o);
	}

	public Reply<Object> updateOrderDeliveryInformation(Order o) {
		return orderDao.updateOrderDeliveryInformation(o);
	}

	public Reply<Object> deleteOrCancelSelfOrder(Long id, Long customerId) {
		int s = orderDao.getOrderStateByIdAndCustomerId(id, customerId);

		if (s == OrderStatus.RECEIVED.value()) {
			return orderDao.deleteSelfOrder(id);
		} else if (s < OrderStatus.DELIVERED.value()) {
			return orderDao.updateOrderState(id, OrderStatus.CANCELED.value());
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> confirmOrder(Long id, Long customerId) {
		Reply<Order> r = orderDao.getOrderByIdAndCustomerId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		Order order = r.getData();

		// todo 沟通分享模块接口

		// 添加分享记录
		for (OrderItem oi : order.getOrderItems()) {
			Long beSharedId = oi.getBeShareId();
			if (beSharedId != null) {
				shareService.sendShareMessage(oi);
				oi.setBeShareId(beSharedId);
			}
		}
		return orderDao.confirmOrder(id);
	}

	public Reply<Object> groupon2Normal(Long id, Long customerId) {
		return orderDao.updateOrderType(id, customerId);
	}

	public Long getSeckillId(Long skuId) {
		return flashSaleService.getSeckillId(skuId);
	}
}

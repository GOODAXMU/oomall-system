package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.external.service.ICustomerService;
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
public class OrderService {

	@Autowired
	private ServiceFactory serviceFactory;

	@Autowired
	private OrderDao orderDao;

	private ICustomerService customerService;
	private IShopService shopService;

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
	}

	public Reply<List<Order>> getOrders(String orderSn, Integer state,
										LocalDateTime beginTime,
										LocalDateTime endTime,
										PageInfo pageInfo, Boolean withParent) {
		return orderDao.getOrders(orderSn, state, beginTime, endTime, pageInfo, withParent);
	}

	public Reply<Order> getOrderById(Long id) {
		Reply<Order> r = orderDao.getOrderById(id);
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

	public Reply<Object> deleteOrCancelSelfOrder(Long id) {
		int s = orderDao.getOrderStateById(id);

		if (s == OrderStatus.COMPLETE.value()) {
			return orderDao.deleteSelfOrder(id);
		} else if (s < OrderStatus.DELIVERED.value()) {
			return orderDao.updateOrderState(id, OrderStatus.CANCELED.value());
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> confirmOrder(Long id) {
		return orderDao.confirmOrder(id);
	}

	public Reply<Object> groupon2Normal(Long id) {
		return orderDao.updateOrderType(id);
	}
}

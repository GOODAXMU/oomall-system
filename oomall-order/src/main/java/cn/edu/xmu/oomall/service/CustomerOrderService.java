package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.DbOrderStatus;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.OrderDao;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.external.service.*;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.Reply;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	private IActivityService activityService;

	@PostConstruct
	public void init() {
		customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
		flashSaleService = (IFlashSaleService) serviceFactory.get(IFlashSaleService.class);
		shareService = (IShareService) serviceFactory.get(IShareService.class);
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
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
		Reply<OrderPo> r = orderDao.getOrderPoByIdAndCustomerId(id, customerId);

		if (!r.isOk()) {
			return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
		}

		OrderPo order = r.getData();

		if (order.getBeDeleted() != null && order.getBeDeleted() == DbOrderStatus.BE_DELETED.value()) {
			return new Reply<>(HttpStatus.NOT_FOUND, ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		if (order.getState() >= OrderStatus.COMPLETED.value()) {
			return orderDao.deleteSelfOrder(id);
		} else if (order.getState() <= OrderStatus.TO_BE_RECEIVED.value()) {
			if (order.getSubState() == OrderStatus.DELIVERED.value()) {
				return new Reply<>(ResponseStatus.ORDER_FORBID);
			}
			return orderDao.updateOrderState(id, OrderStatus.CANCELED.value(), null);
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> confirmOrder(Long id, Long customerId) {
		Reply<OrderPo> r = orderDao.getOrderPoByIdAndCustomerId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
		}

		OrderPo po = r.getData();
		if (po == null || (po.getBeDeleted() != null && po.getBeDeleted() == DbOrderStatus.BE_DELETED.value())) {
			return new Reply<>(HttpStatus.NOT_FOUND, ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		return orderDao.confirmOrder(id);
	}

	public Reply<Object> groupon2Normal(Long id, Long customerId) {
		Reply<OrderPo> r = orderDao.getOrderPoByIdAndCustomerId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
		}

		OrderPo po = r.getData();
		
		return orderDao.groupon2Normal(id, customerId);
	}

	public Long getSeckillId(Long skuId) {
		return flashSaleService.getSeckillId(skuId);
	}

	public Long getGrouponId(Long skuId) {
		return activityService.getGrouponId(skuId);
	}

	public Long getPreSaleId(Long skuId) {
		return activityService.getPreSaleId(skuId);
	}
}

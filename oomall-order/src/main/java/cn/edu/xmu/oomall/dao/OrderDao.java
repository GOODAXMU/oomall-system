package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import cn.edu.xmu.oomall.repository.OrderRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class OrderDao {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	public Order saveOrder(Order order) {
		return order;
	}

	public Reply<List<Order>> getOrders(String orderSn, Integer state,
										LocalDateTime beginTime, LocalDateTime endTime,
										PageInfo pageInfo, Boolean withParent) {

		Page<OrderPo> orderPoPage = orderRepository.findAll(
				SpecificationFactory.get(orderSn, state, beginTime, endTime),
				PageRequest.of(pageInfo.getPage(), pageInfo.getPageSize()));

		pageInfo.calAndSetPagesAndTotal(orderPoPage.getTotalElements());

		List<Order> orders = new ArrayList<>();
		for (OrderPo op : orderPoPage.getContent()) {
			if (op.getPid() != null && op.getPid() == 0 && !withParent) {
				continue;
			}
			orders.add(Order.toOrder(op));
		}

		return new Reply<>(orders);
	}

	public Reply<Order> getOrderById(Long id) {
		Optional<OrderPo> orderPo = orderRepository.findById(id);
		Order o = Order.toOrder(orderPo.orElse(null));
		if (o == null) {
			return new Reply<>(null);
		}

		// 设置订单列表
		List<OrderItemPo> orderItemPos =  orderItemRepository.findByOrderId(o.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItemPo po : orderItemPos) {
			orderItems.add(OrderItem.toOrderItem(po));
		}
		o.setOrderItems(orderItems);

		return new Reply<>(o);
	}

	public Reply<Object> updateOrder(Order o) {
		OrderPo po = OrderPo.toOrderPo(o);
		if (po == null) {
			return new Reply<>(ResponseStatus.INTERNAL_SERVER_ERR);
		}

		int r = orderRepository.update(po);

		if (r <= 0) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> deleteSelfOrder(Long id) {
		int r = orderRepository.deleteSelfOrderByIdAndStateBefore(id, OrderStatus.DELIVERED.value());

		if (r <= 0) {
			return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> confirmOrder(Long id) {
		int r = orderRepository.changeOrderStateWhenStateNotEquals(
				id, OrderStatus.RECEIVED.value(), OrderStatus.FORBID.value());

		if (r <= 0) {
			return new Reply<>(ResponseStatus.ORDER_FORBID);
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}

	public Reply<Object> updateOrderType(Long id) {
		int r = orderRepository.updateGroupon2NormalWhenStateNotEquals(
				id, OrderType.GROUPON.value(), OrderType.NORMAL.value(), OrderStatus.FORBID.value());

		if (r <= 0) {
			return new Reply<>(ResponseStatus.ORDER_FORBID);
		} else {
			return new Reply<>(ResponseStatus.OK);
		}
	}
}

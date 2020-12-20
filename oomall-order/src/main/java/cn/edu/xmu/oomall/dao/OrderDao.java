package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.constant.DbOrderStatus;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-17
 * @modified by Jianheng HUANG, date: 2020-11-27
 * @modified by Jianheng HUANG, date: 2020-11-29
 * @modified by xincong yao, date: 2020-12-3
 * @modified by Jianheng HUANG, date: 2020-12-12
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

    public Reply<List<Order>> getOrders(Long customerId, String orderSn, Integer state,
                                        LocalDateTime beginTime, LocalDateTime endTime,
                                        PageInfo pageInfo, Boolean withParent) {

        Page<OrderPo> orderPoPage = orderRepository.findAll(
                SpecificationFactory.get(customerId, orderSn, state, beginTime, endTime, false),
                PageRequest.of(pageInfo.getJpaPage(), pageInfo.getPageSize()));

        pageInfo.calAndSetPagesAndTotal(orderPoPage.getTotalElements(), orderPoPage.getTotalPages());

        List<Order> orders = new ArrayList<>();
        for (OrderPo op : orderPoPage.getContent()) {
            if (op.getPid() != null && op.getPid() == 0 && !withParent) {
                continue;
            }
            if (op.getBeDeleted() != null && op.getBeDeleted() == DbOrderStatus.BE_DELETED.value()) {
                continue;
            }
            orders.add(Order.toOrder(op));
        }

        return new Reply<>(orders);
    }

    /**
     * @author Jianheng HUANG
     * @date 2020-11-27
     */
    public Reply<Object> getShopOrders(Long shopId, Long customerId,
                                       String orderSn,
                                       LocalDateTime beginTime,
                                       LocalDateTime endTime,
                                       PageInfo pageInfo, Boolean withParent) {

        Page<OrderPo> orderPoPage = orderRepository.findAll(
                SpecificationFactory.get(shopId, customerId, orderSn, beginTime, endTime),
                PageRequest.of(pageInfo.getJpaPage(), pageInfo.getPageSize()));

        pageInfo.calAndSetPagesAndTotal(orderPoPage.getTotalElements(), orderPoPage.getTotalPages());

        List<Order> orders = new ArrayList<>();

        for (OrderPo op : orderPoPage.getContent()) {
            if (op.getPid() != null && op.getPid() == 0 && !withParent) {
                continue;
            }
            orders.add(Order.toOrder(op));
        }

        return new Reply<>(orders);
    }

    public Reply<Order> getOrderByIdAndCustomerId(Long id, Long customerId) {
        Optional<OrderPo> op = orderRepository.findById(id);
        if (op.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        OrderPo opo = op.get();
        if (opo.getBeDeleted() != null && opo.getBeDeleted() == DbOrderStatus.BE_DELETED.value()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (opo.getCustomerId() == null || !opo.getCustomerId().equals(customerId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        Order o = Order.toOrder(opo);

        // 设置订单列表
        List<OrderItemPo> orderItemPos = orderItemRepository.findByOrderId(o.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemPo po : orderItemPos) {
            orderItems.add(OrderItem.toOrderItem(po));
        }
        o.setOrderItems(orderItems);

        return new Reply<>(o);
    }

    /**
     * @author Jianheng HUANG
     * @date 2020-11-29
     */
    public Reply<Order> getShopOrderById(Long shopId, Long id) {

        Optional<OrderPo> orderPo = orderRepository.findById(id);
        if (orderPo.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Order o = Order.toOrder(orderPo.orElse(null));
        if (o.getShop().getId() == null || !o.getShop().getId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        // 设置订单列表
        List<OrderItemPo> orderItemPos = orderItemRepository.findByOrderId(o.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemPo po : orderItemPos) {
            orderItems.add(OrderItem.toOrderItem(po));
        }
        o.setOrderItems(orderItems);

        return new Reply<>(o);
    }

    public Reply<Object> updateOrderDeliveryInformation(Order o) {
        OrderPo po = OrderPo.toOrderPo(o);

        Optional<OrderPo> db = orderRepository.findById(o.getId());
        if (db.isEmpty() || (db.get().getBeDeleted() != null && db.get().getBeDeleted() == DbOrderStatus.BE_DELETED.value())) {
            return new Reply<>(HttpStatus.NOT_FOUND, ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        OrderPo t = db.get();

        if (!t.getCustomerId().equals(o.getCustomer().getId())) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        if (t.getState() < OrderStatus.COMPLETED.value() && t.getSubState() != null && t.getSubState() != OrderStatus.DELIVERED.value()) {
            orderRepository.update(po);
            return new Reply<>();
        }

        return new Reply<>(ResponseStatus.ORDER_FORBID);
    }

    /**
     * @author Jianheng HUANG
     * @date 2020-11-29
     */
    public Reply<Object> addShopOrderMessage(Long shopId, Long id, String message) {

        Optional<OrderPo> orderPo = orderRepository.findById(id);
        if (orderPo.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Order o = Order.toOrder(orderPo.orElse(null));
        if (o.getShop().getId() == null || !o.getShop().getId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        int r = orderRepository.addShopOrderMessage(id, message);
        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    public Reply<Object> deleteSelfOrder(Long id) {
        int r = orderRepository.deleteSelfOrderById(id, DbOrderStatus.BE_DELETED.value());

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return new Reply<>(ResponseStatus.OK);
    }

    public Reply<Object> confirmOrder(Long id) {
        int r = orderRepository.changeOrderStateWhenStateEquals(
                id, OrderStatus.COMPLETED.value(), null,
                OrderStatus.TO_BE_RECEIVED.value(), OrderStatus.DELIVERED.value());

        if (r <= 0) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    /**
     * @author Jianheng HUANG
     * @date 2020-11-29
     */
    public Reply<Object> cancelShopOrder(Long shopId, Long id) {

        Optional<OrderPo> orderPo = orderRepository.findById(id);
        if (orderPo.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Order o = Order.toOrder(orderPo.orElse(null));
        if (o.getShop().getId() == null || !o.getShop().getId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        if (o.getState() == OrderStatus.COMPLETED.value() ||
                o.getState() == OrderStatus.CANCELED.value()) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }

        int r = orderRepository.updateOrderState(id, OrderStatus.CANCELED.value());

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    /**
     * @author Jianheng HUANG
     * @date 2020-11-29
     * @modified 2020-12-12
     */
    public Reply<Object> markShopOrderDelivered(Long shopId, Long id, String shipmentSn) {

        Optional<OrderPo> orderPo = orderRepository.findById(id);
        if (orderPo.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Order o = Order.toOrder(orderPo.orElse(null));
        if (o.getShop().getId() == null || !o.getShop().getId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        if (o.getState()!=OrderStatus.TO_BE_RECEIVED.value()||o.getSubState() != OrderStatus.PAID.value()) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }
        int r = orderRepository.markShopOrderDelievered(id, OrderStatus.DELIVERED.value(), shipmentSn);

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    public Reply<Object> groupon2Normal(Long id, Long customerId) {
        int r = orderRepository.updateGroupon2NormalWhenSubStateEqualsOr(
                id, customerId,
                OrderType.GROUPON.value(), OrderType.NORMAL.value(),
                OrderStatus.GROUPON_THRESHOLD_TO_BE_REACH.value(),
                OrderStatus.GROUPON_THRESHOLD_NOT_REACH.value(),
                OrderStatus.PAID.value()
        );

        if (r <= 0) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        } else {
            return new Reply<>(HttpStatus.CREATED, ResponseStatus.OK);
        }
    }

    public Reply<Object> updateOrderState(Long id, Integer state, Integer subState) {
        int r = orderRepository.updateState(id, state, subState);

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return new Reply<>(ResponseStatus.OK);
    }

    public Reply<OrderPo> getOrderPoByIdAndCustomerId(Long id, Long customerId) {
        Optional<OrderPo> op = orderRepository.findById(id);
        OrderPo po = op.isEmpty() ? null : op.get();
        if (po == null || (po.getBeDeleted() != null && po.getBeDeleted() == DbOrderStatus.BE_DELETED.value())) {
            return new Reply<>(HttpStatus.NOT_FOUND, ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!customerId.equals(po.getCustomerId())) {
            return new Reply<>(HttpStatus.FORBIDDEN, ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        return new Reply<>(po);
    }
}

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
 * @modified by Jianheng HUANG, date: 2020-11-27
 * @modified by Jianheng HUANG, date: 2020-11-29
 * @modified by xincong yao, date: 2020-12-3
 * @modified by Jianheng HUANG, date: 2020-12-12
 * TODO: 店家的权限检查
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
                SpecificationFactory.get(customerId, orderSn, state, beginTime, endTime),
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

        if (orderPoPage.isEmpty()) {
            if (orderSn != null) {
                Page<OrderPo> orderPoPage2 = orderRepository.findAll(
                        SpecificationFactory.get(customerId, orderSn, beginTime, endTime),
                        PageRequest.of(pageInfo.getJpaPage(), pageInfo.getPageSize()));
                if (!orderPoPage2.isEmpty()) {
                    return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
                }
                else {
                    return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
                }

            } else {
                // 严格的customerId和orderSn同时筛选，不另外判断
                return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
            }
        }

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

        int r = orderRepository.updateWhenStateLessThanAndSubStateNotEquals(
                po, OrderStatus.COMPLETED.value(), OrderStatus.DELIVERED.value());

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
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
        int r = orderRepository.deleteSelfOrderById(id);

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

        int r = orderRepository.markShopOrderDelievered(id, OrderStatus.DELIVERED.value(), shipmentSn);

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    public Reply<Object> updateOrderType(Long id, Long customerId) {
        int r = orderRepository.updateGroupon2NormalWhenSubStateEqualsOr(
                id, customerId,
                OrderType.GROUPON.value(), OrderType.NORMAL.value(),
                OrderStatus.GROUPON_THRESHOLD_TO_BE_REACH.value(),
                OrderStatus.GROUPON_THRESHOLD_NOT_REACH.value()
        );

        if (r <= 0) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        } else {
            return new Reply<>(ResponseStatus.OK);
        }
    }

    public Reply<Object> updateOrderState(Long id, Integer state, Integer subState) {
        int r = orderRepository.updateState(id, state, subState);

        if (r <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return new Reply<>(ResponseStatus.OK);
    }

    public OrderPo getOrderPoByIdAndCustomerId(Long id, Long customerId) {
        Optional<OrderPo> op = orderRepository.findById(id);
        OrderPo po = op.isEmpty() ? null : op.get();
        if (po == null || !customerId.equals(po.getCustomerId())) {
            return null;
        }

        return po;
    }
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.external.service.IOrderService;
import cn.edu.xmu.oomall.service.IDubboOrderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 * modified 2020/12/16
 */
@Component
public class SimulateOrderServiceImpl implements IOrderService {

    @DubboReference(version = "${oomall.external.order-service.version}", cache = "false", timeout = 5000, check = false)
    private IDubboOrderService orderService;

    @Override
    public Boolean isCustomerOwnOrder(Long customerId, Long orderId) {
        return orderService.isCustomerOwnOrder(customerId, orderId);
    }

    @Override
    public Boolean isShopOwnOrder(Long shopId, Long orderId) {
        return orderService.isShopOwnOrder(shopId, orderId);
    }

    @Override
    public Boolean orderCanBePaid(Long id) {
        return orderService.orderCanBePaid(id);
    }

    @Override
    public void checkOrderPaid(Long id, Long amount) {
        orderService.checkOrderPaid(id, amount);
    }

    @Override
    public Long getOrderPresaleDeposit(Long id) {
        return orderService.getOrderPresaleDeposit(id);
    }

    @Override
    public Long getCustomerIdByOrderId(Long id) {
//        return orderService.;
        return 0L;
    }

    @Override
    public OrderItemDto getOrderItem(Long orderItemId) {
        return orderService.getOrderItem(orderItemId);
    }
}

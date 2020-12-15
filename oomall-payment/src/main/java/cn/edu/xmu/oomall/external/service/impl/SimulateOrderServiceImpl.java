package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.external.service.IOrderService;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 */
@Component
public class SimulateOrderServiceImpl implements IOrderService {

    @Override
    public Boolean isCustomerOwnOrder(Long customerId, Long orderId) {
        return true;
    }

    @Override
    public Boolean isShopOwnOrder(Long shopId, Long orderId) {
        return true;
    }

    @Override
    public Boolean orderCanBePaid(Long id) {
        return true;
    }

    @Override
    public void checkOrderPaid(Long id, Long amount) {
        /* do nothing */
    }

    @Override
    public Long getOrderPresaleDeposit(Long id) {
        return 0L;
    }

    @Override
    public Long getCustomerIdByOrderId(Long id) {
        return 0L;
    }

    @Override
    public OrderItemDto getOrderItem(Long orderItemId) {
        return new OrderItemDto();
    }
}

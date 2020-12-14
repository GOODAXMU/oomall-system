package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.dto.OrderItemDto;

import java.util.List;

/**
 * @author Wang Zhizhou
 * create 2020/11/28
 * modified 2020/12/11
 */
public interface IOrderService {

    /**
     * 检查客户是否拥有该订单
     * @param orderId    订单id
     * @param customerId 顾客id
     * @return  校验确认
     */
    Boolean isCustomerOwnOrder(Long customerId, Long orderId);

    /**
     * 检查商店是否拥有该订单
     * @param shopId    商店id
     * @param orderId   订单id
     * @return
     */
    Boolean isShopOwnOrder(Long shopId, Long orderId);


    /**
     * 查看订单是否可以进行支付
     * @param id 订单id
     * @return
     */
    Boolean orderCanBePaid(Long id);

    /**
     * 根据已完成的支付数额修改订单状态
     * @param id 订单id
     * @param amount 已支付金额
     */
    void checkOrderPaid(Long id, Long amount);

    /**
     * 获取订单的定金价格(用于筛选可以被返款的部分)
     * @param id 订单id
     * @return  定金价格, 非预售返回 -1
     */
    Long getOrderPresaleDeposit(Long id);

    /**
     * 获取订单的顾客id
     * @param id 订单id
     * @return
     */
    Long getCustomerIdByOrderId(Long id);

    /**
     * 获取orderItemid
     * @param orderItemId
     * @return
     */
    OrderItemDto getOrderItem(Long orderItemId);
}

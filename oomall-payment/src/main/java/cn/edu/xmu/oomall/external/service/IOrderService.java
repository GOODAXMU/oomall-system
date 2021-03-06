package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.dto.OrderItemDto;

import java.util.List;

/**
 * @author Wang Zhizhou
 * create 2020/11/28
 * modified 2020/12/18
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
     * 获得订单当前状态应该支付的金额
     * @param customerId    支付顾客id
     * @param orderId       支付订单id
     * @return  null    ->  不存在该id的订单 (资源不存在)
     *         -1       ->  用户与订单不匹配 (资源操作不匹配)
     *          0       ->  订单不可支付 (订单状态禁止)
     *         > 0      ->  可支付金额 (正常支付)
     */
    Long priceOrderCanBePaid(Long customerId, Long orderId);

    /**
     * 检查商店是否拥有该订单
     * @param shopId    商店id
     * @param orderId   订单id
     * @return
     */
    Boolean isShopOwnOrder(Long shopId, Long orderId);

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

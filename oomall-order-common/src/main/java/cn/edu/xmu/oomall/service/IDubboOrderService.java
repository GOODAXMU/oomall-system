package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.*;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-30
 */
public interface IDubboOrderService {

	/**
	 * 获取用于提交售后单的订单信息
	 * @param orderItemId
	 * @return
	 */
	AfterSaleDto getAfterSaleByOrderItemId(Long orderItemId);

	/**
	 * 商铺是否拥有某个order
	 * @param shopId
	 * @param orderId
	 * @return
	 */
	Boolean isShopOwnOrder(Long shopId, Long orderId);

	/**
	 * 用户是否拥有某个order
	 * @param customerId
	 * @param orderId
	 * @return
	 */
	Boolean isCustomerOwnOrder(Long customerId, Long orderId);

	/**
	 * 用户是否拥有某个orderItem
	 * @param customerId
	 * @param orderItemId
	 * @return
	 */
	Boolean isCustomerOwnOrderItem(Long customerId, Long orderItemId);

	/**
	 * 获取订单项信息
	 * @param orderItemId
	 * @return
	 */
	OrderItemDto getOrderItem(Long orderItemId);


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
	 * 返回确认收货后七天到八天且未退款的订单项信息
	 * @return
	 */
	List<EffectiveShareDto> getEffectiveShareRecord();

	/**
	 * 创建换货订单
	 * @param dto 换货订单所需的信息
	 * @return 成功返回0, 错误返回500
	 */
	Integer createExchangeOrder(ExchangeOrderDto dto);

	/**
	 * 修改订单状态
	 * @param dto
	 * @return
	 */
	Boolean changeOrderState(OrderStateDto dto);

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
}

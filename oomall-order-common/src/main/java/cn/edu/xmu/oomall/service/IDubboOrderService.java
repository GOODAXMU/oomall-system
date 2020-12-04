package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.AfterSaleDto;

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
	 * 用户是否拥有某个orderItem
	 * @param customerId
	 * @param orderItemId
	 * @return
	 */
	Boolean isCustomerOwnOrderItem(Long customerId, Long orderItemId);
}

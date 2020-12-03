package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IShareService {

	/**
	 * 发送分享成功的消息
	 * @param orderItem
	 */
	void sendShareMessage(OrderItem orderItem);

	/**
	 * 获取点击记录
	 * @param customerId
	 * @param skuId
	 * @return
	 */
	Long getBeSharedId(Long customerId, Long skuId);
}

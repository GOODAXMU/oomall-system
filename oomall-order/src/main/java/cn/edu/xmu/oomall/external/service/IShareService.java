package cn.edu.xmu.oomall.external.service;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IShareService {

	/**
	 * 发送分享成功的消息
	 * @param beSharedId
	 * @param orderItemId
	 */
	void sendShareMessage(Long beSharedId, Long orderItemId);

	/**
	 * 获取点击记录
	 * @param customerId
	 * @param skuId
	 * @return
	 */
	Long getBeSharedId(Long customerId, Long skuId);
}

package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface IFlashSaleService {

	/**
	 * 当前时刻是否是秒杀商品
	 * @param skuId
	 * @return
	 */
	Long getSeckillId(Long skuId);

	/**
	 * 设置秒杀活动下商品的价格和名称, 不校验时间
	 * @param orderItem
	 * @return
	 */
	void setPriceAndName(OrderItem orderItem);
}

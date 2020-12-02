package cn.edu.xmu.oomall.external.service;

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
	 * 获取秒杀活动下商品的价格, 不校验时间
	 * @param skuId
	 * @return
	 */
	Long getPrice(Long skuId);
}

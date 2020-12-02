package cn.edu.xmu.oomall.external.service;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface ISeckillService {

	/**
	 * 扣库存
	 * @param skuId
	 * @param quantity 准备扣掉的库存数, 正数
	 * @return
	 */
	Boolean deductInventory(Long skuId, Integer quantity, Long seckillId);
}

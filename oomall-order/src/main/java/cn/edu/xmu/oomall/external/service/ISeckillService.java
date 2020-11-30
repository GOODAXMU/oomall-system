package cn.edu.xmu.oomall.external.service;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface ISeckillService {

	/**
	 * 扣库存
	 * @param skuId
	 * @param quantity
	 * @return
	 */
	Boolean deductInventory(Long skuId, Integer quantity);
}

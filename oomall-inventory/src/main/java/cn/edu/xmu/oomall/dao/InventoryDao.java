package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.constant.InventoryResponseStatus;
import cn.edu.xmu.oomall.dto.InventoryResult;
import cn.edu.xmu.oomall.repository.GoodsSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-11
 */
@Component
public class InventoryDao {

	@Autowired
	private GoodsSkuRepository goodsSkuRepository;

	public Map<String, Object> modifyInventory(Long skuId, Integer quantity) {
		/**
		 * 传入的quantity为负表示减少库存
		 * fetchQuantity的第二个参数为正表示获取库存, 也就是从数据库中减少库存
		 */
		int r = goodsSkuRepository.fetchQuantity(skuId, -quantity);
		return r > 0 ? InventoryResult.getResult(InventoryResponseStatus.OK)
				: InventoryResult.getResult(InventoryResponseStatus.OUT_OF_INVENTORY);
	}
}

package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.FlashSaleItemPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author xincong yao
 * @date 2020-10-31
 */
public interface SeckillItemRepository extends
		JpaRepository<FlashSaleItemPo, Long>,
		JpaSpecificationExecutor<FlashSaleItemPo> {

	/**
	 * 根据saleId获取秒杀商品id的集合
	 * @param saleId     秒杀活动id
	 * @param pageable   分页查询
	 * @return           秒杀商品id的集合
	 */
	@Query(value = "SELECT fsi.id FROM FlashSaleItemPo fsi " +
			"WHERE :saleId = fsi.saleId")
	Page<Long> findIdListBySaleId(Long saleId, Pageable pageable);

	/**
	 * 根据id获取库存
	 * @param id
	 * @return
	 */
	@Query(value = "SELECT fsi.quantity FROM FlashSaleItemPo fsi " +
			"WHERE :id = fsi.id")
	Integer findQuantityById(Long id);

	/**
	 * 根据saleId获取秒杀商品id与quantity的集合
	 * @param saleId     秒杀活动id
	 * @param pageable   分页查询
	 * @return           秒杀商品id与quantity的集合
	 */
	@Query(value = "SELECT new FlashSaleItemPo(fsi.id, fsi.quantity) FROM FlashSaleItemPo fsi " +
			"WHERE :saleId = fsi.saleId")
	Page<FlashSaleItemPo> findItemsBySaleId(Long saleId, Pageable pageable);

	/**
	 * 扣除指定数量的库存
	 * @param id
	 * @param number
	 * @return
	 */
	@Modifying
	@Transactional(rollbackFor = Exception.class)
	@Query(value = "UPDATE flash_sale_item fsi " +
			"SET quantity = quantity - :number " +
			"WHERE id = :id AND quantity >= :number", nativeQuery = true)
	int fetchQuantity(Long id, Integer number);
}

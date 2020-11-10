package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.GoodsSkuPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 * @date 2020-11-10
 */
public interface GoodsSkuRepository extends
		JpaRepository<GoodsSkuPo, Long>,
		JpaSpecificationExecutor<GoodsSkuPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE GoodsSkuPo gs SET " +
			"gs.goodsSpuId = CASE WHEN :#{#goodsSku.goodsSpuId} IS NULL " +
			"THEN gs.goodsSpuId ELSE :#{#goodsSku.goodsSpuId} END, " +
			"gs.skuSn = CASE WHEN :#{#goodsSku.skuSn} IS NULL " +
			"THEN gs.skuSn ELSE :#{#goodsSku.skuSn} END, " +
			"gs.name = CASE WHEN :#{#goodsSku.name} IS NULL " +
			"THEN gs.name ELSE :#{#goodsSku.name} END, " +
			"gs.originalPrice = CASE WHEN :#{#goodsSku.originalPrice} IS NULL " +
			"THEN gs.originalPrice ELSE :#{#goodsSku.originalPrice} END, " +
			"gs.configuration = CASE WHEN :#{#goodsSku.configuration} IS NULL " +
			"THEN gs.configuration ELSE :#{#goodsSku.configuration} END, " +
			"gs.weight = CASE WHEN :#{#goodsSku.weight} IS NULL " +
			"THEN gs.weight ELSE :#{#goodsSku.weight} END, " +
			"gs.imageUrl = CASE WHEN :#{#goodsSku.imageUrl} IS NULL " +
			"THEN gs.imageUrl ELSE :#{#goodsSku.imageUrl} END, " +
			"gs.inventory = CASE WHEN :#{#goodsSku.inventory} IS NULL " +
			"THEN gs.inventory ELSE :#{#goodsSku.inventory} END, " +
			"gs.detail = CASE WHEN :#{#goodsSku.detail} IS NULL " +
			"THEN gs.detail ELSE :#{#goodsSku.detail} END, " +
			"gs.disabled = CASE WHEN :#{#goodsSku.disabled} IS NULL " +
			"THEN gs.disabled ELSE :#{#goodsSku.disabled} END " +
			"WHERE gs.id = :#{#goodsSku.id}")
	int update(GoodsSkuPo goodsSku);
}

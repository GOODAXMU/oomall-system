package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 */
public interface PieceModelRepository extends
		JpaRepository<PieceFreightModelPo, Long>,
		JpaSpecificationExecutor<PieceFreightModelPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE PieceFreightModelPo pfm SET " +
			"pfm.freightModelId = CASE WHEN :#{#pieceFreightModel.freightModelId} IS NULL " +
			"THEN pfm.freightModelId ELSE :#{#pieceFreightModel.freightModelId} END, " +
			"pfm.firstItems = CASE WHEN :#{#pieceFreightModel.firstItems} IS NULL " +
			"THEN pfm.firstItems ELSE :#{#pieceFreightModel.firstItems} END, " +
			"pfm.firstItemsPrice = CASE WHEN :#{#pieceFreightModel.firstItemsPrice} IS NULL " +
			"THEN pfm.firstItemsPrice ELSE :#{#pieceFreightModel.firstItemsPrice} END, " +
			"pfm.additionalItems = CASE WHEN :#{#pieceFreightModel.additionalItems} IS NULL " +
			"THEN pfm.additionalItems ELSE :#{#pieceFreightModel.additionalItems} END, " +
			"pfm.additionalItemsPrice = CASE WHEN :#{#pieceFreightModel.additionalItemsPrice} IS NULL " +
			"THEN pfm.additionalItemsPrice ELSE :#{#pieceFreightModel.additionalItemsPrice} END, " +
			"pfm.regionId = CASE WHEN :#{#pieceFreightModel.regionId} IS NULL " +
			"THEN pfm.regionId ELSE :#{#pieceFreightModel.regionId} END " +
			"WHERE pfm.id = :#{#pieceFreightModel.id}")
	int update(PieceFreightModelPo pieceFreightModel);

}

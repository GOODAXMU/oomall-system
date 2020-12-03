package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 * @date 2020-11-10
 */
public interface WeightModelRepository extends
		JpaRepository<WeightFreightModelPo, Long>,
		JpaSpecificationExecutor<WeightFreightModelPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE WeightFreightModelPo wfm SET " +
			"wfm.freightModelId = CASE WHEN :#{#weightFreightModel.freightModelId} IS NULL " +
			"THEN wfm.freightModelId ELSE :#{#weightFreightModel.freightModelId} END, " +
			"wfm.firstWeight = CASE WHEN :#{#weightFreightModel.firstWeight} IS NULL " +
			"THEN wfm.firstWeight ELSE :#{#weightFreightModel.firstWeight} END, " +
			"wfm.firstWeightFreight = CASE WHEN :#{#weightFreightModel.firstWeightFreight} IS NULL " +
			"THEN wfm.firstWeightFreight ELSE :#{#weightFreightModel.firstWeightFreight} END, " +
			"wfm.tenPrice = CASE WHEN :#{#weightFreightModel.tenPrice} IS NULL " +
			"THEN wfm.tenPrice ELSE :#{#weightFreightModel.tenPrice} END, " +
			"wfm.fiftyPrice = CASE WHEN :#{#weightFreightModel.fiftyPrice} IS NULL " +
			"THEN wfm.fiftyPrice ELSE :#{#weightFreightModel.fiftyPrice} END, " +
			"wfm.hundredPrice = CASE WHEN :#{#weightFreightModel.hundredPrice} IS NULL " +
			"THEN wfm.hundredPrice ELSE :#{#weightFreightModel.hundredPrice} END, " +
			"wfm.trihunPrice = CASE WHEN :#{#weightFreightModel.trihunPrice} IS NULL " +
			"THEN wfm.trihunPrice ELSE :#{#weightFreightModel.trihunPrice} END, " +
			"wfm.abovePrice = CASE WHEN :#{#weightFreightModel.abovePrice} IS NULL " +
			"THEN wfm.abovePrice ELSE :#{#weightFreightModel.abovePrice} END, " +
			"wfm.regionId = CASE WHEN :#{#weightFreightModel.regionId} IS NULL " +
			"THEN wfm.regionId ELSE :#{#weightFreightModel.regionId} END ," +
			"wfm.gmtCreate = CASE WHEN :#{#freightModel.gmtCreated} IS NULL " +
			"THEN wfm.gmtCreate ELSE :#{#freightModel.gmtCreated} END, " +
			"wfm.gmtModified = CASE WHEN :#{#freightModel.gmtModified} IS NULL " +
			"THEN wfm.gmtModified ELSE :#{#freightModel.gmtModified} END " +
			"WHERE wfm.id = :#{#weightFreightModel.id}")
	int update(WeightFreightModelPo weightFreightModel);
}

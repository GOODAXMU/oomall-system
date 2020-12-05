package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.FreightModelPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author xincong yao
 * @date 2020-11-10
 */
public interface FreightModelRepository extends
		JpaRepository<FreightModelPo, Long>,
		JpaSpecificationExecutor<FreightModelPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE FreightModelPo fm SET " +
			"fm.shopId = CASE WHEN :#{#freightModel.shopId} IS NULL " +
			"THEN fm.shopId ELSE :#{#freightModel.shopId} END, " +
			"fm.name = CASE WHEN :#{#freightModel.name} IS NULL " +
			"THEN fm.name ELSE :#{#freightModel.name} END, " +
			"fm.defaultModel = CASE WHEN :#{#freightModel.defaultModel} IS NULL " +
			"THEN fm.defaultModel ELSE :#{#freightModel.defaultModel} END, " +
			"fm.type = CASE WHEN :#{#freightModel.type} IS NULL " +
			"THEN fm.type ELSE :#{#freightModel.type} END, " +
			"fm.weightUnit = CASE WHEN :#{#freightModel.weightUnit} IS NULL " +
			"THEN fm.weightUnit ELSE :#{#freightModel.weightUnit} END, " +
			"fm.gmtCreate = CASE WHEN :#{#freightModel.gmtCreate} IS NULL " +
			"THEN fm.gmtCreate ELSE :#{#freightModel.gmtCreate} END, " +
			"fm.gmtModified = CASE WHEN :#{#freightModel.gmtModified} IS NULL " +
			"THEN fm.gmtModified ELSE :#{#freightModel.gmtModified} END " +
			"WHERE fm.id = :#{#freightModel.id}")
	int update(FreightModelPo freightModel);
}

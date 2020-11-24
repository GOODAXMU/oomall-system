package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.ParamValuePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface ParamValueRepository extends
		JpaRepository<ParamValuePo, Long>,
		JpaSpecificationExecutor<ParamValuePo> {

	@Query(value = "SELECT pv FROM ParamValuePo pv WHERE taskId = :id")
	List<ParamValuePo> findParamValuesByTaskId(Long id);
}

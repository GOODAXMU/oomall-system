package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.ParamTypePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface ParamTypeRepository extends
		JpaRepository<ParamTypePo, Long>,
		JpaSpecificationExecutor<ParamTypePo> {

	@Query(value = "SELECT pt FROM ParamTypePo pt WHERE methodId = :id")
	List<ParamTypePo> findParamTypesByMethodId(Long id);
}

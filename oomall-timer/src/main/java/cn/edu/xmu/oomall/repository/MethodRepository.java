package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.MethodPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface MethodRepository extends
		JpaRepository<MethodPo, Long>,
		JpaSpecificationExecutor<MethodPo> {

	@Query(value = "SELECT m FROM MethodPo m WHERE id = :methodId")
	MethodPo findMethodById(Long methodId);

}

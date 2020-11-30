package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface OrderRepository extends
		JpaRepository<OrderPo, Long>,
		JpaSpecificationExecutor<OrderPo> {

}

package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.OrderItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface OrderItemRepository extends
		JpaRepository<OrderItemPo, Long>,
		JpaSpecificationExecutor<OrderItemPo> {

}

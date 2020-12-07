package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.OrderItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface OrderItemRepository extends
		JpaRepository<OrderItemPo, Long>,
		JpaSpecificationExecutor<OrderItemPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderItemPo oi " +
			"SET oi.beShareId = :beSharedId " +
			"WHERE id = :id")
	int updateBeSharedIdById(Long id, Long beSharedId);

}

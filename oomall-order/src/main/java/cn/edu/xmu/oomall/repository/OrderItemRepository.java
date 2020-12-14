package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.OrderItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-10
 */
public interface OrderItemRepository extends
		JpaRepository<OrderItemPo, Long>,
		JpaSpecificationExecutor<OrderItemPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderItemPo oi SET " +
			"oi.orderId = CASE WHEN :#{#orderItem.orderId} IS NULL THEN oi.orderId ELSE :#{#orderItem.orderId} END, " +
			"oi.goodsSkuId = CASE WHEN :#{#orderItem.goodsSkuId} IS NULL THEN oi.goodsSkuId ELSE :#{#orderItem.goodsSkuId} END, " +
			"oi.quantity = CASE WHEN :#{#orderItem.quantity} IS NULL THEN oi.quantity ELSE :#{#orderItem.quantity} END, " +
			"oi.price = CASE WHEN :#{#orderItem.price} IS NULL THEN oi.price ELSE :#{#orderItem.price} END, " +
			"oi.discount = CASE WHEN :#{#orderItem.discount} IS NULL THEN oi.discount ELSE :#{#orderItem.discount} END, " +
			"oi.name = CASE WHEN :#{#orderItem.name} IS NULL THEN oi.name ELSE :#{#orderItem.name} END, " +
			"oi.couponActivityId = CASE WHEN :#{#orderItem.couponActivityId} IS NULL THEN oi.couponActivityId ELSE :#{#orderItem.couponActivityId} END, " +
			"oi.beShareId = CASE WHEN :#{#orderItem.beShareId} IS NULL THEN oi.beShareId ELSE :#{#orderItem.beShareId} END " +
			"WHERE oi.id = :#{#orderItem.id}")
	int update(OrderItemPo orderItem);

	List<OrderItemPo> findByOrderId(Long id);

	@Query(value = "SELECT o.orderId FROM OrderItemPo o WHERE o.id = :id")
	Long findOrderIdById(Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderItemPo oip SET oip.orderId = :orderId WHERE oip.id = :id")
	int changeOrderIdTo(Long id, Long orderId);
}

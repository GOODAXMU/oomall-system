package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-10
 * modified by Jianheng HUANG, date: 2020-11-29
 * modified by xincong yao, date: 2020-12-3
 */
public interface OrderRepository extends
		JpaRepository<OrderPo, Long>,
		JpaSpecificationExecutor<OrderPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo o SET " +
			"o.customerId = CASE WHEN :#{#order.customerId} IS NULL THEN o.customerId ELSE :#{#order.customerId} END, " +
			"o.shopId = CASE WHEN :#{#order.shopId} IS NULL THEN o.shopId ELSE :#{#order.shopId} END, " +
			"o.orderSn = CASE WHEN :#{#order.orderSn} IS NULL THEN o.orderSn ELSE :#{#order.orderSn} END, " +
			"o.pid = CASE WHEN :#{#order.pid} IS NULL THEN o.pid ELSE :#{#order.pid} END, " +
			"o.consignee = CASE WHEN :#{#order.consignee} IS NULL THEN o.consignee ELSE :#{#order.consignee} END, " +
			"o.regionId = CASE WHEN :#{#order.regionId} IS NULL THEN o.regionId ELSE :#{#order.regionId} END, " +
			"o.address = CASE WHEN :#{#order.address} IS NULL THEN o.address ELSE :#{#order.address} END, " +
			"o.mobile = CASE WHEN :#{#order.mobile} IS NULL THEN o.mobile ELSE :#{#order.mobile} END, " +
			"o.message = CASE WHEN :#{#order.message} IS NULL THEN o.message ELSE :#{#order.message} END, " +
			"o.orderType = CASE WHEN :#{#order.orderType} IS NULL THEN o.orderType ELSE :#{#order.orderType} END, " +
			"o.freightPrice = CASE WHEN :#{#order.freightPrice} IS NULL THEN o.freightPrice ELSE :#{#order.freightPrice} END, " +
			"o.couponId = CASE WHEN :#{#order.couponId} IS NULL THEN o.couponId ELSE :#{#order.couponId} END, " +
			"o.couponActivityId = CASE WHEN :#{#order.couponActivityId} IS NULL THEN o.couponActivityId ELSE :#{#order.couponActivityId} END, " +
			"o.discountPrice = CASE WHEN :#{#order.discountPrice} IS NULL THEN o.discountPrice ELSE :#{#order.discountPrice} END, " +
			"o.originPrice = CASE WHEN :#{#order.originPrice} IS NULL THEN o.originPrice ELSE :#{#order.originPrice} END, " +
			"o.presaleId = CASE WHEN :#{#order.presaleId} IS NULL THEN o.presaleId ELSE :#{#order.presaleId} END, " +
			"o.grouponId = CASE WHEN :#{#order.grouponId} IS NULL THEN o.grouponId ELSE :#{#order.grouponId} END, " +
			"o.grouponDiscount = CASE WHEN :#{#order.grouponDiscount} IS NULL THEN o.grouponDiscount ELSE :#{#order.grouponDiscount} END, " +
			"o.rebateNum = CASE WHEN :#{#order.rebateNum} IS NULL THEN o.rebateNum ELSE :#{#order.rebateNum} END, " +
			"o.confirmTime = CASE WHEN :#{#order.confirmTime} IS NULL THEN o.confirmTime ELSE :#{#order.confirmTime} END, " +
			"o.shipmentSn = CASE WHEN :#{#order.shipmentSn} IS NULL THEN o.shipmentSn ELSE :#{#order.shipmentSn} END, " +
			"o.state = CASE WHEN :#{#order.state} IS NULL THEN o.state ELSE :#{#order.state} END, " +
			"o.subState = CASE WHEN :#{#order.subState} IS NULL THEN o.subState ELSE :#{#order.subState} END, " +
			"o.beDeleted = CASE WHEN :#{#order.beDeleted} IS NULL THEN o.beDeleted ELSE :#{#order.beDeleted} END " +
			"WHERE o.id = :#{#order.id}")
	int update(OrderPo order);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo o SET " +
			"o.consignee = CASE WHEN :#{#order.consignee} IS NULL THEN o.consignee ELSE :#{#order.consignee} END, " +
			"o.regionId = CASE WHEN :#{#order.regionId} IS NULL THEN o.regionId ELSE :#{#order.regionId} END, " +
			"o.address = CASE WHEN :#{#order.address} IS NULL THEN o.address ELSE :#{#order.address} END, " +
			"o.mobile = CASE WHEN :#{#order.mobile} IS NULL THEN o.mobile ELSE :#{#order.mobile} END, " +
			"o.message = CASE WHEN :#{#order.message} IS NULL THEN o.message ELSE :#{#order.message} END, " +
			"o.gmtModified = CASE WHEN :#{#order.gmtModified} IS NULL THEN o.gmtModified ELSE :#{#order.gmtModified} END " +
			"WHERE o.id = :#{#order.id} AND o.customerId = :#{#order.customerId} AND o.gmtModified IS NOT NULL AND o.state < :state")
	int updateWhenStateLessThan(OrderPo order, Integer state);

	@Query(value = "SELECT o.state FROM OrderPo o WHERE o.id = :id AND o.customerId = :customerId")
	Integer findOrderStateByIdAndCustomerId(Long id, Long customerId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo o SET o.state = :state WHERE o.id = :id")
	int updateOrderState(Long id, Integer state);

	/**
	 * @author Jianheng HUANG
	 * @date 2020-11-29
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo o SET o.message = :message WHERE o.id = :id")
	int addShopOrderMessage(Long id, String message);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo p SET p.beDeleted = 1 WHERE p.id = :id")
	int deleteSelfOrderById(Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo p SET p.state = :to " +
			"WHERE p.id = :id AND p.state = :s")
	int changeOrderStateWhenStateEquals(Long id, Integer to, Integer s);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo p SET p.subState = :to " +
			"WHERE p.id = :id AND p.subState = :s")
	int changeOrderSubStateWhenSubStateEquals(Long id, Integer to, Integer s);

	@Modifying
	@Transactional
	@Query(value = "UPDATE OrderPo p " +
			"SET p.orderType = :normal, p.grouponId = null, p.grouponDiscount = null " +
			"WHERE p.id = :id AND p.customerId = :customerId AND p.orderType = :groupon AND p.state < :state")
	int updateGroupon2NormalWhenStateLessThan(Long id, Long customerId, int groupon, int normal, int state);

	@Query(value = "SELECT new OrderPo(o.orderSn, o.shopId) FROM OrderPo o WHERE id = :orderId")
	OrderPo findOrderSnAndShopIdById(Long orderId);

	@Query(value = "SELECT o.customerId FROM OrderPo o WHERE o.id = :id")
	Long findCustomerIdById(Long id);

	@Query(value = "SELECT o.state FROM OrderPo o WHERE o.id = :id")
	Integer findOrderStatusById(Long id);

	@Query(value = "SELECT new OrderPo(o.id, o.originPrice, o.originPrice) FROM OrderPo o WHERE id = :id")
	OrderPo findPrice(Long id);
}

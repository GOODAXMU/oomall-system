package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.entity.PaymentPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-10
 * @author Wang Zhizhou
 * modified 2020/12/14
 */
public interface PaymentRepository extends
		JpaRepository<PaymentPo, Long>,
		JpaSpecificationExecutor<PaymentPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE PaymentPo p SET p.amount = CASE WHEN :#{#payment.amount} IS NULL THEN p.amount ELSE :#{#payment.amount} END, " +
			"p.actualAmount = CASE WHEN :#{#payment.actualAmount} IS NULL THEN p.actualAmount ELSE :#{#payment.actualAmount} END, " +
			"p.aftersaleId = CASE WHEN :#{#payment.aftersaleId} IS NULL THEN p.aftersaleId ELSE :#{#payment.aftersaleId} END, " +
			"p.paymentPattern = CASE WHEN :#{#payment.paymentPattern} IS NULL THEN p.paymentPattern ELSE :#{#payment.paymentPattern} END, " +
			"p.payTime = CASE WHEN :#{#payment.payTime} IS NULL THEN p.payTime ELSE :#{#payment.payTime} END, " +
			"p.paySn = CASE WHEN :#{#payment.paySn} IS NULL THEN p.paySn ELSE :#{#payment.paySn} END, " +
			"p.beginTime = CASE WHEN :#{#payment.beginTime} IS NULL THEN p.beginTime ELSE :#{#payment.beginTime} END, " +
			"p.endTime = CASE WHEN :#{#payment.endTime} IS NULL THEN p.endTime ELSE :#{#payment.endTime} END, " +
			"p.orderId = CASE WHEN :#{#payment.orderId} IS NULL THEN p.orderId ELSE :#{#payment.orderId} END, " +
			"p.state = CASE WHEN :#{#payment.state} IS NULL THEN p.state ELSE :#{#payment.state} END " +
			"WHERE p.id = :#{#payment.id}")
	int update(PaymentPo payment);

	@Modifying
	@Transactional
	@Query(value = "UPDATE PaymentPo po SET po.state = :state WHERE po.id = :id")
	int updatePaymentState(Long id, Integer state);

	@Modifying
	@Transactional
	@Query(value = "SELECT p FROM PaymentPo p WHERE p.orderId = :oid")
	List<PaymentPo> findAllByOrderId(Long oid);


	@Modifying
	@Transactional
	@Query(value = "SELECT p FROM PaymentPo p WHERE p.aftersaleId = :aid")
	List<PaymentPo> findAllByAfterSaleId(Long aid);
}

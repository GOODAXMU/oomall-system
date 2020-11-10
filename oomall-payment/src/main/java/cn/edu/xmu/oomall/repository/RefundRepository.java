package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.entity.RefundPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xincong yao
 * @date 2020-11-10
 */
public interface RefundRepository extends
		JpaRepository<PaymentPo, Long>,
		JpaSpecificationExecutor<PaymentPo> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE RefundPo r SET " +
			"r.paymentId = CASE WHEN :#{#refund.paymentId} IS NULL THEN r.paymentId ELSE :#{#refund.paymentId} END, " +
			"r.amount = CASE WHEN :#{#refund.amount} IS NULL THEN r.amount ELSE :#{#refund.amount} END, " +
			"r.paySn = CASE WHEN :#{#refund.paySn} IS NULL THEN r.paySn ELSE :#{#refund.paySn} END, " +
			"r.billId = CASE WHEN :#{#refund.billId} IS NULL THEN r.billId ELSE :#{#refund.billId} END, " +
			"r.state = CASE WHEN :#{#refund.state} IS NULL THEN r.state ELSE :#{#refund.state} END " +
			"WHERE r.id = :#{#refund.id}")
	int update(RefundPo refund);
}

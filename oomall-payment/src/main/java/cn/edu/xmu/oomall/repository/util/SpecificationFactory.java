package cn.edu.xmu.oomall.repository.util;

import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.entity.RefundPo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-10
 * <p>
 * 不支持根据id查询
 * 构造复杂对象来执行id查询性能低
 * 鼓励使用hibernate接口或者手写query
 */
public class SpecificationFactory {

	public static Specification<PaymentPo> get(PaymentPo payment) {
		return (Specification<PaymentPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (payment.getAmount() != null) {
				predicates.add(builder.equal(root.get("amount"), payment.getAmount()));
			}
			if (payment.getActualAmount() != null) {
				predicates.add(builder.equal(root.get("actualAmount"), payment.getActualAmount()));
			}
			if (payment.getPaymentPattern() != null) {
				predicates.add(builder.equal(root.get("paymentPattern"), payment.getPaymentPattern()));
			}
			if (payment.getPayTime() != null) {
				predicates.add(builder.equal(root.get("payTime"), payment.getPayTime()));
			}
			if (payment.getPaySn() != null) {
				predicates.add(builder.equal(root.get("paySn"), payment.getPaySn()));
			}
			if (payment.getBeginTime() != null) {
				predicates.add(builder.equal(root.get("beginTime"), payment.getBeginTime()));
			}
			if (payment.getEndTime() != null) {
				predicates.add(builder.equal(root.get("endTime"), payment.getEndTime()));
			}
			if (payment.getOrderId() != null) {
				predicates.add(builder.equal(root.get("orderId"), payment.getOrderId()));
			}
			if (payment.getState() != null) {
				predicates.add(builder.equal(root.get("state"), payment.getState()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<RefundPo> get(RefundPo refund) {
		return (Specification<RefundPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (refund.getPaymentId() != null) {
				predicates.add(builder.equal(root.get("paymentId"), refund.getPaymentId()));
			}
			if (refund.getAmount() != null) {
				predicates.add(builder.equal(root.get("amount"), refund.getAmount()));
			}
			if (refund.getPaySn() != null) {
				predicates.add(builder.equal(root.get("paySn"), refund.getPaySn()));
			}
			if (refund.getBillId() != null) {
				predicates.add(builder.equal(root.get("billId"), refund.getBillId()));
			}
			if (refund.getState() != null) {
				predicates.add(builder.equal(root.get("state"), refund.getState()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}

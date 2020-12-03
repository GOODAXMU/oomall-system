package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.repository.PaymentRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import cn.edu.xmu.oomall.vo.PaymentResponse;
import cn.edu.xmu.oomall.vo.Reply;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Zhizhou
 * create 2020/11/24
 * modified 2020/11/30
 */

@Component
public class PaymentDao {

    @Autowired
    private PaymentRepository paymentRepository;

    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId) {
        PaymentPo po = new PaymentPo();
        po.setOrderId(orderId);

        List<Payment> payments = new ArrayList<>();
        for (PaymentPo paymentPo : paymentRepository.findAll(SpecificationFactory.get(po))) {
            payments.add(new Payment(paymentPo));
        }

        if (payments.size() > 0) {
            return new Reply<>(payments);
        }

        return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
    }

    public Reply<List<Payment>> getPaymentsByAftersaleId(Long aftersaleId) {
        PaymentPo po = new PaymentPo();
        po.setAftersaleId(aftersaleId);

        List<Payment> payments = new ArrayList<>();
        for (PaymentPo paymentPo : paymentRepository.findAll(SpecificationFactory.get(po))) {
            payments.add(new Payment(paymentPo));
        }

        if (payments.size() > 0) {
            return new Reply<>(payments);
        }

        return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
    }

    public Reply<List<Long>> getOrderIdByPaymentId(Long paymentId) {
        PaymentPo po = paymentRepository.getOne(paymentId);
        List<Long> ids = new ArrayList<>();
        ids.add(po.getOrderId());
        ids.add(po.getAftersaleId());

        if (null == ids.get(0) && null == ids.get(1)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return new Reply<>(ids);
    }

    public Reply<Payment> savePayment(Payment payment) {
        PaymentPo po = payment.createPo();
        po.setGmtCreated(LocalDateTime.now());
        po.setGmtModified(LocalDateTime.now());

        po = paymentRepository.save(po);
        return new Reply<>(new Payment(po));
    }

    public Reply<Object> updatePayment(Payment payment) {
        paymentRepository.update(payment.createPo());

        return new Reply<>();
    }
}

package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.repository.PaymentRepository;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Zhizhou
 * create 2020/11/24
 * modified 2020/12/14
 */

@Component
public class PaymentDao {

    @Autowired
    private PaymentRepository paymentRepository;

    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId) {
        List<Payment> payments = new ArrayList<>();
        for (PaymentPo paymentPo : paymentRepository.findAllByOrderId(orderId)) {
            payments.add(new Payment(paymentPo));
        }

        return new Reply<>(payments);
    }

    public Reply<List<Payment>> getPaymentsByAfterSaleId(Long afterSaleId) {
        List<Payment> payments = new ArrayList<>();
        for (PaymentPo paymentPo : paymentRepository.findAllByAfterSaleId(afterSaleId)) {
            payments.add(new Payment(paymentPo));
        }

        return new Reply<>(payments);
    }

    public Reply<Payment> getPaymentById(Long paymentId) {
        PaymentPo po = paymentRepository.getOne(paymentId);
        if (null == po) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return new Reply<>(new Payment(po));
    }

    public Reply<Payment> savePayment(Payment payment) {
        PaymentPo po = payment.createPo();
        po.setGmt();

        po = paymentRepository.save(po);
        return new Reply<>(new Payment(po));
    }

    public Long calcOrderPayments(Long orderId) {
        Long price = 0L;

        for (PaymentPo po : paymentRepository.findAllByOrderId(orderId)) {
            price += po.getActualAmount();
        }

        return  price;
    }

    public Long calcAfterSalePayments(Long afterSaleId) {
        Long price = 0L;

        for (PaymentPo po : paymentRepository.findAllByAfterSaleId(afterSaleId)) {
            price += po.getActualAmount();
        }

        return  price;
    }
}

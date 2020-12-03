package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.PaymentPo;
import cn.edu.xmu.oomall.entity.RefundPo;
import cn.edu.xmu.oomall.repository.RefundRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wang Zhizhou
 * created 2020/11/29
 * modified 2020/11/29
 */
@Component
public class RefundDao {

    @Autowired
    private RefundRepository refundRepository;

    public Reply<List<Refund>> getRefundsByOrderId(Long orderId) {
        RefundPo refundPo = new RefundPo();
        refundPo.setOrderId(orderId);

        List<Refund> refunds = new ArrayList<>();
        for (RefundPo po : refundRepository.findAll(SpecificationFactory.get(refundPo))) {
            refunds.add(new Refund(po));
        }

        if (refunds.size() > 0) {
            return new Reply<>(refunds);
        }

        return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
    }

    public Reply<List<Refund>> getRefundsByAftersaleId(Long aftersaleId) {
        RefundPo refundPo = new RefundPo();
        refundPo.setAftersaleId(aftersaleId);

        List<Refund> refunds = new ArrayList<>();
        for (RefundPo po : refundRepository.findAll(SpecificationFactory.get(refundPo))) {
            refunds.add(new Refund(po));
        }

        if (refunds.size() > 0) {
            return new Reply<>(refunds);
        }

        return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
    }

    public Reply<Refund> saveRefund(Refund refund) {
        RefundPo po = refund.createPo();
        po.setGmtCreated(LocalDateTime.now());
        po.setGmtModified(LocalDateTime.now());

        po = refundRepository.save(po);
        return new Reply<>(new Refund(po));
    }

    public Reply<Object> updateRefund(Refund refund) {
        refundRepository.update(refund.createPo());

        return new Reply<>();
    }

}

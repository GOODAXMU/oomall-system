package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.constant.ResponseStatus;
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
 * modified 2020/12/14
 */
@Component
public class RefundDao {

    @Autowired
    private RefundRepository refundRepository;

    public Reply<List<Refund>> getRefundsByOrderId(Long orderId) {
        List<Refund> refunds = new ArrayList<>();
        for (RefundPo po : refundRepository.findAllByOrderId(orderId)) {
            refunds.add(new Refund(po));
        }

        return new Reply<>(refunds);
    }

    public Reply<List<Refund>> getRefundsByAfterSaleId(Long aftersaleId) {
        List<Refund> refunds = new ArrayList<>();
        for (RefundPo po : refundRepository.findAllByAfterSaleId(aftersaleId)) {
            refunds.add(new Refund(po));
        }

        return new Reply<>(refunds);
    }

    public Reply<Refund> saveRefund(Refund refund) {
        RefundPo po = refund.createPo();
        po.setGmt();

        po = refundRepository.save(po);
        return new Reply<>(new Refund(po));
    }
}

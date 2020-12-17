package cn.edu.xmu.oomall.service.dubbo;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.dao.PaymentDao;
import cn.edu.xmu.oomall.dao.RefundDao;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import cn.edu.xmu.oomall.external.service.IOrderService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.service.IDubboPaymentService;
import cn.edu.xmu.oomall.service.PatternPayService;
import cn.edu.xmu.oomall.vo.Reply;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 * modified 2020/12/15
 */
//@DubboService(version = "${oomall.payment.version}")
public class DubboPaymentServiceImpl implements IDubboPaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private RefundDao refundDao;

    @Autowired
    private PatternPayService patternPayService;

    @Autowired
    private ServiceFactory serviceFactory;

    private IOrderService orderService;

    @PostConstruct
    public void init() {
        orderService = (IOrderService) serviceFactory.get(IOrderService.class);
    }

    @Override
    public Boolean createRefund(Long afterSaleId, Long orderItemId, Integer quantity) {
        OrderItemDto oi = orderService.getOrderItem(orderItemId);
        if (null == oi) {
            return false;
        }

        Long deposit = orderService.getOrderPresaleDeposit(oi.getOrderId());
        Reply<List<Payment>> r = paymentDao.getPaymentsByOrderId(oi.getOrderId());

        if (!r.isOk()) {            // 查询失败无从返款
            return false;
        } else if (deposit > 0){    // 存在定金, 定金无法返款
            r.getData().removeIf(payment -> payment.getActualAmount() == deposit);
        }

        // 根据 orderItem 价格数量和折扣 和 返款quantity 计算返款 amount 和 返款比例
        Long refundAmount = oi.getPrice() * quantity - oi.getDiscount() * quantity / oi.getQuantity();
        Long totalPayment = 0L;
        for (Payment payment : r.getData()) {
            totalPayment += payment.getActualAmount();
        }

        double refundRate = (double)refundAmount / totalPayment;

        Boolean success = true;
        for (Payment payment : r.getData()) {
            Long amount = (long)(payment.getActualAmount() * refundRate);
            Refund refund = new Refund(payment.getId(), afterSaleId, amount);
            refund.setState(Refund.State.WAITING);

            Payment rePayment = new Payment(payment.getPattern(), amount, oi.getOrderId(), afterSaleId);
            // 返款是否成功
            if (patternPayService.refundByPattern(rePayment)) {
                refund.setState(Refund.State.SUCCESS);
                refundDao.saveRefund(refund);
            }
            else {
                refund.setState(Refund.State.FAILED);
                success = false;
                // 即使本次失败仍然对其他支付进行返款
            }
        }

        return success;
    }
}

package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.PaymentDao;
import cn.edu.xmu.oomall.dao.RefundDao;
import cn.edu.xmu.oomall.external.service.IAfterSaleServer;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.service.IOrderService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Zhizhou
 * create 2020/11/24
 * modified 2020/12/10
 */

@Service
public class CustomerPaymentService {

    @Autowired
    private PaymentDao paymentDao;
    
    @Autowired
    private RefundDao refundDao;

    @Autowired
    private ServiceFactory serviceFactory;

    private IExternalPayment externalPayment;
    private IOrderService orderService;
    private IAfterSaleServer afterSaleServer;

    @PostConstruct
    public void init() {
        externalPayment = (IExternalPayment) serviceFactory.get(IExternalPayment.class);
    }

    /**
     * 根据所创建的 payment 完成订单支付
     * @return
     */
    public Reply<Payment> createOrderPayment(Payment payment, Long customerId) {
        Long orderId = payment.getOrderId();
        if (null == orderId) {
            return new Reply<>(ResponseStatus.FIELD_INVALID);
        }

        // 检查用户和该订单是否匹配
        if (orderService.isCustomerOwnOrder(customerId, orderId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        // 根据订单状态确认是否允许支付
        if (!orderService.orderCanBePaid(orderId)) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }

        payment.setPaySn(payment.getPaymentPattern() + UUID.randomUUID().toString());
        payment.setState(Payment.State.WAITING);

        // 进行支付
        if (externalPayment.pay(payment.getActualAmount()) > 0L) {
            payment.setPayTime(LocalDateTime.now());
            payment.setState(Payment.State.SUCCESS);
        }
        else {
            payment.setPayTime(null);
            payment.setState(Payment.State.FAILED);
        }

        // 支付成果才写入数据库
        if (payment.isPaySuccess()) {
            paymentDao.savePayment(payment);
        }

        // 提醒订单服务器检查支付状态
        orderService.checkOrderPaid(orderId, paymentDao.calcOrderPayments(orderId));

        return new Reply<>(payment);
    }

    /**
     * 根据所创建的 payment 完成订单支付
     * @return
     */
    public Reply<Payment> createAfterSalePayment(Payment payment, Long customerId) {
        Long afterSaleId = payment.getAfterSaleId();
        if (null == afterSaleId) {
            return new Reply<>(ResponseStatus.FIELD_INVALID);
        }

        // 检查用户和该售后是否匹配
        if (afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        // 根据订单状态确认是否允许支付
        if (!afterSaleServer.afterSaleCanBePaid(afterSaleId)) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }

        payment.setPaySn(payment.getPaymentPattern() + UUID.randomUUID().toString());
        payment.setState(Payment.State.WAITING);

        // 进行支付
        if (externalPayment.pay(payment.getActualAmount()) > 0L) {
            payment.setPayTime(LocalDateTime.now());
            payment.setState(Payment.State.SUCCESS);
        }
        else {
            payment.setPayTime(null);
            payment.setState(Payment.State.FAILED);
        }

        // 支付成果才写入数据库
        if (payment.isPaySuccess()) {
            paymentDao.savePayment(payment);
        }

        // 提醒订单服务器检查支付状态
        afterSaleServer.checkAfterSalePaid(afterSaleId, paymentDao.calcAfterSalePayments(afterSaleId));

        return new Reply<>(payment);
    }

    /**
     * 根据订单id 查询该订单的所有支付信息
     * @param orderId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId, Long customerId) {
        // 检查顾客查询订单属于本顾客
        if (orderService.isCustomerOwnOrder(customerId, orderId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return paymentDao.getPaymentsByOrderId(orderId);
    }

    /**
     * 根据售后id 查询该售后的所有支付信息
     * @param  afterSaleId  售后id
     * @return
     */
    public Reply<List<Payment>> getPaymentsByAfterSaleId(Long afterSaleId, Long customerId) {
        if (afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return paymentDao.getPaymentsByAfterSaleId(afterSaleId);
    }

    /**
     * 根据订单id 查询该订单的所有退款信息
     * @param orderId
     * @return
     */
    public Reply<List<Refund>> getRefundsByOrderId(Long orderId, Long customerId) {
        // 检查顾客查询订单属于本顾客
        if (orderService.isCustomerOwnOrder(customerId, orderId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return refundDao.getRefundsByOrderId(orderId);
    }

    /**
     * 根据订单id 查询该售后单的所有退款信息
     * @param afterSaleId
     * @return
     */
    public Reply<List<Refund>> getRefundByAfterSaleId(Long afterSaleId, Long customerId) {
        if (afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return refundDao.getRefundsByAfterSaleId(afterSaleId);
    }

}

package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.PaymentDao;
import cn.edu.xmu.oomall.dao.RefundDao;
import cn.edu.xmu.oomall.external.service.IAfterSaleService;
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
 * modified 2020/12/18
 */

@Service
public class CustomerPaymentService {

    @Autowired
    private PaymentDao paymentDao;
    
    @Autowired
    private RefundDao refundDao;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private PatternPayService patternPaymentService;

    private IOrderService orderService;
    private IAfterSaleService afterSaleServer;

    @PostConstruct
    public void init() {
        orderService = (IOrderService) serviceFactory.get(IOrderService.class);
        afterSaleServer = (IAfterSaleService) serviceFactory.get(IAfterSaleService.class);
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

        // 获取该订单在当前状态下的应支付金额
        Long price = orderService.priceOrderCanBePaid(customerId, orderId);
        if (null == price) {        // 订单id 不存在
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        } else if (price < 0) {     // 订单与顾客不匹配
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        } else if (price == 0) {    // 订单状态不允许支付
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        } else if (price < payment.getActualAmount()) {     // 超额支付, 视作订单状态不允许
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }

        // price 为订单应付金额

        // todo 下面的逻辑删除
        /*
        // 检查顾客查询订单属于本顾客
        Boolean b = orderService.isCustomerOwnOrder(customerId, orderId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        // 根据订单状态确认是否允许支付
        if (!orderService.orderCanBePaid(orderId)) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }
        */

        payment.setPaySn(payment.getPattern() + "-" + UUID.randomUUID().toString());

        // 进行支付
        if (patternPaymentService.payByPattern(payment)) {
            payment.setPayTime();
            payment.setState(Payment.State.SUCCESS);
        }
        else {
            payment.setPayTime(null);
            payment.setState(Payment.State.FAILED);
        }

        // 支付成果才写入数据库
        if (payment.isPaySuccess()) {
            Reply<Payment> r = paymentDao.savePayment(payment);
            if (r.isOk()) {
                // 提醒订单服务器检查支付状态
                orderService.checkOrderPaid(orderId, paymentDao.calcOrderPayments(orderId));
            }

            return r;
        }

        // 支付失败并不报错, 返回失败的支付信息
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

        // 检查顾客查询售后属于本顾客
        Boolean b = afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        // 根据订单状态确认是否允许支付
        if (!afterSaleServer.afterSaleCanBePaid(afterSaleId)) {
            return new Reply<>(ResponseStatus.ORDER_FORBID);
        }

        payment.setPaySn(payment.getPattern() + "-" + UUID.randomUUID().toString());

        // 进行支付
        if (patternPaymentService.payByPattern(payment)) {
            payment.setPayTime();
            payment.setState(Payment.State.SUCCESS);
        }
        else {
            payment.setPayTime(null);
            payment.setState(Payment.State.FAILED);
        }

        // 支付成果才写入数据库
        if (payment.isPaySuccess()) {
            Reply<Payment> r = paymentDao.savePayment(payment);

            return r;
        }

        // 支付失败不报错, 返回失败的支付信息
        return new Reply<>(payment);
    }

    /**
     * 根据订单id 查询该订单的所有支付信息
     * @param orderId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId, Long customerId) {
        // 检查顾客查询订单属于本顾客
        Boolean b = orderService.isCustomerOwnOrder(customerId, orderId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        return paymentDao.getPaymentsByOrderId(orderId);
    }

    /**
     * 根据售后id 查询该售后的所有支付信息
     * @param  afterSaleId  售后id
     * @return
     */
    public Reply<List<Payment>> getPaymentsByAfterSaleId(Long afterSaleId, Long customerId) {
        // 检查顾客查询售后属于本顾客
        Boolean b = afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
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
        Boolean b = orderService.isCustomerOwnOrder(customerId, orderId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        return refundDao.getRefundsByOrderId(orderId);
    }

    /**
     * 根据订单id 查询该售后单的所有退款信息
     * @param afterSaleId
     * @return
     */
    public Reply<List<Refund>> getRefundByAfterSaleId(Long afterSaleId, Long customerId) {
        // 检查顾客查询售后属于本顾客
        Boolean b = afterSaleServer.isCustomerOwnAfterSale(customerId, afterSaleId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        return refundDao.getRefundsByAfterSaleId(afterSaleId);
    }

}

package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.PaymentDao;
import cn.edu.xmu.oomall.dao.RefundDao;
import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.service.IOrderService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Zhizhou
 * create 2020/12/10
 * modified 2020/12/11
 */

@Service
public class ShopPaymentService {

    @Autowired
    private PaymentDao paymentDao;
    
    @Autowired
    private RefundDao refundDao;

    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private PatternPayService patternPayService;

    private IOrderService orderService;
    private IAfterSaleService afterSaleServer;

    @PostConstruct
    public void init() {
        // todo 装填 IXXService
    }


    /**
     * 根据订单id 查询该订单的所有支付信息
     * @param orderId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId, Long shopId) {
        if (orderService.isShopOwnOrder(shopId, orderId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return paymentDao.getPaymentsByOrderId(orderId);
    }

    /**
     * 根据售后id 查询该售后的所有支付信息
     * @param afterSaleId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByAfterSaleId(Long afterSaleId, Long shopId) {
        if (afterSaleServer.isShopOwnAfterSale(shopId, afterSaleId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return paymentDao.getPaymentsByAfterSaleId(afterSaleId);
    }

    /**
     * 根据所创建的 refund 完成返款
     * @param refund 请求创建的返款信息
     * @param shopId 请求来自的shopId
     * @return
     */
    public Reply<Refund> createRefund(Refund refund, Long shopId) {
        // 根据 paymentId 查询支付信息, 并由此设计返款
        Reply<Payment> r = paymentDao.getPaymentById(refund.getPaymentId());
        if (!r.isOk()) {
            return new Reply<>(r.getResponseStatus());
        }

        // 校验 shopId 与操作资源id
        Long orderId = r.getData().getOrderId();
        Long afterSaleId = r.getData().getAfterSaleId();
        boolean shopOwnOrder     = null != orderId && orderService.isShopOwnOrder(shopId, orderId);
        boolean shopOwnAfterSale = null != afterSaleId && afterSaleServer.isShopOwnAfterSale(shopId, afterSaleId);
        if (!shopOwnOrder && !shopOwnAfterSale) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        // 设置返款信息
        refund.setOrderId(orderId);
        refund.setAftersaleId(afterSaleId);
        refund.setPaySn("refund" + UUID.randomUUID().toString());
        refund.setState(Refund.State.WAITING);

        // 创建用于返款的反向支付
        Payment payment = new Payment(r.getData().getPaymentPattern(), refund.getAmount(), orderId, afterSaleId);

        // 返款是否成功
        if (patternPayService.refundByPattern(payment)) {
            refund.setState(Refund.State.SUCCESS);
        }
        else {
            refund.setState(Refund.State.FAILED);
        }

        if (refund.isRefundSuccess()) {
            refundDao.saveRefund(refund);
        }

        return new Reply<>(refund);
    }

    /**
     * 根据订单id 查询该订单的所有退款信息
     * @param orderId
     * @return
     */
    public Reply<List<Refund>> getRefundsByOrderId(Long orderId, Long shopId) {
        if (orderService.isShopOwnOrder(shopId, orderId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return refundDao.getRefundsByOrderId(orderId);
    }

    /**
     * 根据订单id 查询该售后单的所有退款信息
     * @param afterSaleId
     * @return
     */
    public Reply<List<Refund>> getRefundsByAfterSaleId(Long afterSaleId, Long shopId) {
        if (afterSaleServer.isShopOwnAfterSale(shopId, afterSaleId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        return refundDao.getRefundsByAfterSaleId(afterSaleId);
    }

}

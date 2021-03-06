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
import java.util.List;
import java.util.UUID;

/**
 * @author Wang Zhizhou
 * create 2020/12/10
 * modified 2020/12/16
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
    private IAfterSaleService afterSaleService;

    @PostConstruct
    public void init() {
        orderService = (IOrderService) serviceFactory.get(IOrderService.class);
        afterSaleService = (IAfterSaleService) serviceFactory.get(IAfterSaleService.class);
    }


    /**
     * 根据订单id 查询该订单的所有支付信息
     * @param orderId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByOrderId(Long orderId, Long shopId) {
        // 检查订单是否属于该商店
        Boolean b = orderService.isShopOwnOrder(shopId, orderId);
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
     * @param afterSaleId
     * @return
     */
    public Reply<List<Payment>> getPaymentsByAfterSaleId(Long afterSaleId, Long shopId) {
        // 检查售后是否属于商店
        Boolean b = afterSaleService.isShopOwnAfterSale(shopId, afterSaleId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
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

        Boolean shopOwnOrder = null;
        if (null != orderId) {
            // 检查订单是否属于该商店
            shopOwnOrder = orderService.isShopOwnOrder(shopId, orderId);
        }

        Boolean shopOwnAfterSale = null;
        if (null  != afterSaleId) {
            // 检查售后是否属于商店
            shopOwnAfterSale = afterSaleService.isShopOwnAfterSale(shopId, afterSaleId);
        }

        if (null == shopOwnOrder && null == shopOwnAfterSale) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        else if (null != shopOwnAfterSale && !shopOwnAfterSale) {
            return new Reply<>((ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE));
        }
        else if (null != shopOwnOrder && !shopOwnOrder) {
            return new Reply<>((ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE));
        }

        // 设置返款信息
        refund.setOrderId(orderId);
        refund.setAftersaleId(afterSaleId);

        // 创建用于返款的反向支付
        Payment payment = new Payment(r.getData().getPattern(), refund.getAmount(), orderId, afterSaleId);

        // 返款是否成功
        if (patternPayService.refundByPattern(payment)) {
            refund.setState(Refund.State.SUCCESS);
        }
        else {
            refund.setState(Refund.State.FAILED);
        }

        if (refund.isRefundSuccess()) {
            return refundDao.saveRefund(refund);
        }

        return new Reply<>(refund);
    }

    /**
     * 根据订单id 查询该订单的所有退款信息
     * @param orderId
     * @return
     */
    public Reply<List<Refund>> getRefundsByOrderId(Long orderId, Long shopId) {
        // 检查订单是否属于该商店
        Boolean b = orderService.isShopOwnOrder(shopId, orderId);
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
    public Reply<List<Refund>> getRefundsByAfterSaleId(Long afterSaleId, Long shopId) {
        // 检查售后是否属于商店
        Boolean b = afterSaleService.isShopOwnAfterSale(shopId, afterSaleId);
        if (null == b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!b) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }

        return refundDao.getRefundsByAfterSaleId(afterSaleId);
    }

}

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
 * create 2020/12/10
 * modified 2020/12/10
 */

@Service
public class ShopPaymentService {

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
     * 根据支付单id 查询该支付指向的订单id 和售后id 用于创建返款信息, 其中一个应为null
     * @param id
     * @return
     */
    public Reply<List<Long>> getOrderIdByPaymentId(Long id) {
        return paymentDao.getOrderIdByPaymentId(id);
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
     * @param refund
     * @return
     */
    public Reply<Refund> createRefund(Refund refund) {
        refund.setPaySn("refund" + UUID.randomUUID().toString());
        refund.setState(Refund.State.WAITING);

        // 根据插入数据库分配获得 id
        refund = refundDao.saveRefund(refund).getData();
        if (null == refund) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }

        // 返款是否成功
        if (externalPayment.pay(refund.getAmount()) > 0L) {
            refund.setState(Refund.State.SUCCESS);
        }
        else {
            refund.setState(Refund.State.FAILED);
        }

        if (refundDao.updateRefund(refund).isOk()) {
            return new Reply<>(refund);
        }

        return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
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

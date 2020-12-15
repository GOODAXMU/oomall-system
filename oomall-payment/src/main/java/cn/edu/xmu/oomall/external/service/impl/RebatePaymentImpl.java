package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.service.IOrderService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 * modified 2020/12/14
 */
@Component
public class RebatePaymentImpl implements IExternalPayment {

    @Autowired
    private ServiceFactory serviceFactory;

    // todo 需解决init后依然为 null
    private IOrderService orderService;
    private IAfterSaleService afterSaleService;
    private ICustomerService customerService;

    @PostConstruct
    public void init() {
        orderService = (IOrderService) serviceFactory.get(IOrderService.class);
        afterSaleService = (IAfterSaleService) serviceFactory.get(IAfterSaleService.class);
        customerService = (ICustomerService) serviceFactory.get(ICustomerService.class);
    }

    @Override
    public Boolean pay(@NotNull Payment payment) {
        Long price = payment.getActualAmount();
        Long customerId = orderService.getCustomerIdByOrderId(payment.getOrderId());

        return customerService.useRebate(customerId, price);
    }

    @Override
    public Boolean refund(@NotNull Payment payment) {
        Long amount = payment.getActualAmount();
        Long customerId = null;
        if (null != payment.getOrderId()) {
            customerId = orderService.getCustomerIdByOrderId(payment.getOrderId());
        }
        if (null != payment.getAfterSaleId()) {
            Long customerIdTemp = afterSaleService.getCustomerIdByAfterSaleId(payment.getAfterSaleId());

            // 退换支付的订单和售后对应的顾客不匹配
            if (customerId != customerIdTemp) {
                return false;
            }
        }

        // 没有顾客无从退换返点
        if (null == customerId) {
            return false;
        }

        return customerService.refundRebate(customerId, amount);
    }

    @Override
    public String getPattern() {
        return "001";
    }
}

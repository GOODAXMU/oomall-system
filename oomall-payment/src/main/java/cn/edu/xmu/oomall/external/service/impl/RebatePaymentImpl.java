package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.service.IOrderService;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 */
public class RebatePaymentImpl implements IExternalPayment {

    private IOrderService orderService;
    private IAfterSaleService afterSaleService;
    private ICustomerService customerService;

    @PostConstruct
    public void init() {
        // todo 装填
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
}
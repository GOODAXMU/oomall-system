package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * create 2020/11/26
 * modified 2020/12/15
 */

@Component
public class SimplePaymentImpl implements IExternalPayment {

    @Override
    public Boolean pay(@NotNull Payment payment) {
        return true;
    }

    @Override
    public Boolean refund(@NotNull Payment payment) {
        return true;
    }

    @Override
    public String getPattern() {
        return "002";
    }

    @Override
    public String getPatternName() {
        return "模拟支付渠道";
    }
}

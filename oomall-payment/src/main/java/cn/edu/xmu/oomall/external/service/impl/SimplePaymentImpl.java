package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * create 2020/11/26
 * modified 2020/12/14
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
    public int getPatternId() {
        return 2;
    }
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IExternalPayment;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/11/26
 * modified 2020/11/26
 */

@Component
public class SimplePaymentImpl implements IExternalPayment {

    @Override
    public Long pay(Long price) {
        return 1L;
    }
}

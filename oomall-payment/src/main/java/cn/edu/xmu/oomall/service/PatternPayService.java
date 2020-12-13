package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 * modified 2020/12/13
 */
@Component
public class PatternPayService {

    private Map<String, IExternalPayment> patternPay;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    public void init() {
        for (Map.Entry<String, Object> e : serviceFactory.getPatternPayServices().entrySet()) {
            patternPay.put(e.getKey(), (IExternalPayment)e.getValue());
        }
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行支付
     * @param payment   支付
     * @return
     */
    public Boolean payByPattern(Payment payment) {
        return patternPay
                .get(payment.getPaymentPattern().getPatternName())
                .pay(payment);
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行返款
     * @param payment   反向支付
     * @return
     */
    public Boolean refundByPattern(Payment payment) {
        return patternPay
                .get(payment.getPaymentPattern().getPatternName())
                .refund(payment);
    }

}

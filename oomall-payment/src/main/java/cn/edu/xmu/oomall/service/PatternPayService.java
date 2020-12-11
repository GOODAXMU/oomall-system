package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IExternalPayment;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 */
public class PatternPayService {

    private Map<String, IExternalPayment> patternPay;

    @PostConstruct
    public void init() {
        // todo 装填 patternPay
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行支付
     * @param payment   支付
     * @return
     */
    public Boolean payByPattern(Payment payment) {
        return patternPay.get(payment.getPaymentPattern().getPatternName()).pay(payment);
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行返款
     * @param payment   反向支付
     * @return
     */
    public Boolean refundByPattern(Payment payment) {
        return patternPay.get(payment.getPaymentPattern().getPatternName()).refund(payment);
    }

}

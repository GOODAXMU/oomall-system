package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.external.service.IExternalPayment;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 * modified 2020/12/15
 */
@Component
public class PatternPayService {

    private Map<String, IExternalPayment> patternPay = new HashMap<>();

    private Map<Integer, String> patternIds = new HashMap<>();

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    public void init() {
        for (Map.Entry<String, Object> e : serviceFactory.getPatternPayServices().entrySet()) {
            patternPay.put(e.getKey(), (IExternalPayment)e.getValue());
            patternIds.put(((IExternalPayment) e.getValue()).getPatternId(), e.getKey());
        }
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行支付
     * @param payment   支付
     * @return
     */
    public Boolean payByPattern(Payment payment) {
        return patternPay
                .get(payment.getPattern())
                .pay(payment);
    }

    /**
     * 根据payment 中的 pattern 选择支付适配器接口进行返款
     * @param payment   反向支付
     * @return
     */
    public Boolean refundByPattern(Payment payment) {
        return patternPay
                .get(payment.getPattern())
                .refund(payment);
    }

    /**
     * 获取所有可行的支付渠道
     * @return
     */
    public Map<Integer, String> getPatternIds() {
        return patternIds;
    }
}

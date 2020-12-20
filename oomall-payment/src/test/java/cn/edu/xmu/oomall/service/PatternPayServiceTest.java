package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.OomallPaymentApplication;
import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.vo.PaymentPostRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author Wang Zhizhou
 * create 2020-12-20
 */
@SpringBootTest(classes = OomallPaymentApplication.class)
@Slf4j
public class PatternPayServiceTest {

    @Autowired
    private PatternPayService patternPayService;

    /**
     * 获取当前可用的两个支付渠道与名称
     */
    @Test
    void getPattern2PatternName() {
        Map<String, String> map = patternPayService.getPattern2PatternName();
        Assert.assertEquals(map.size(), 2);
        Assert.assertEquals(map.get("001"), "返点支付");
        Assert.assertEquals(map.get("002"), "模拟支付渠道");
    }

    /**
     * 根据支付渠道进行支付
     */
    @Test
    void payByPattern() {
        PaymentPostRequest vo = new PaymentPostRequest();
        vo.setPrice(100L);
        vo.setPaymentPattern("002");

        Payment payment = new Payment(null, 142857142857L, vo);
        Boolean b = patternPayService.payByPattern(payment);
        Assert.assertTrue(b);
    }

    /**
     * 根据反向支付进行返款
     */
    @Test
    void refundByPattern() {
        Payment payment = new Payment("002", 0L, null, null);
        Boolean b = patternPayService.refundByPattern(payment);
        Assert.assertTrue(b);
    }
}

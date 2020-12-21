package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallPaymentApplication;
import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.vo.PaymentPostRequest;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST;

/**
 * @author Wang Zhizhou
 * create 2020-12-20
 */
@SpringBootTest(classes = OomallPaymentApplication.class)
@Slf4j
public class PaymentDaoTest {

    @Autowired
    private PaymentDao paymentDao;

    /**
     * 获取订单(id=47007) 的所有支付(2条)
     */
    @Test
    void getPaymentsByOrderIdTest1() {
        Long orderId = 47007L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByOrderId(orderId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 2);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 47007L);
        Assert.assertEquals(r.getData().get(1).getId().longValue(), 47008L);
    }

    /**
     * 获取订单(id=47123) 的所有支付(1条)
     */
    @Test
    void getPaymentsByOrderIdTest2() {
        Long orderId = 47123L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByOrderId(orderId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 1);
        Payment payment = r.getData().get(0);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 67840L);
    }

    /**
     * 获取订单(id=1428571428) 的所有支付(0条)
     */
    @Test
    void getPaymentsByOrderIdTest3() {
        Long orderId = 1428571428L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByOrderId(orderId);
        Assert.assertTrue(r.isOk());
        Assert.assertTrue(r.getData().isEmpty());
    }

    /**
     * 获取售后(id=47001) 的所有支付(2条)
     */
    @Test
    void getPaymentsByAfterSaleIdTest1() {
        Long aftersaleId = 47001L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByAfterSaleId(aftersaleId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 2);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 47001L);
        Assert.assertEquals(r.getData().get(1).getId().longValue(), 47002L);
    }

    /**
     * 获取售后(id=47123) 的所有支付(1条)
     */
    @Test
    void getPaymentsByAfterSaleIdTest2() {
        Long aftersaleId = 47123L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByAfterSaleId(aftersaleId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 1);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 47008L);
    }

    /**
     * 获取售后(id=1428571428) 的所有支付(0条)
     */
    @Test
    void getPaymentsByAfterSaleIdTest3() {
        Long aftersaleId = 1428571428L;
        Reply<List<Payment>> r = paymentDao.getPaymentsByAfterSaleId(aftersaleId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 0);
        Assert.assertTrue(r.getData().isEmpty());
    }

    /**
     * 获取支付(id=1428)
     */
    @Test
    void getPaymentByIdTest1() {
        Long paymentId = 1428L;
        Reply<Payment> r = paymentDao.getPaymentById(paymentId);
        Assert.assertTrue(r.isOk());
        Assert.assertFalse(null == r.getData());
    }

    /**
     * 获取支付(id=14285714), 没有此id
     */
    @Test
    void getPaymentByIdTest2() {
        Long paymentId = 14285714L;
        Reply<Payment> r = paymentDao.getPaymentById(paymentId);
        Assert.assertFalse(r.isOk());
        Assert.assertEquals(r.getResponseStatus(), RESOURCE_ID_NOT_EXIST);
    }

    /**
     * 向数据库插入支付, 为订单(id=142857142857) 创建一笔价格为 100, 通过 002 渠道的支付
     */
    @Test
    void savePaymentTest1() {
        PaymentPostRequest vo = new PaymentPostRequest();
        vo.setPrice(100L);
        vo.setPaymentPattern("002");

        Payment payment = new Payment(142857142857L, null, vo);
        Reply<Payment> r = paymentDao.savePayment(payment);

        Assert.assertTrue(r.isOk());
        Assert.assertEquals(payment.getOrderId(), r.getData().getOrderId());
        Assert.assertEquals(payment.getActualAmount(), r.getData().getActualAmount());
        Assert.assertEquals(payment.getAmount(), r.getData().getAmount());
        Assert.assertEquals(payment.getPattern(), r.getData().getPattern());
    }

    /**
     * 向数据库插入支付, 为售后(id=142857142857) 创建一笔价格为 100, 通过 002 渠道的支付
     */
    @Test
    void savePaymentTest2() {
        PaymentPostRequest vo = new PaymentPostRequest();
        vo.setPrice(100L);
        vo.setPaymentPattern("002");

        Payment payment = new Payment(null, 142857142857L, vo);
        Reply<Payment> r = paymentDao.savePayment(payment);

        Assert.assertTrue(r.isOk());
        Assert.assertEquals(payment.getAfterSaleId(), r.getData().getAfterSaleId());
        Assert.assertEquals(payment.getActualAmount(), r.getData().getActualAmount());
        Assert.assertEquals(payment.getAmount(), r.getData().getAmount());
        Assert.assertEquals(payment.getPattern(), r.getData().getPattern());
    }

    /**
     * 计算订单(id=37885) 的已支付金额
     */
    @Test
    void calcOrderPayments() {
        Long orderId = 37885L;
        Long price = paymentDao.calcOrderPayments(orderId);
        Assert.assertEquals(price.longValue(), 1998L);
    }

    /**
     * 计算售后(id=18587738957) 的已支付金额
     */
    @Test
    void calcAfterSalePayments() {
        Long aftersaleId = 18587738957L;
        Long price = paymentDao.calcAfterSalePayments(aftersaleId);
        Assert.assertEquals(price.longValue(), 3500L);
    }
}

package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallPaymentApplication;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.vo.RefundPostRequest;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wang Zhizhou
 * create 2020-12-20
 */
@SpringBootTest(classes = OomallPaymentApplication.class)
@Slf4j
class RefundDaoTest {

    @Autowired
    private RefundDao refundDao;

    /**
     * 查询订单(id=1) 的返款(1条)
     */
    @Test
    void getRefundsByOrderIdTest1() {
        Long orderId = 1L;
        Reply<List<Refund>> r = refundDao.getRefundsByOrderId(orderId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 1);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 1L);
    }

    /**
     * 查询订单(id=1428) 的返款(0条)
     */
    @Test
    void getRefundsByOrderIdTest2() {
        Long orderId = 1428L;
        Reply<List<Refund>> r = refundDao.getRefundsByOrderId(orderId);
        Assert.assertTrue(r.isOk());
        Assert.assertTrue(r.getData().isEmpty());
    }

    /**
     * 查询售后(id=1) 的返款(1条)
     */
    @Test
    void getRefundsByAfterSaleIdTest1() {
        Long afterSaleId = 1L;
        Reply<List<Refund>> r = refundDao.getRefundsByAfterSaleId(afterSaleId);
        Assert.assertTrue(r.isOk());
        Assert.assertEquals(r.getData().size(), 1);
        Assert.assertEquals(r.getData().get(0).getId().longValue(), 1L);
    }

    /**
     * 查询售后(id=1428) 的返款(0条)
     */
    @Test
    void getRefundsByAfterSaleIdTest2() {
        Long afterSaleId = 1428L;
        Reply<List<Refund>> r = refundDao.getRefundsByAfterSaleId(afterSaleId);
        Assert.assertTrue(r.isOk());
        Assert.assertTrue(r.getData().isEmpty());
    }

    /**
     * 为支付(id=12345) 创建金额为 0 的返款
     */
    @Test
    void saveRefund() {
        RefundPostRequest vo = new RefundPostRequest();
        vo.setAmount(0L);

        Refund refund = new Refund(12345L, vo);
        Reply<Refund> r = refundDao.saveRefund(refund);

        Assert.assertTrue(r.isOk());
        Assert.assertEquals(refund.getPaymentId(), r.getData().getPaymentId());
        Assert.assertEquals(refund.getAmount(), r.getData().getAmount());
    }
}
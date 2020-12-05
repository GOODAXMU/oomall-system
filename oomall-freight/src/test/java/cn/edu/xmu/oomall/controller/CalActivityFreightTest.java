package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.service.FreightService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhibin lan
 * @date 2020-11-24
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
public class CalActivityFreightTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    FreightService freightService;

    @Test
    public void calActivityFreight() throws Exception{
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setSkuId(Long.valueOf(1));
        purchaseItem.setCount(1);
        Long rid = Long.valueOf(200);
        Long result = freightService.calActivityFreight(purchaseItem,rid).getData();
        Assert.assertEquals(result,Long.valueOf(11));


    }
}

package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.repository.FreightModelRepository;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author zhibin lan
 * @date 2020-11-24
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class CreateFreightModelTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private FreightDao freightDao;

    @Autowired
    private FreightModelRepository freightModelRepository;

    @Test
    public void createFreight() throws Exception{
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setName("测试名2");
        freightModelPo.setWeightUnit(Long.valueOf(500));
        freightModelPo.setGmtCreated(LocalDateTime.now());
        freightModelPo.setGmtModified(LocalDateTime.now());
        log.debug(freightModelPo.toString());

        Reply<FreightModelPo> reply =  freightDao.createFreightModel(freightModelPo);
        log.info("reply: "+reply.toString());
        Assert.assertEquals(Long.valueOf(2),reply.getData().getId());
    }
}

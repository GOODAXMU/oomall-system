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
import org.skyscreamer.jsonassert.JSONAssert;
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
        FreightModel freightModel = new FreightModel();
        freightModel.setName("测试名2");
        log.debug(freightModel.toString());

        Reply<FreightModel> reply =  freightDao.createFreightModel(freightModel);
        log.info("reply: "+reply.toString());
        Assert.assertEquals(Long.valueOf(2),reply.getData().getId());
    }

    @Test
    public void assertTest() throws Exception{
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":2,\"total\":4,\"pages\":2,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        String response1 = "{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":2,\"total\":4,\"pages\":2,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        String response2 = "{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":3,\"total\":4,\"pages\":2,\"list\":[{\"id\":9,\"name\":\"测试模板\",\"type\":0,\"defaultModel\":true,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":10,\"name\":\"测试模板2\",\"type\":0,\"defaultModel\":false,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}}";
        JSONAssert.assertEquals(expectedResponse,response1,false);
        JSONAssert.assertEquals(expectedResponse,response2,false);
    }
}

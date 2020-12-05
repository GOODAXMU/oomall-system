package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.freight.test.util.JwtHelper;
import cn.edu.xmu.oomall.vo.FreightModelPutRequest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhibin lan
 * @date 2020-11-28
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class ModifyFreightModelTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void modifyFreightModel() throws Exception{
        String token = creatTestToken(1L,1L,1000);
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("测试名");
        freightModelPutRequest.setUnit(Long.valueOf(500));
        String json = JSON.toJSONString(freightModelPutRequest);
        String responseString = this.mvc.perform(put("/shops/1/freightmodels/200").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    public void modifyFreightModel1() throws Exception{
        String token = creatTestToken(1L,1L,1000);
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("测试名");
        freightModelPutRequest.setUnit(Long.valueOf(550));
        String json = JSON.toJSONString(freightModelPutRequest);
        String responseString = this.mvc.perform(put("/shops/1/freightmodels/9").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    public void modifyFreightModel2() throws Exception{
        String token = creatTestToken(1L,1L,1000);
        FreightModelPutRequest freightModelPutRequest = new FreightModelPutRequest();
        freightModelPutRequest.setName("测试模板");
        freightModelPutRequest.setUnit(Long.valueOf(550));
        String json = JSON.toJSONString(freightModelPutRequest);
        String responseString = this.mvc.perform(put("/shops/1/freightmodels/9").header("authorization",token)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"code\":802,\"message\":\"运费模板名重复\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }


    private final String creatTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        log.debug(token);
        return token;
    }
}

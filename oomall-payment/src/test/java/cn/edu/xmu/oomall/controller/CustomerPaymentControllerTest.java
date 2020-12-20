package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallPaymentApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Wang Zhizhou
 * create 2020-12-16
 */
@SpringBootTest(classes = OomallPaymentApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerPaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * 获得支付的所有状态
     * @throws Exception
     */
    @Test
    public void getPaymentStatesTest() throws Exception {
        String response = mvc.perform(get("/payments/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"code\":0,\"name\":\"未支付\"},{\"code\":1,\"name\":\"已支付\"},{\"code\":2,\"name\":\"支付失败\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获得所有可使用的支付渠道
     * @throws Exception
     */
    @Test
    public void getAllPatternTest() throws Exception {
        String response = mvc.perform(get("/payments/patterns"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"payPattern\":\"001\",\"name\":\"返点支付\"},{\"payPattern\":\"002\",\"name\":\"模拟支付渠道\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

}
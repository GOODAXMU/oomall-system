package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallPaymentApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wang Zhizhou
 * create 2020-12-15
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

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"payPattern\":\"001\",\"name\":\"RebatePayment\"},{\"payPattern\":\"002\",\"name\":\"SimplePayment\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id = 1)下的所有支付
     * @throws Exception
     */
    @Test
    public void getPaymentTest1() throws Exception {
        String response = mvc.perform(get("/orders/{id}/payments", 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":1,\"orderId\":1,\"aftersaleId\":null,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-10T19:29:50\",\"payPattern\":\"0\",\"state\":0,\"beginTime\":\"2020-12-10T19:29:50\",\"endTime\":\"2020-12-10T19:29:50\",\"gmtCreateTime\":\"2020-12-10T19:29:50\",\"gmtModifiedTime\":\"2020-12-10T19:29:50\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id = 23)下的所有支付, 实际上该订单不存在任何支付
     * @throws Exception
     */
    @Test
    public void getPaymentTest2() throws Exception {
        String response = mvc.perform(get("/orders/{id}/payments", 23L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    // todo 创建支付与查询返款的 Test (sql缺失)
}
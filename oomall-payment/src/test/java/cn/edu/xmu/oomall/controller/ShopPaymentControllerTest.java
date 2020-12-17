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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wang Zhizhou
 * create 2020-12-15
 */
@SpringBootTest(classes = OomallPaymentApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ShopPaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getPaymentTest1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}/payments", 1L, 1L)
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
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}/payments", 1L, 23L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }


    /**
     * 获取售后(id=1)下的所有支付(2条)
     * @throws Exception
     */
    @Test
    public void getAfterSalePaymentTest1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/payments", 1L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":30000,\"orderId\":null,\"aftersaleId\":1,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T19:29:50\",\"payPattern\":\"002\",\"state\":0,\"beginTime\":\"2020-12-15T19:29:50\",\"endTime\":\"2020-12-15T19:29:50\",\"gmtCreateTime\":\"2020-12-15T19:29:50\",\"gmtModifiedTime\":\"2020-12-15T19:29:50\"},{\"id\":30001,\"orderId\":null,\"aftersaleId\":1,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T20:03:21\",\"payPattern\":\"001\",\"state\":0,\"beginTime\":\"2020-12-15T20:03:21\",\"endTime\":\"2020-12-15T20:03:21\",\"gmtCreateTime\":\"2020-12-15T20:03:21\",\"gmtModifiedTime\":\"2020-12-15T20:03:21\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=10)下的所有支付(1条)
     * @throws Exception
     */
    @Test
    public void getAfterSalePaymentTest2() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/payments", 1L, 10L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":30004,\"orderId\":null,\"aftersaleId\":10,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T23:34:10\",\"payPattern\":\"002\",\"state\":0,\"beginTime\":\"2020-12-15T23:34:10\",\"endTime\":\"2020-12-15T23:34:10\",\"gmtCreateTime\":\"2020-12-15T23:34:10\",\"gmtModifiedTime\":\"2020-12-15T23:34:10\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=11)下的所有支付(0条)
     * @throws Exception
     */
    @Test
    public void getAfterSalePaymentTest3() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/payments", 1L, 11L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id=1)下的所有返款(2条)
     * @throws Exception
     */
    @Test
    public void getOrderRefund1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}/refund", 1L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":100,\"paymentId\":1,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T19:29:50\",\"gmtModifiedTime\":\"2020-12-15T19:29:50\",\"orderId\":1,\"aftersaleId\":null},{\"id\":101,\"paymentId\":1,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T19:30:45\",\"gmtModifiedTime\":\"2020-12-15T19:30:45\",\"orderId\":1,\"aftersaleId\":null}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id=100)下的所有返款(0条)
     * @throws Exception
     */
    @Test
    public void getOrderRefund2() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}/refund", 1L, 100L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=10)下的所有返款(1条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefund1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/refund", 1L, 10L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":105,\"paymentId\":30004,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T23:13:43\",\"gmtModifiedTime\":\"2020-12-15T23:13:43\",\"orderId\":null,\"aftersaleId\":10}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=1)下的所有返款(2条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefund2() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/refund", 1L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":102,\"paymentId\":30000,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T20:21:22\",\"gmtModifiedTime\":\"2020-12-15T20:21:22\",\"orderId\":null,\"aftersaleId\":1},{\"id\":103,\"paymentId\":30001,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T22:00:24\",\"gmtModifiedTime\":\"2020-12-15T22:00:24\",\"orderId\":null,\"aftersaleId\":1}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=11)下的所有返款(0条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefund3() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/aftersales/{id}/refund", 1L, 11L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }
}
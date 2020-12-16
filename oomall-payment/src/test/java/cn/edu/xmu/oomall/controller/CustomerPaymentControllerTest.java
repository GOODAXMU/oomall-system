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

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"payPattern\":\"001\",\"name\":\"RebatePayment\"},{\"payPattern\":\"002\",\"name\":\"SimplePayment\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id = 1)下的所有支付
     * @throws Exception
     */
    @Test
    public void getOrderPaymentTest1() throws Exception {
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
    public void getOrderPaymentTest2() throws Exception {
        String response = mvc.perform(get("/orders/{id}/payments", 23L)
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
        String response = mvc.perform(get("/aftersales/{id}/payments", 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":30000,\"orderId\":null,\"aftersaleId\":1,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T19:29:50\",\"payPattern\":\"002\",\"state\":0,\"beginTime\":\"2020-12-15T19:29:50\",\"endTime\":\"2020-12-15T19:29:50\",\"gmtCreateTime\":\"2020-12-15T19:29:50\",\"gmtModifiedTime\":\"2020-12-15T19:29:50\"},{\"id\":30001,\"orderId\":null,\"aftersaleId\":1,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T20:03:21\",\"payPattern\":\"001\",\"state\":0,\"beginTime\":\"2020-12-15T20:03:21\",\"endTime\":\"2020-12-15T20:03:21\",\"gmtCreateTime\":\"2020-12-15T20:03:21\",\"gmtModifiedTime\":\"2020-12-15T20:03:21\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=2)下的所有支付(1条)
     * @throws Exception
     */
    @Test
    public void getAfterSalePaymentTest2() throws Exception {
        String response = mvc.perform(get("/aftersales/{id}/payments", 2L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":30002,\"orderId\":null,\"aftersaleId\":2,\"amount\":0,\"actualAmount\":0,\"payTime\":\"2020-12-15T21:34:20\",\"payPattern\":\"002\",\"state\":0,\"beginTime\":\"2020-12-15T21:34:20\",\"endTime\":\"2020-12-15T21:34:20\",\"gmtCreateTime\":\"2020-12-15T21:34:20\",\"gmtModifiedTime\":\"2020-12-15T21:34:20\"}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=3)下的所有支付(0条)
     * @throws Exception
     */
    @Test
    public void getAfterSalePaymentTest3() throws Exception {
        String response = mvc.perform(get("/aftersales/{id}/payments", 3L)
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
    public void getOrderRefundTest1() throws Exception {
        String response = mvc.perform(get("/orders/{id}/refunds", 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":100,\"paymentId\":1,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T19:29:50\",\"gmtModifiedTime\":\"2020-12-15T19:29:50\",\"orderId\":1,\"aftersaleId\":null},{\"id\":101,\"paymentId\":1,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T19:30:45\",\"gmtModifiedTime\":\"2020-12-15T19:30:45\",\"orderId\":1,\"aftersaleId\":null}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取订单(id=10)下的所有返款(0条)
     * @throws Exception
     */
    @Test
    public void getOrderRefundTest2() throws Exception {
        String response = mvc.perform(get("/orders/{id}/refunds", 10L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=5)下的所有返款(1条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefundTest1() throws Exception {
        String response = mvc.perform(get("/aftersales/{id}/refunds", 5L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":104,\"paymentId\":30003,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T22:34:56\",\"gmtModifiedTime\":\"2020-12-15T22:34:56\",\"orderId\":null,\"aftersaleId\":5}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=1)下的所有返款(2条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefundTest2() throws Exception {
        String response = mvc.perform(get("/aftersales/{id}/refunds", 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":102,\"paymentId\":30000,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T20:21:22\",\"gmtModifiedTime\":\"2020-12-15T20:21:22\",\"orderId\":null,\"aftersaleId\":1},{\"id\":103,\"paymentId\":30001,\"amount\":0,\"state\":\"未退款\",\"gmtCreateTime\":\"2020-12-15T22:00:24\",\"gmtModifiedTime\":\"2020-12-15T22:00:24\",\"orderId\":null,\"aftersaleId\":1}]}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 获取售后(id=7)下的所有返款(0条)
     * @throws Exception
     */
    @Test
    public void getAfterSaleRefundTest3() throws Exception {
        String response = mvc.perform(get("/aftersales/{id}/refunds", 7L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 为订单(id=25)创建普通支付
     * @throws Exception
     */
    @Test
    public void createPaymentTest1() throws Exception {
        String response = mvc.perform(post("/orders/{id}/payments", 25L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"price\": 0,\"paymentPattern\": \"002\"}")
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 为订单(id=25)创建返点支付
     * @throws Exception
     */
    @Test
    public void createPaymentTest2() throws Exception {
        String response = mvc.perform(post("/orders/{id}/payments", 25L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"price\": 0,\"paymentPattern\": \"001\"}")
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
}
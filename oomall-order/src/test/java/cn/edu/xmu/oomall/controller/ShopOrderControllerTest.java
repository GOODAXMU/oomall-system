package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OomallOrderApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ShopOrderControllerTest {

    @Autowired
    private MockMvc mvc;


    /**
     * 1 获取商铺订单概要测试1
     * 正常访问本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(0)
    public void getAllShopOrdersTest1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?pageSize=2", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"page\":1,\"pageSize\":2,\"total\":26,\"pages\":13,\"list\":[" +
                "{\"id\":240000,\"customerId\":2830,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240001,\"customerId\":4298,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 2 获取商铺订单概要测试2
     * 正常访问本商铺的订单（分页）
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    public void getAllShopOrdersTest2() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?page=2&pageSize=2", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"page\":2,\"pageSize\":2,\"total\":26,\"pages\":13,\"list\":[" +
                "{\"id\":240002,\"customerId\":5344,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240003,\"customerId\":2830,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 3 获取商铺订单概要测试3
     * 正常访问本商铺的订单（分页大小）
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    public void getAllShopOrdersTest3() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?page=2&pageSize=5", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"page\":2,\"pageSize\":5,\"total\":26,\"pages\":6,\"list\":[" +
                "{\"id\":240005,\"customerId\":5344,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240006,\"customerId\":2830,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240007,\"customerId\":4298,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240008,\"customerId\":5344,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}," +
                "{\"id\":240009,\"customerId\":2830,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}]}}";
        Assert.assertEquals(expectedResponse, response);

    }

    /**
     * 4 获取商铺订单概要测试4
     * 正常访问本商铺的订单（订单号）
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    public void getAllShopOrdersTest4() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?orderSn=2020121229742&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"page\":1,\"pageSize\":1,\"total\":1,\"pages\":1,\"list\":[" +
                "{\"id\":240025,\"customerId\":7,\"shopId\":3," +
                "\"pid\":null,\"orderType\":0,\"state\":3,\"subState\":24,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 5 获取商铺订单概要测试5
     * 访问非本商铺的订单（订单号）
     *
     * @throws Exception
     */
    @Test
    @Order(4)
    public void getAllShopOrdersTest5() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?orderSn=2019071257669&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":1,\"total\":0,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 6 获取商铺订单概要测试6
     * 访问不存在的订单（订单号）
     *
     * @throws Exception
     */
    @Test
    @Order(5)
    public void getAllShopOrdersTest6() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?orderSn=20190712576690000&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":1,\"total\":0,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 7 获取商铺订单概要测试7
     * 正常访问本商铺的订单（顾客id）
     *
     * @throws Exception
     */
    @Test
    @Order(6)
    public void getAllShopOrdersTest7() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?customerId=7&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"page\":1,\"pageSize\":1,\"total\":3,\"pages\":3,\"list\":[" +
                "{\"id\":240022,\"customerId\":7,\"shopId\":3,\"pid\":null,\"orderType\":0," +
                "\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\",\"originPrice\":null," +
                "\"discountPrice\":null,\"freightPrice\":null," +
                "\"grouponId\":null,\"presaleId\":null,\"shipmentSn\":null}]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 8 获取商铺订单概要测试8
     * 访问非本商铺的订单（顾客id）
     * （顾客没下过本商铺的订单，但下过其他的订单）
     *
     * @throws Exception
     */
    @Test
    @Order(7)
    public void getAllShopOrdersTest8() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?customerId=734&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":1,\"total\":0,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 9 获取商铺订单概要测试9
     * 访问非本商铺的订单（顾客id+订单序列号）
     * （订单序列号存在但不属于该顾客）
     *
     * @throws Exception
     */
    @Test
    @Order(8)
    public void getAllShopOrdersTest9() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?customerId=734&orderSn=2019121224844&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":1,\"total\":0,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 10 获取商铺订单概要测试10
     * 访问非本商铺的订单（顾客id+订单序列号）
     * （订单序列号存在且属于该顾客但不属于本商铺）
     *
     * @throws Exception
     */
    @Test
    @Order(9)
    public void getAllShopOrdersTest10() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders?customerId=5344&orderSn=2019121224844&pageSize=1", 3)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":1,\"total\":0,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 11 获取商铺订单详细内容测试1
     * 访问不存在的订单
     *
     * @throws Exception
     */
    @Test
    @Order(10)
    public void getShopOrderDetailsTest1() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 666666L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 12 获取商铺订单详细内容测试2
     * 访问不属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(11)
    public void getShopOrderDetailsTest2() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 2L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 13 获取商铺订单详细内容测试3
     * 访问属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(12)
    public void getShopOrderDetailsTest3() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 240000L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":240000," +
                "\"orderSn\":\"2020112681600\"," +
                "\"customer\":{\"id\":2830,\"userName\":\"abee\",\"name\":\"wilson\"}," +
                "\"shop\":{\"id\":345,\"name\":\"super shop\",\"state\":null," +
                "\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"}," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"gmtModified\":\"2020-12-10T19:29:33\",\"confirmTime\":null," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"rebateNum\":null,\"message\":null," +
                "\"regionId\":null,\"address\":null,\"mobile\":\"13959288888\",\"consignee\":\"刘慧\"," +
                "\"couponId\":null,\"grouponId\":null," +
                "\"presaleId\":null,\"shipmentSn\":null,\"orderItems\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 14 店家留言测试1
     * 访问不存在的订单
     *
     * @throws Exception
     */
    @Test
    @Order(13)
    public void addShopOrderMessageTest1() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}", 3L, 666666L)
                .content("{\"message\": \"6666\"}").contentType("application/json")
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 15 店家留言测试2
     * 访问不属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(14)
    public void addShopOrderMessageTest2() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}", 3L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"message\": \"6666\"}").contentType("application/json"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 16 店家留言测试3
     * 访问属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(15)
    public void addShopOrderMessageTest3() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}", 3L, 240020L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"message\": \"6666\"}").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        Assert.assertEquals(expectedResponse, response);

        response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 240020L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"id\":240020,\"orderSn\":\"2020112681717\"," +
                "\"customer\":{\"id\":4298,\"userName\":\"abee\",\"name\":\"wilson\"}," +
                "\"shop\":{\"id\":345,\"name\":\"super shop\",\"state\":null," +
                "\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"}," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"gmtModified\":\"2020-12-10T19:29:33\",\"confirmTime\":null," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"rebateNum\":null,\"message\":\"6666\"," +
                "\"regionId\":null,\"address\":null,\"mobile\":\"13959288888\",\"consignee\":\"刘媛媛\"," +
                "\"couponId\":null,\"grouponId\":null," +
                "\"presaleId\":null,\"shipmentSn\":null,\"orderItems\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }


    /**
     * 17 店家取消订单测试1
     * 访问不存在的订单
     *
     * @throws Exception
     */
    @Test
    @Order(16)
    public void cancelShopOrderTest1() throws Exception {
        String response = mvc.perform(delete("/shops/{shopId}/orders/{id}", 3L, 66666L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";

        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 18 店家取消订单测试2
     * 访问不属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(17)
    public void cancelShopOrderTest2() throws Exception {
        String response = mvc.perform(delete("/shops/{shopId}/orders/{id}", 3L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        Assert.assertEquals(expectedResponse, response);

    }

    /**
     * 19 店家取消订单测试3
     * 访问属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(18)
    public void cancelShopOrderTest3() throws Exception {
        String response = mvc.perform(delete("/shops/{shopId}/orders/{id}", 3L, 240021L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        Assert.assertEquals(expectedResponse, response);

        response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 240021L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{" +
                "\"id\":240021,\"orderSn\":\"2020112681718\"," +
                "\"customer\":{\"id\":1,\"userName\":\"abee\",\"name\":\"wilson\"}," +
                "\"shop\":{\"id\":345,\"name\":\"super shop\",\"state\":null," +
                "\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"}," +
                "\"pid\":null,\"orderType\":0,\"state\":4,\"subState\":21,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"gmtModified\":\"2020-12-10T19:29:33\",\"confirmTime\":null," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"rebateNum\":null,\"message\":null," +
                "\"regionId\":null,\"address\":null,\"mobile\":\"13959288888\",\"consignee\":\"刘勤\"," +
                "\"couponId\":null,\"grouponId\":null," +
                "\"presaleId\":null,\"shipmentSn\":null,\"orderItems\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 20 店家取消订单测试4
     * 访问属于本商铺的订单
     * 但当前订单状态为“已完成”
     *
     * @throws Exception
     */
    @Test
    @Order(19)
    public void cancelShopOrderTest4() throws Exception {
        String response = mvc.perform(delete("/shops/{shopId}/orders/{id}", 3L, 240025L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":801,\"errmsg\":\"订单状态禁止\"}";

        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 21 店家标记订单发货1
     * 访问不存在的订单
     *
     * @throws Exception
     */
    @Test
    @Order(20)
    public void markShopOrderDeliverTest1() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}/deliver", 3L, 66666666L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"freightSn\": \"8888\"}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";

        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 22 店家取消订单测试2
     * 访问不属于本商铺的订单
     *
     * @throws Exception
     */
    @Test
    @Order(21)
    public void markShopOrderDeliverTest2() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}/deliver", 3L, 1L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"freightSn\": \"8888\"}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();


        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 23 店家取消订单测试3
     * 访问属于本商铺的订单
     * （但是当前订单状态并非“付款完成”）
     *
     * @throws Exception
     */
    @Test
    @Order(22)
    public void markShopOrderDeliverTest3() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}/deliver", 3L, 240024L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"freightSn\": \"8888\"}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":801,\"errmsg\":\"订单状态禁止\"}";

        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 24 店家取消订单测试4
     * 访问属于本商铺的订单
     * 当前订单状态为“付款完成”
     *
     * @throws Exception
     */
    @Test
    @Order(23)
    public void markShopOrderDeliverTest4() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}/deliver", 3L, 240019L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
                .content("{\"freightSn\": \"8888\"}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        Assert.assertEquals(expectedResponse, response);

        response = mvc.perform(get("/shops/{shopId}/orders/{id}", 3L, 240019L)
                .header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":240019," +
                "\"orderSn\":\"2020112681716\"," +
                "\"customer\":{\"id\":2830,\"userName\":\"abee\",\"name\":\"wilson\"}," +
                "\"shop\":{\"id\":345,\"name\":\"super shop\",\"state\":null," +
                "\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"}," +
                "\"pid\":null,\"orderType\":0,\"state\":2,\"subState\":24,\"gmtCreate\":\"2020-12-10T19:29:33\"," +
                "\"gmtModified\":\"2020-12-10T19:29:33\",\"confirmTime\":null," +
                "\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null," +
                "\"rebateNum\":null,\"message\":null," +
                "\"regionId\":null,\"address\":null,\"mobile\":\"13959288888\",\"consignee\":\"刘慧\"," +
                "\"couponId\":null,\"grouponId\":null," +
                "\"presaleId\":null,\"shipmentSn\":\"8888\",\"orderItems\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

}

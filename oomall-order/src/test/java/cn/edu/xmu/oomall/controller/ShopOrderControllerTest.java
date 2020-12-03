package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderApplication;
import org.junit.Assert;
import org.junit.Test;
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

    //手动更改id=2和id=7的订单的shop_id=1

    @Test
    public void getAllShopOrdersTest() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"page\":1,\"pageSize\":20,\"total\":2,\"pages\":0,\"list\":[]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void getShopOrderDetailsTest() throws Exception {
        String response = mvc.perform(get("/shops/{shopId}/orders/{id}", 1L, 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);

        String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":2,\"customer\":{\"id\":2,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":1,\"name\":\"super shop\",\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"},\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null,\"message\":null,\"regionId\":null,\"address\":null,\"mobile\":\"13959288888\",\"consignee\":\"刘恩羽\",\"couponId\":null,\"grouponId\":null,\"orderItems\":[{\"skuId\":185,\"orderId\":2,\"name\":null,\"quantity\":1,\"price\":4475,\"discount\":null,\"couponActivityId\":null,\"beShareId\":null}]}}";
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void addShopOrderMessageTest() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}", 1L, 1L)
                .content("{\"message\": \"\"}").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\",\"data\":null}";

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void cancelShopOrderTest() throws Exception {
        String response = mvc.perform(delete("/shops/{shopId}/orders/{id}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\",\"data\":null}";

        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void markShopOrderDeliverTest() throws Exception {
        String response = mvc.perform(put("/shops/{shopId}/orders/{id}/deliver", 1L, 1L)
                .content("{\"freightSn\": \"\"}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\",\"data\":null}";

        Assert.assertEquals(expectedResponse, response);
    }


}

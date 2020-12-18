package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yan song
 * @date 2020-11-30
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class GetPieceFreightModelTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getPieceFreightModels() throws Exception{
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        String response = mvc.perform(get("/shops/1/freightmodels/2/pieceItems").header("authorization",token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";

        Assert.assertEquals(expectedResponse, response);
    }

    /**
     * 测试获取重量运费模板明细
     * 成功
     * @author zhibin lan
     * @throws Exception
     */
    @Test
    public void getPieceFreightModels1() throws Exception{
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        String responseString = mvc.perform(get("/shops/1/freightmodels/11/pieceItems").header("authorization",token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"id\":206,\"regionId\":200,\"firstItem\":1,\"firstItemPrice\":50,\"additionalItems\":1,\"additionalItemsPrice\":60,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":207,\"regionId\":201,\"firstItem\":1,\"firstItemPrice\":20,\"additionalItems\":2,\"additionalItemsPrice\":12,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"},{\"id\":208,\"regionId\":202,\"firstItem\":2,\"firstItemPrice\":30,\"additionalItems\":1,\"additionalItemsPrice\":20,\"gmtCreate\":\"2020-12-02T20:33:08\",\"gmtModified\":\"2020-12-02T20:33:08\"}]}";
        System.out.println("responseString :" + responseString );
        Assert.assertEquals(expectedResponse, responseString);
    }

    /**
     * 测试获取重量运费模板明细
     * 操作资源id不是自己的对象
     * @author yan song
     * @throws Exception
     */
    @Test
    public void getPieceFreightModels2() throws Exception{
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        String responseString = mvc.perform(get("/shops/2/freightmodels/11/pieceItems").header("authorization",token))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":505}";
        System.out.println("responseString :" + responseString );
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
}

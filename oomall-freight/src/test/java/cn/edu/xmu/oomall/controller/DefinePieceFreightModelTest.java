package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.FreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.PieceItemRequest;
import com.alibaba.fastjson.JSON;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class DefinePieceFreightModelTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 测试定义计件模板功能
     *
     * @throws Exception
     */
    @Test
    public void definePieceFreightModel() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        PieceItemRequest pieceItemRequest = new PieceItemRequest();
        pieceItemRequest.setAdditionalItemPrice(1L);
        pieceItemRequest.setAdditionalItems(10);
        pieceItemRequest.setFirstItem(1);
        pieceItemRequest.setRegionId(201L);
        pieceItemRequest.setFirstItemPrice(1L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/9/pieceItems")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":803,\"errmsg\":\"运费模板中该地区已经定义\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
    @Test
    public void definePieceFreightModelSuccessfully() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        PieceItemRequest pieceItemRequest = new PieceItemRequest();
        pieceItemRequest.setAdditionalItemPrice(1L);
        pieceItemRequest.setAdditionalItems(10);
        pieceItemRequest.setFirstItem(1);
        pieceItemRequest.setRegionId(2L);
        pieceItemRequest.setFirstItemPrice(1L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/9/pieceItems").header("authorization", token)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
    @Test
    public void definePieceFreightModelOutOfScope() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";

        PieceItemRequest pieceItemRequest = new PieceItemRequest();
        pieceItemRequest.setAdditionalItemPrice(1L);
        pieceItemRequest.setAdditionalItems(10);
        pieceItemRequest.setFirstItem(1);
        pieceItemRequest.setRegionId(2L);
        pieceItemRequest.setFirstItemPrice(1L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/13/pieceItems").header("authorization", token)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
}

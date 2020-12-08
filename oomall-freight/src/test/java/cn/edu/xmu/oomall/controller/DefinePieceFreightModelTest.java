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
    public void defineWeightFreightModel() throws Exception {
        PieceItemRequest pieceItemRequest = new PieceItemRequest();
        pieceItemRequest.setAdditionalItemPrice(1L);
        pieceItemRequest.setAdditionalItems(10);
        pieceItemRequest.setFirstItem(1);
        pieceItemRequest.setRegionId(1L);
        pieceItemRequest.setFirstItemPrice(1L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/1/pieceItems")
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
}

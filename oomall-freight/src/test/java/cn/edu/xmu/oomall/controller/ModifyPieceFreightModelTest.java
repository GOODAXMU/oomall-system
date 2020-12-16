package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.PieceFreightModelModifyRequest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class ModifyPieceFreightModelTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void modifyPieceFreightModel() throws Exception{
        PieceFreightModelModifyRequest pieceItemRequest = new PieceFreightModelModifyRequest();
        pieceItemRequest.setAdditionalItemsPrice(1L);
        pieceItemRequest.setAdditionalItems(10);
        pieceItemRequest.setFirstItem(1);
        pieceItemRequest.setRegionId(1L);
        pieceItemRequest.setFirstItemPrice(1L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(put("/shops/1/pieceItems/201")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    public void modifyPieceFreightModel1() throws Exception{
        PieceFreightModelModifyRequest pieceItemRequest = new PieceFreightModelModifyRequest();
        pieceItemRequest.setRegionId(210L);
        pieceItemRequest.setFirstItem(3);
        pieceItemRequest.setFirstItemPrice(12L);
        pieceItemRequest.setAdditionalItems(2);
        pieceItemRequest.setAdditionalItemsPrice(16L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(put("/shops/1/pieceItems/10000")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    public void modifyPieceFreightModel2() throws Exception{
        PieceFreightModelModifyRequest pieceItemRequest = new PieceFreightModelModifyRequest();
        pieceItemRequest.setRegionId(210L);
        pieceItemRequest.setFirstItem(3);
        pieceItemRequest.setFirstItemPrice(12L);
        pieceItemRequest.setAdditionalItems(2);
        pieceItemRequest.setAdditionalItemsPrice(16L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(put("/shops/1/pieceItems/209")
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
    @Test
    public void modifyPieceFreightModel3() throws Exception{
        PieceFreightModelModifyRequest pieceItemRequest = new PieceFreightModelModifyRequest();
        pieceItemRequest.setRegionId(210L);
        pieceItemRequest.setFirstItem(3);
        pieceItemRequest.setFirstItemPrice(12L);
        pieceItemRequest.setAdditionalItems(2);
        pieceItemRequest.setAdditionalItemsPrice(16L);
        String json = JSON.toJSONString(pieceItemRequest);
        String responseString = this.mvc.perform(put("/shops/1/pieceItems/209")
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

    @Test
    public void modifyPieceFreightModel6() throws Exception {
        String json = "{\"additionalItemPrice\":16,\"additionalItems\":2,\"firstItem\":3,\"firstItemPrice\":12,\"regionId\":200}";
        String responseString = this.mvc.perform(put("/shops/1/pieceItems/201")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":803}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
}

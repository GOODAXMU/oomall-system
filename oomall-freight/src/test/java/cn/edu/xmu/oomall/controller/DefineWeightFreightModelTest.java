package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.FreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.WeightFreightModelDefineRequest;
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
public class DefineWeightFreightModelTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 测试定义重量模板功能
     *
     * @throws Exception
     */
    @Test
    public void defineWeightFreightModel() throws Exception {
        WeightFreightModelDefineRequest weightFreightModelDefineRequest = new WeightFreightModelDefineRequest();
        weightFreightModelDefineRequest.setAbovePrice(200L);
        weightFreightModelDefineRequest.setFiftyPrice(100L);
        weightFreightModelDefineRequest.setFirstWeight(1L);
        weightFreightModelDefineRequest.setFirstWeightFreight(1L);
        weightFreightModelDefineRequest.setHundredPrice(200L);
        weightFreightModelDefineRequest.setRegionId(201L);
        weightFreightModelDefineRequest.setTenPrice(10L);
        weightFreightModelDefineRequest.setTrihunPrice(300L);
        String json = JSON.toJSONString(weightFreightModelDefineRequest);
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/9/weightItems")
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":803,\"errmsg\":\"运费模板中该地区已经定义\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
    @Test
    public void defineWeightFreightModelSuccessfully() throws Exception {
        String json = "{\n" +
                "  \"abovePrice\": 0,\n" +
                "  \"fiftyPrice\": 0,\n" +
                "  \"firstWeight\": 0,\n" +
                "  \"firstWeightFreight\": 0,\n" +
                "  \"hundredPrice\": 0,\n" +
                "  \"regionId\": 0,\n" +
                "  \"tenPrice\": 0,\n" +
                "  \"trihunPrice\": 0\n" +
                "}";
        String responseString = this.mvc.perform(post("/shops/{shopId}/freightmodels/{id}/weightItems",47002,47012)
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
    public void defineWeightFreightModelOutOfScope() throws Exception {
        String json = "{\n" +
                "  \"abovePrice\": 0,\n" +
                "  \"fiftyPrice\": 0,\n" +
                "  \"firstWeight\": 0,\n" +
                "  \"firstWeightFreight\": 0,\n" +
                "  \"hundredPrice\": 0,\n" +
                "  \"regionId\": 0,\n" +
                "  \"tenPrice\": 0,\n" +
                "  \"trihunPrice\": 0\n" +
                "}";
        String responseString = this.mvc.perform(post("/shops/{shopId}/freightmodels/{id}/weightItems",47012,47012)
                .contentType("application/json;charset=UTF-8")
                .content(json))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

}

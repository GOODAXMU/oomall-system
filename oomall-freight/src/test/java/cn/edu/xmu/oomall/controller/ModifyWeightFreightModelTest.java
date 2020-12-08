package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.FreightModelPutRequest;
import cn.edu.xmu.oomall.vo.WeightFreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.WeightItemRequest;
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
public class ModifyWeightFreightModelTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void modifyFreightModel() throws Exception{
        WeightItemRequest weightItemRequest = new WeightItemRequest();
        weightItemRequest.setAbovePrice(100L);
        weightItemRequest.setFiftyPrice(50L);
        weightItemRequest.setFirstWeight(1);
        weightItemRequest.setFirstWeightPrice(1L);
        weightItemRequest.setHundredPrice(100L);
        weightItemRequest.setRegionId(1L);
        weightItemRequest.setTenPrice(10L);
        weightItemRequest.setTrihunPrice(300L);
        String json = JSON.toJSONString(weightItemRequest);
        String responseString = this.mvc.perform(put("/shops/1/weightItems/2")
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
}

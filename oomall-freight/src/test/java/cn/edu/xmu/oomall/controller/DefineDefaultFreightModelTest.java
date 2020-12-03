package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhibin lan
 * @date 2020-11-24
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class DefineDefaultFreightModelTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 测试定义模板功能
     * 操作资源不存在
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel() throws Exception {
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/200/default"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    /**
     * 测试定义模板功能
     * 成功
     * @throws Exception
     */
    @Test
    public void defineDefaultFreightModel1() throws Exception {
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/9/default"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"code\":0,\"message\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }
}

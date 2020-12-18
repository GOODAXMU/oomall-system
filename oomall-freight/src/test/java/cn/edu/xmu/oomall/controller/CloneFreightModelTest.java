package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author zhibin lan
 * @date 2020-11-28
 */
@SpringBootTest(classes = OomallOrderFreightApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class CloneFreightModelTest {
    @Autowired
    private MockMvc mvc;

    /**
     * 测试克隆模板功能
     * 资源不存在
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/200/clone").header("authorization", token))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    /**
     * 测试克隆模板功能
     * 成功
     *
     * @throws Exception
     */
    @Test
    public void cloneFreightModel1() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";
        String responseString = this.mvc.perform(post("/shops/1/freightmodels/9/clone").header("authorization", token))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        System.out.println(responseString);

        int startIndex = responseString.indexOf("id");
        int endIndex = responseString.indexOf("name");
        String id = responseString.substring(startIndex + 4, endIndex - 2);
        System.out.println("id :" + id.toString());
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    public void cloneFreightModel2() throws Exception {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjA1MTk1NDQ5MjdLIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjoxLCJleHAiOjM3NTQ2NTI5MzYsInVzZXJJZCI6MSwiaWF0IjoxNjA3MTY5Mjg5fQ.jJUTyU6Y53XRasLDqHFcT5VDQZm8qRx06MepkRGI9H0";
        String responseString = this.mvc.perform(post("/shops/2/freightmodels/9/clone").header("authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = "{\"errno\":505}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }


}

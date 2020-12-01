package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderFreightApplication;
import cn.edu.xmu.oomall.vo.FreightCalculateRequest;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class CalculateFreightTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void calculateFreight() throws Exception {
		List<FreightCalculateRequest> freightCalculateRequestList = new ArrayList<FreightCalculateRequest>();
		FreightCalculateRequest freightCalculateRequest = new FreightCalculateRequest();
		freightCalculateRequest.setCount(1);
		freightCalculateRequest.setSkuId(Long.valueOf(1));
		freightCalculateRequestList.add(freightCalculateRequest);
		String json = JSON.toJSONString(freightCalculateRequestList);
		String responseString = this.mvc.perform(post("/region/200/price")
				.contentType("application/json;charset=UTF-8")
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn()
				.getResponse()
				.getContentAsString();
		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":11}";
		JSONAssert.assertEquals(expectedResponse, responseString, true);
	}
}

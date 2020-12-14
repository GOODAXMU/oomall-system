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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author xincong yao
 * @date 2020-12-14
 */
@SpringBootTest(classes = OomallOrderApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
public class CustomerOrderControllerGetStatusTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAllOrdersStatesTest() throws Exception {
		String response = mvc.perform(get("/orders/states"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"code\":1,\"name\":\"待付款\"},{\"code\":2,\"name\":\"待收货\"},{\"code\":3,\"name\":\"已完成\"},{\"code\":4,\"name\":\"已取消\"},{\"code\":11,\"name\":\"新订单\"},{\"code\":12,\"name\":\"待支付尾款\"},{\"code\":21,\"name\":\"付款完成\"},{\"code\":22,\"name\":\"待成团\"},{\"code\":23,\"name\":\"未成团\"},{\"code\":24,\"name\":\"已发货\"}]}";

		Assert.assertEquals(expectedResponse, response);
	}
}

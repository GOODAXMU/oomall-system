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
public class CustomerOrderControllerGetAllOrderTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAllOrdersTest0() throws Exception {
		String response = mvc.perform(get("/orders")
				.queryParam("orderSn", "917de2ea-e092-46b2-a3d1-1478de7a93b8")
				.queryParam("state", "6")
				.queryParam("beginTime", "2020-12-12T11:06:15")
				.queryParam("endTime", "2020-12-13T11:06:15")
				.queryParam("page", "1")
				.queryParam("pageSize", "10")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":10,\"total\":1,\"pages\":1,\"list\":[{\"id\":100027,\"customerId\":112,\"shopId\":345,\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":1607771175,\"originPrice\":0,\"discountPrice\":0,\"freightPrice\":2}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getAllOrdersTest1() throws Exception {
		String response = mvc.perform(get("/orders")
				.queryParam("state", "6")
				.queryParam("beginTime", "2019-12-12T11:06:15")
				.queryParam("endTime", "2021-12-13T11:06:15")
				.queryParam("page", "4")
				.queryParam("pageSize", "3")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":4,\"pageSize\":3,\"total\":30,\"pages\":10,\"list\":[{\"id\":100009,\"customerId\":112,\"shopId\":null,\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":1607719611,\"originPrice\":0,\"discountPrice\":0,\"freightPrice\":2},{\"id\":100010,\"customerId\":112,\"shopId\":null,\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":1607719631,\"originPrice\":0,\"discountPrice\":0,\"freightPrice\":2},{\"id\":100011,\"customerId\":112,\"shopId\":null,\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":1607719691,\"originPrice\":0,\"discountPrice\":0,\"freightPrice\":2}]}}";

		Assert.assertEquals(expectedResponse, response);
	}
}

package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.OomallOrderApplication;
import cn.edu.xmu.oomall.vo.CommonResponse;
import cn.edu.xmu.oomall.vo.OrderPostRequest;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OomallOrderApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerOrderControllerTest1 {

	@Autowired
	private MockMvc mvc;

	@Test
	public void createNormalOrderTest() throws Exception {
		OrderPostRequest request = new OrderPostRequest();
		request.setAddress("address1");
		request.setConsignee("tom");
		request.setMessage("as soon as possible");
		request.setMobile("13344444444");
		request.setRegionId(66L);
		List<OrderPostRequest.OrderItem> ois = new ArrayList<>();
		OrderPostRequest.OrderItem oi1 = new OrderPostRequest.OrderItem();
		oi1.setSkuId(280L);
		oi1.setQuantity(2);
		OrderPostRequest.OrderItem oi2 = new OrderPostRequest.OrderItem();
		oi2.setSkuId(281L);
		oi2.setQuantity(3);
		ois.add(oi1);
		ois.add(oi2);
		request.setOrderItems(ois);

		String r1 = mvc.perform(post("/orders")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content(JSON.toJSONString(request))
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		CommonResponse response1 = JSON.parseObject(r1, CommonResponse.class);

		String data1 = (String) response1.getData();
		String expectedData1 = "2f761644-2819-482e-9ad7-ab4fd336da9c";
		Assert.assertEquals(expectedData1.length(), data1.length());

		Thread.sleep(5000);

		String r2 = mvc.perform(
				get("/orders")
						.queryParam("orderSn", data1)
						.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedData2 = "";
		Assert.assertEquals(expectedData2, r2);
	}
}

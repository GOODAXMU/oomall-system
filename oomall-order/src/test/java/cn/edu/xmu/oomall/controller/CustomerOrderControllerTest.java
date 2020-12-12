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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OomallOrderApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerOrderControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void createNormalOrderTest() throws Exception {
		String response = mvc.perform(post("/orders")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":112,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":6642,\"discountPrice\":12,\"freightPrice\":null,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":123,\"discount\":3,\"couponActivityId\":542342,\"beShareId\":111},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":123,\"discount\":3,\"couponActivityId\":542343,\"beShareId\":111},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":123,\"discount\":3,\"couponActivityId\":542344,\"beShareId\":111},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":123,\"discount\":3,\"couponActivityId\":542345,\"beShareId\":111}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createGrouponOrderTest() throws Exception {
		String response = mvc.perform(post("/orders")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123, \"grouponId\": 12333}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":112,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":1,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":12333,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":666,\"discount\":null,\"couponActivityId\":542342,\"beShareId\":null},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":null,\"discount\":null,\"couponActivityId\":542343,\"beShareId\":null},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":null,\"discount\":null,\"couponActivityId\":542344,\"beShareId\":null},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":null,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createPresaleOrderTest() throws Exception {
		String response = mvc.perform(post("/orders")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123, \"presaleId\": 12333}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":112,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":2,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":666,\"discount\":null,\"couponActivityId\":542342,\"beShareId\":null},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":null,\"discount\":null,\"couponActivityId\":542343,\"beShareId\":null},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":null,\"discount\":null,\"couponActivityId\":542344,\"beShareId\":null},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":null,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createSeckillOrderTest() throws Exception {

		String response = mvc.perform(post("/orders")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 1, \"quantity\": 1566, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":112,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":1,\"orderId\":null,\"name\":null,\"quantity\":1566,\"price\":888,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getAllOrdersStatesTest() throws Exception {
		String response = mvc.perform(get("/orders/states"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":[{\"code\":0,\"name\":\"已取消\"},{\"code\":1,\"name\":\"定金待支付\"},{\"code\":2,\"name\":\"待支付\"},{\"code\":3,\"name\":\"待参团\"},{\"code\":4,\"name\":\"已支付定金\"},{\"code\":5,\"name\":\"待支付尾款\"},{\"code\":6,\"name\":\"创建订单\"},{\"code\":7,\"name\":\"预售中止\"},{\"code\":8,\"name\":\"已参团\"},{\"code\":9,\"name\":\"未达到团购门槛\"},{\"code\":10,\"name\":\"已成团\"},{\"code\":11,\"name\":\"已支付\"},{\"code\":12,\"name\":\"已支付尾款\"},{\"code\":13,\"name\":\"已退款\"},{\"code\":14,\"name\":\"订单中止\"},{\"code\":15,\"name\":\"售后单待发货\"},{\"code\":16,\"name\":\"发货中\"},{\"code\":17,\"name\":\"到货\"},{\"code\":18,\"name\":\"已签收\"}]}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getAllOrdersTest() throws Exception {
		String response = mvc.perform(get("/orders")
				.queryParam("orderSn", "917de2ea-e092-46b2-a3d1-1478de7a93b8")
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"page\":1,\"pageSize\":20,\"total\":1,\"pages\":0,\"list\":[]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getOrderDetailsTest() throws Exception {
		String response = mvc.perform(get("/orders/{id}", 1L).header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":1,\"customer\":{\"id\":112,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":345,\"name\":\"super shop\",\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"},\"pid\":null,\"orderType\":null,\"state\":0,\"subState\":null,\"gmtCreate\":1606843990,\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null,\"message\":null,\"regionId\":100,\"address\":\"xiamen\",\"mobile\":\"1334567890\",\"consignee\":\"consigneeeee\",\"couponId\":null,\"grouponId\":null,\"orderItems\":[{\"skuId\":185,\"orderId\":1,\"name\":null,\"quantity\":1,\"price\":4475,\"discount\":null,\"couponActivityId\":null,\"beShareId\":null}]}}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void updateSelfOrderTest() throws Exception {
		String response = mvc.perform(put("/orders/{id}", 1L)
				.header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"consignee\": \"consigneeeee\",\"regionId\": 100,\"address\": \"xiamen\",\"mobile\": \"1334567890\"}").contentType("application/json;charset=UTF-8"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteSelfOrder() throws Exception {
		String response = mvc.perform(delete("/orders/{id}", 2L).header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void confirmOrderTest() throws Exception {
		String response = mvc.perform(put("/orders/{id}/confirm", 1L).header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":800,\"errmsg\":\"订单状态禁止\"}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void transferOrderTest() throws Exception {
		String response = mvc.perform(post("/orders/{id}/groupon-normal", 1L).header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":800,\"errmsg\":\"订单状态禁止\"}";

		Assert.assertEquals(expectedResponse, response);
	}

}

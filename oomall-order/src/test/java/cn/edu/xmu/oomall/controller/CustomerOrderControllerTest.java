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
				.header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":null,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":6642,\"discountPrice\":12,\"freightPrice\":null,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":123,\"discount\":3,\"couponActivityId\":542342,\"beShareId\":111},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":123,\"discount\":3,\"couponActivityId\":542343,\"beShareId\":111},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":123,\"discount\":3,\"couponActivityId\":542344,\"beShareId\":111},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":123,\"discount\":3,\"couponActivityId\":542345,\"beShareId\":111}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createGrouponOrderTest() throws Exception {
		String response = mvc.perform(post("/orders")
				.header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123, \"grouponId\": 12333}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":null,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":1,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":12333,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":666,\"discount\":null,\"couponActivityId\":542342,\"beShareId\":null},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":null,\"discount\":null,\"couponActivityId\":542343,\"beShareId\":null},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":null,\"discount\":null,\"couponActivityId\":542344,\"beShareId\":null},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":null,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createPresaleOrderTest() throws Exception {
		String response = mvc.perform(post("/orders")
				.header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 123213, \"quantity\": 12, \"couponActId\": 542342}, {\"skuId\": 123214, \"quantity\": 13, \"couponActId\": 542343}, {\"skuId\": 123215, \"quantity\": 14, \"couponActId\": 542344}, {\"skuId\": 123216, \"quantity\": 15, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123, \"presaleId\": 12333}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":null,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":2,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":123213,\"orderId\":null,\"name\":null,\"quantity\":12,\"price\":666,\"discount\":null,\"couponActivityId\":542342,\"beShareId\":null},{\"skuId\":123214,\"orderId\":null,\"name\":null,\"quantity\":13,\"price\":null,\"discount\":null,\"couponActivityId\":542343,\"beShareId\":null},{\"skuId\":123215,\"orderId\":null,\"name\":null,\"quantity\":14,\"price\":null,\"discount\":null,\"couponActivityId\":542344,\"beShareId\":null},{\"skuId\":123216,\"orderId\":null,\"name\":null,\"quantity\":15,\"price\":null,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void createSeckillOrderTest() throws Exception {

		String response = mvc.perform(post("/orders")
				.header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"orderItems\": [{\"skuId\": 1, \"quantity\": 1566, \"couponActId\": 542345}], \"consignee\": \"consigneeee\", \"regionId\": 100222, \"address\": \"addresssss\", \"mobile\": \"13333333\", \"message\": \"messageeeee\", \"couponId\": 123123}")
				.contentType("application/json"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":null,\"customer\":{\"id\":null,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":null,\"gmtCreate\":null,\"gmtModified\":null},\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":null,\"originPrice\":0,\"discountPrice\":null,\"freightPrice\":2,\"message\":\"messageeeee\",\"regionId\":100222,\"address\":\"addresssss\",\"mobile\":\"13333333\",\"consignee\":\"consigneeee\",\"couponId\":123123,\"grouponId\":null,\"orderItems\":[{\"skuId\":1,\"orderId\":null,\"name\":null,\"quantity\":1566,\"price\":888,\"discount\":null,\"couponActivityId\":542345,\"beShareId\":null}]}}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getAllOrdersStatesTest() throws Exception {
		String response = mvc.perform(get("/orders/states"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":[{\"code\":0,\"name\":\"新建态\"},{\"code\":10,\"name\":\"已发货\"},{\"code\":11,\"name\":\"已收货\"},{\"code\":12,\"name\":\"已完成\"},{\"code\":-1,\"name\":\"禁止\"},{\"code\":-2,\"name\":\"已取消\"}]}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getAllOrdersTest() throws Exception {
		String response = mvc.perform(get("/orders").header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"page\":1,\"pageSize\":20,\"total\":37918,\"pages\":1895,\"list\":[{\"id\":30,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":31,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":32,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":33,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":34,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":35,\"customerId\":4,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":36,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":37,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":38,\"customerId\":4,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":39,\"customerId\":3,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":41,\"customerId\":1,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":42,\"customerId\":9,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":43,\"customerId\":12,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":44,\"customerId\":9,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":45,\"customerId\":4,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":46,\"customerId\":2,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":47,\"customerId\":10,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":48,\"customerId\":3,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":49,\"customerId\":2,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null},{\"id\":50,\"customerId\":10,\"shopId\":null,\"pid\":null,\"orderType\":null,\"state\":6,\"subState\":null,\"gmtCreate\":1606243220,\"originalPrice\":null,\"discountPrice\":null,\"freightPrice\":null}]}}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void getOrderDetailsTest() throws Exception {
		String response = mvc.perform(get("/orders/{id}", 1L).header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		System.out.println(response);

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":{\"id\":1,\"customer\":{\"id\":1,\"userName\":\"abee\",\"realName\":\"wilson\"},\"shop\":{\"id\":null,\"name\":\"super shop\",\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"},\"pid\":null,\"orderType\":0,\"state\":-2,\"subState\":null,\"gmtCreate\":1606243220,\"originPrice\":null,\"discountPrice\":null,\"freightPrice\":null,\"message\":null,\"regionId\":100,\"address\":\"xiamen\",\"mobile\":\"1334567890\",\"consignee\":\"consigneeeee\",\"couponId\":null,\"grouponId\":null,\"orderItems\":[{\"skuId\":185,\"orderId\":1,\"name\":null,\"quantity\":1,\"price\":4475,\"discount\":null,\"couponActivityId\":null,\"beShareId\":null}]}}";
		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void updateSelfOrderTest() throws Exception {
		String response = mvc.perform(put("/orders/{id}", 1L)
				.header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM")
				.content("{\"consignee\": \"consigneeeee\",\"regionId\": 100,\"address\": \"xiamen\",\"mobile\": \"1334567890\"}").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":504,\"message\":\"操作的资源id不存在\",\"data\":null}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void deleteSelfOrder() throws Exception {
		String response = mvc.perform(delete("/orders/{id}", 1L).header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":0,\"message\":\"成功\",\"data\":null}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void confirmOrderTest() throws Exception {
		String response = mvc.perform(put("/orders/{id}/confirm", 1L).header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":800,\"message\":\"订单状态禁止\",\"data\":null}";

		Assert.assertEquals(expectedResponse, response);
	}

	@Test
	public void transferOrderTest() throws Exception {
		String response = mvc.perform(post("/orders/{id}/groupon-normal", 1L).header("authentication", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isCreated())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"code\":800,\"message\":\"订单状态禁止\",\"data\":null}";

		Assert.assertEquals(expectedResponse, response);
	}

}

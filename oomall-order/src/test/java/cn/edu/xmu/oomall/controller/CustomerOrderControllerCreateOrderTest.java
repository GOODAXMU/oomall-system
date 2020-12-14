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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author xincong yao
 * @date 2020-12-14
 */
@Transactional
@SpringBootTest(classes = OomallOrderApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerOrderControllerCreateOrderTest {

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

}

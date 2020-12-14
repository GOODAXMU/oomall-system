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
public class CustomerOrderControllerGetOrderDetailTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getOrderDetailsTest0() throws Exception {
		String response = mvc.perform(get("/orders/{id}", 100028L).header("authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0aGlzIGlzIGEgdG9rZW4iLCJhdWQiOiJNSU5JQVBQIiwidG9rZW5JZCI6IjIwMjAxMjAzMDkxMTA1ODhVIiwiaXNzIjoiT09BRCIsImRlcGFydElkIjowLCJleHAiOjM3NTQ0NDE1MTIsInVzZXJJZCI6MTEyLCJpYXQiOjE2MDY5NTc4NjV9.FWk_Gc8yEVrah74GyBQRB3gTnw1nz_riMuAvrujF1uM"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();

		String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"id\":100028,\"customer\":{\"id\":112,\"userName\":\"abee\",\"name\":\"wilson\"},\"shop\":{\"id\":345,\"name\":\"super shop\",\"state\":null,\"gmtCreate\":\"2020-11-27T07:59:58.623756500\",\"gmtModified\":\"2020-11-27T07:59:58.623756500\"},\"pid\":null,\"orderType\":0,\"state\":6,\"subState\":null,\"gmtCreate\":1607800954,\"originPrice\":60,\"discountPrice\":6,\"freightPrice\":2,\"message\":\"as soon as possible\",\"regionId\":66,\"address\":\"address1\",\"mobile\":\"13344444444\",\"consignee\":\"tom\",\"couponId\":null,\"grouponId\":null,\"orderItems\":[{\"skuId\":333,\"orderId\":100028,\"name\":\"Asku\",\"quantity\":2,\"price\":12,\"discount\":3,\"couponActId\":null,\"beSharedId\":null},{\"skuId\":335,\"orderId\":100028,\"name\":\"Asku\",\"quantity\":3,\"price\":12,\"discount\":3,\"couponActId\":null,\"beSharedId\":null}]}}";

		Assert.assertEquals(expectedResponse, response);
	}
}

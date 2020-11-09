package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 */
@ApiModel(description = "获取订单详细内容的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailGetResponse {

	private class Customer {
		private Long id;
		private String userName;
		private String realName;
	}

	private class Shop {
		private Long id;
		private String name;
		private String gmtCreateTime;
		private String gmtModiTime;
	}
	private class OrderItem {
		private Long skuId;

		private Long orderId;

		private String name;

		private Long quantity;

		private Long price;

		private Long discount;

		private Long couponId;

		private Long couponActivityId;

		private Long beSharedId;
	}

	private Long id;
	private Customer customer;
	private Shop shop;
	private Long pid;
	private Integer orderType;
	private Integer state;
	private Integer subState;
	private Long gmtCreateTime;
	private Long originPrice;
	private Long discountPrice;
	private Long freightPrice;
	private String message;
	private String consignee;
	private Long couponId;
	private Long grouponId;
	private List<OrderItem> orderItems;

}

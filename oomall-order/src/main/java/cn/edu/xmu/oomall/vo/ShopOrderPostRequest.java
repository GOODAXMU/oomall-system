package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 */
@ApiModel(description = "指定新订单的资料")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderPostRequest {

	private class OrderItem{
		@NotNull
		@Min(value = 0)
		private Long skuId;
		@NotNull
		@Min(value = 1)
		private Long quantity;
	}

	@Valid
	private List<OrderItem> orderItems;

	@NotNull
	private String consignee;

	@NotNull
	@Min(value = 0)
	private Long region_id;

	@NotNull
	private String address;

	@NotNull
	@Min(value = 1)
	private Long mobile;

	@NotNull
	private String message;
}

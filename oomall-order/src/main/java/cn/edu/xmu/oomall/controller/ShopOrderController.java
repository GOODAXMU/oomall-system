package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 */
@Api(value = "供店家访问的订单api")
@Validated
@RestController
@RequestMapping("/shops")
public class ShopOrderController {

	@ApiOperation(value = "店家查询商户所有订单 (概要)。")
	@GetMapping(value = "/{shopId}/orders", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public List<OrderSummaryGetResponse> searchShopOrders(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@Min(value = 0) Long customerId,
			String orderSn,
			@Min(value = 1) Integer page,
			@Min(value = 1)	@Max(value = 50) Integer pageSize) {
		return null;
	}

	@ApiOperation(value = "管理员建立售后订单。")
	@PostMapping(value = "/{shopId}/orders", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderDetailGetResponse createShopOrder(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@Valid @RequestBody ShopOrderPostRequest orderInfo) {
		return null;
	}

	@ApiOperation(value = "店家修改订单 (留言)。")
	@PutMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public Object addShopOrderMessage(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@Valid @RequestBody ShopOrderMessageAddRequest orderInfo) {
		return null;
	}

	@ApiOperation(value = "店家查询店内订单完整信息（普通，团购，预售）")
	@GetMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	//API标准 v1.0.6 返回类型有误
	public OrderDetailGetResponse/*OrderSummaryGetResponse*/ getShopOrderDetail(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}

	@ApiOperation(value = "管理员取消本店铺订单。")
	@DeleteMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public Object cancelShopOrder(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}


	@ApiOperation(value = "店家对订单标记发货。")
	@PutMapping(value = "/{shopId}/orders/{id}/deliver", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Object markShopOrderDeliver(
			@NotNull @Min(value = 0) @PathVariable Long shopId,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@Valid @RequestBody ShopOrderDeliverPutRequest body) {
		return null;
	}

}

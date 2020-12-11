package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.service.ShopPaymentService;
import cn.edu.xmu.oomall.vo.PaymentResponse;
import cn.edu.xmu.oomall.vo.RefundPostRequest;
import cn.edu.xmu.oomall.vo.RefundResponse;
import cn.edu.xmu.oomall.vo.Reply;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-9
 * @author Wang Zhizhou
 * modified 2020/12/11
 */
@Api(value = "供卖家及管理员访问的支付api")
@Validated
@RestController
@RequestMapping
public class ShopPaymentController {

	@Autowired
	private ShopPaymentService shopPaymentService;

	@ApiOperation(value = "管理员查询订单的支付信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/shops/{shopId}/orders/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<List<PaymentResponse>> getOrderPayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		Reply<List<Payment>> r = shopPaymentService.getPaymentsByOrderId(id, shopId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "管理员查询售后单的支付信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/shops/{shopId}/aftersales/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<List<PaymentResponse>> getAfterSalePayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		Reply<List<Payment>> r = shopPaymentService.getPaymentsByAfterSaleId(id, shopId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "管理员创建退款单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "支付单id", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "RefundPostRequest", name = "body", value = "退款金额", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "/shops/{shopId}/payments/{id}/refund", produces = "application/json;charset=UTF-8")
	public Reply<RefundResponse> createRefund(
			@Valid @RequestBody RefundPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		Refund refund = new Refund(id, request);
		Reply<Refund> r = shopPaymentService.createRefund(refund, shopId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(refund.createVo());
	}


	@ApiOperation(value = "管理员查询订单的退款信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/shops/{shopId}/orders/{id}/refund", produces = "application/json;charset=UTF-8")
	public Reply<List<RefundResponse>> getOrderRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		Reply<List<Refund>> r = shopPaymentService.getRefundsByOrderId(id, shopId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<RefundResponse> paymentResponses = new ArrayList<>();
		for (Refund payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "管理员查询售后单的退款信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/shops/{shopId}/aftersales/{id}/refund", produces = "application/json;charset=UTF-8")
	public Reply<List<RefundResponse>> getAfterSaleRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		Reply<List<Refund>> r = shopPaymentService.getRefundsByAfterSaleId(id, shopId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<RefundResponse> paymentResponses = new ArrayList<>();
		for (Refund payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}

}

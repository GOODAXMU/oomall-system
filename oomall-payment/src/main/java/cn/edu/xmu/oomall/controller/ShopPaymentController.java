package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.service.PaymentService;
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
 * modified 2020/11/29
 */
@Api(value = "供卖家及管理员访问的支付api")
@Validated
@RestController
@RequestMapping
public class ShopPaymentController {

	@Autowired
	private PaymentService paymentService;

	@ApiOperation(value = "管理员查询订单的支付信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/shops/{shopId}/orders/{id}/payments")
	public Reply<List<PaymentResponse>> getPayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		// todo 检查 shopId 是否正确

		List<Payment> payments = paymentService.getPayments(id).getData();
		if (null == payments) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : payments) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "管理员查询售后单的支付信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/shops/{shopId}/aftersales/{id}/payments")
	public Reply<List<PaymentResponse>> getAftersalePayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		// todo 检查 shopId 是否正确

		List<Payment> payments = paymentService.getAftersalePayments(id).getData();
		if (null == payments) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : payments) {
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
	@PostMapping("/shops/{shopId}/payments/{id}/refund")
	public Reply<RefundResponse> createRefund(
			@Valid @RequestBody RefundPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable String shopId) {

		Reply<List<Long>> rIds = paymentService.getOrderIdByPaymentId(id);
		if (!rIds.isOk() || null == rIds.getData()) {
			return new Reply<>(rIds.getResponseStatus());
		}

		Long oid = rIds.getData().get(0);
		Refund.Type type = Refund.Type.NORMAL;

		if (null == oid) {
			oid = rIds.getData().get(1);
			type = Refund.Type.AFTERSALE;
		}

		Refund refund = new Refund(id, oid, request, type);
		Reply<Refund> r = paymentService.createRefund(refund);
		refund = r.getData();
		if (null == refund) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(refund.createVo());
	}


	@ApiOperation(value = "管理员查询订单的退款信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/shops/{shopId}/orders/{id}/refund")
	public Reply<List<RefundResponse>> getRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {
		// todo 检查 shopId 是否正确

		List<Refund> refunds = paymentService.getRefunds(id).getData();
		if (null == refunds) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<RefundResponse> paymentResponses = new ArrayList<>();
		for (Refund payment : refunds) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "管理员查询售后单的退款信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "店铺id", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/shops/{shopId}/aftersales/{id}/refund")
	public Reply<List<RefundResponse>> getAftersaleRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable Long shopId) {

		// todo 检查 shopId 是否正确

		List<Refund> refunds = paymentService.getAftersaleRefunds(id).getData();
		if (null == refunds) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<RefundResponse> paymentResponses = new ArrayList<>();
		for (Refund payment : refunds) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


}

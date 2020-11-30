package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.service.PaymentService;
import cn.edu.xmu.oomall.vo.*;
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
@Api(value = "供买家访问的支付api")
@Validated
@RestController
@RequestMapping
public class CustomerPaymentController {

	@Autowired
	private PaymentService paymentService;


	@ApiOperation(value = "获取所有支付状态", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/payments/states")
	public Reply<List<PaymentStatesGetResponse>> getPaymentStates() {
		List<PaymentStatesGetResponse> r = new ArrayList<>();
		for (Payment.State ps : Payment.State.values()) {
			r.add(new PaymentStatesGetResponse(ps.getCode(), ps.getDescription()));
		}

		return new Reply<>(r);
	}


	@ApiOperation(value = "买家为订单创建支付单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "PaymentPostRequest", name = "body", value = "支付信息", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/orders/{id}/payments")
	public Reply<PaymentResponse> createPayment(
			@Valid @RequestBody PaymentPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id) {

		Payment payment = new Payment(id, request, Payment.Type.NORMAL);

		Reply<Payment> r = paymentService.createPayment(payment);
		payment = r.getData();
		if (null == payment) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(payment.createVo());
	}


	@ApiOperation(value = "买家查看支付信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/orders/{id}/payments")
	public Reply<List<PaymentResponse>> getPayment(
			@NotNull @Min(value = 0) @PathVariable Long id) {
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


	@ApiOperation(value = "查看所有支付渠道", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/payments/patterns")
	public Reply<List<PayPatternResponse>> getAllPattern() {
		List<PayPatternResponse> r = new ArrayList<>();
		for (Payment.Pattern pattern : Payment.Pattern.values()) {
			r.add(new PayPatternResponse(pattern.getPatternName(), pattern.getPatternName()));
		}

		return new Reply<>(r);
	}


	@ApiOperation(value = "买家为售后单创建支付单")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后单id", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "PaymentPostRequest", name = "body", value = "支付信息", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/aftersales/{id}/payments")
	public Reply<PaymentResponse> createAftersalePayment(
			@Valid @RequestBody PaymentPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id) {

		Payment payment = new Payment(id, request, Payment.Type.AFTERSALE);

		Reply<Payment> r = paymentService.createPayment(payment);
		payment = r.getData();
		if (null == payment) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(payment.createVo());
	}


	@ApiOperation(value = "买家查看售后支付信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aftersales/{id}/payments")
	public Reply<List<PaymentResponse>> getAftersalePayment(
			@NotNull @Min(value = 0) @PathVariable Long id) {
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


	@ApiOperation(value = "买家查看返款信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/orders/{id}/refunds")
	public Reply<List<RefundResponse>> getRefund(
			@NotNull @Min(value = 0) @PathVariable Long id) {
		List<Refund> refunds = paymentService.getRefunds(id).getData();
		if (null == refunds) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<RefundResponse> refundResponses = new ArrayList<>();
		for (Refund refund : refunds) {
			refundResponses.add(refund.createVo());
		}

		return new Reply<>(refundResponses);
	}


	@ApiOperation(value = "买家查看售后返款信息", produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aftersales/{id}/refunds")
	public Reply<List<RefundResponse>> getAftersaleRefund(
			@NotNull @Min(value = 0) @PathVariable Long id) {
		List<Refund> refunds = paymentService.getAftersaleRefunds(id).getData();
		if (null == refunds) {
			return new Reply<>(cn.edu.xmu.oomall.constant.ResponseStatus.RESOURCE_ID_NOT_EXIST);
		}

		List<RefundResponse> refundResponses = new ArrayList<>();
		for (Refund refund : refunds) {
			refundResponses.add(refund.createVo());
		}

		return new Reply<>(refundResponses);
	}


}

package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.annotation.Audit;
import cn.edu.xmu.oomall.annotation.LoginUser;
import cn.edu.xmu.oomall.bo.Payment;
import cn.edu.xmu.oomall.bo.Refund;
import cn.edu.xmu.oomall.service.CustomerPaymentService;
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
 * modified 2020/12/20
 */
@Api(value = "供买家访问的支付api")
@Validated
@RestController
@RequestMapping
public class CustomerPaymentController {

	@Autowired
	private CustomerPaymentService customerPaymentService;


	@ApiOperation(value = "获取所有支付状态", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/payments/states", produces = "application/json;charset=UTF-8")
	public Reply<List<PaymentStatesGetResponse>> getPaymentStates() {
		List<PaymentStatesGetResponse> r = new ArrayList<>();
		for (Payment.State ps : Payment.State.values()) {
			r.add(new PaymentStatesGetResponse(ps.getCode(), ps.getDescription()));
		}

		return new Reply<>(r);
	}


	@ApiOperation(value = "查看所有支付渠道", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/payments/patterns", produces = "application/json;charset=UTF-8")
	public Reply<List<PayPatternResponse>> getAllPattern() {
		List<PayPatternResponse> r = new ArrayList<>();
		for (Payment.Pattern pattern : Payment.Pattern.values()) {
			r.add(new PayPatternResponse(String.format("%03d", pattern.getPatternId()), pattern.getPatternName()));
		}

		return new Reply<>(r);
	}


	@Audit
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
	@PostMapping(value = "/orders/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<PaymentResponse> createPayment(
			@Valid @RequestBody PaymentPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Payment payment = new Payment(id, null, request);

		Reply<Payment> r = customerPaymentService.createOrderPayment(payment, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(r.getData().createVo());
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
	@PostMapping(value = "/aftersales/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<PaymentResponse> createAfterSalePayment(
			@Valid @RequestBody PaymentPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Payment payment = new Payment(null, id, request);

		Reply<Payment> r = customerPaymentService.createAfterSalePayment(payment, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		return new Reply<>(r.getData().createVo());
	}


	@Audit
	@ApiOperation(value = "买家查看支付信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/orders/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<List<PaymentResponse>> getOrderPayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Reply<List<Payment>> r = customerPaymentService.getPaymentsByOrderId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "买家查看售后支付信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/aftersales/{id}/payments", produces = "application/json;charset=UTF-8")
	public Reply<List<PaymentResponse>> getAfterSalePayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Reply<List<Payment>> r = customerPaymentService.getPaymentsByAfterSaleId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<PaymentResponse> paymentResponses = new ArrayList<>();
		for (Payment payment : r.getData()) {
			paymentResponses.add(payment.createVo());
		}

		return new Reply<>(paymentResponses);
	}


	@ApiOperation(value = "买家查看返款信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/orders/{id}/refunds", produces = "application/json;charset=UTF-8")
	public Reply<List<RefundResponse>> getOrderRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Reply<List<Refund>>	r = customerPaymentService.getRefundsByOrderId(id, customerId);
		if (r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<RefundResponse> refundResponses = new ArrayList<>();
		for (Refund refund : r.getData()) {
			refundResponses.add(refund.createVo());
		}

		return new Reply<>(refundResponses);
	}


	@ApiOperation(value = "买家查看售后返款信息", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "售后id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功")
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/aftersales/{id}/refunds", produces = "application/json;charset=UTF-8")
	public Reply<List<RefundResponse>> getAfterSaleRefund(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@LoginUser Long customerId) {

		Reply<List<Refund>> r = customerPaymentService.getRefundByAfterSaleId(id, customerId);
		if (!r.isOk()) {
			return new Reply<>(r.getResponseStatus());
		}

		List<RefundResponse> refundResponses = new ArrayList<>();
		for (Refund refund : r.getData()) {
			refundResponses.add(refund.createVo());
		}

		return new Reply<>(refundResponses);
	}

}

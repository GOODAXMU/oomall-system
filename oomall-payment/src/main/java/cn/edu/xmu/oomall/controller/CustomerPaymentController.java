package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.vo.PaymentPostRequest;
import cn.edu.xmu.oomall.vo.PaymentResponse;
import cn.edu.xmu.oomall.vo.PaymentStatesGetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@Api(value = "供买家访问的支付api")
@Validated
@RestController
@RequestMapping
public class CustomerPaymentController {

	@ApiOperation(value = "获取所有支付状态")
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/payments/states")
	public List<PaymentStatesGetResponse> getPaymentStates() {
		return null;
	}

	@ApiOperation(value = "买家为订单创建支付单")
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/orders/{id}/payments")
	public PaymentResponse createPayment(
			@Valid @RequestBody PaymentPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}

	@ApiOperation(value = "买家查看支付信息")
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/orders/{id}/payments")
	public List<PaymentResponse> getPayment(
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}
}

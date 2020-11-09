package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.vo.PaymentResponse;
import cn.edu.xmu.oomall.vo.RefundPostRequest;
import cn.edu.xmu.oomall.vo.RefundPostResponse;
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
@Api(value = "供卖家及管理员访问的支付api")
@Validated
@RestController
@RequestMapping
public class ShopPaymentController {

	@ApiOperation(value = "管理员查询订单的支付信息")
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/shops/{shopId}/orders/{id}/payments")
	public List<PaymentResponse> getPayment(
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable String shopId) {
		return null;
	}

	@ApiOperation(value = "管理员创建退款单")
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/shops/{shopId}/payments/{id}/refund")
	public RefundPostResponse createRefund(
			@Valid @RequestBody RefundPostRequest request,
			@NotNull @Min(value = 0) @PathVariable Long id,
			@NotNull @Min(value = 0) @PathVariable String shopId) {
		return null;
	}
}

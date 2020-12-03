package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * @date 2020-11-28
 * 实际上和 PaymentPostRequest 一致
 */
@ApiModel(description = "创建售后支付单方法的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AftersalePaymentPostRequest {

	@NotNull
	@Min(value = 0)
	private Long price;

	@NotBlank
	private String pattern;
}

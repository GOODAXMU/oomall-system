package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@ApiModel(description = "创建支付单方法的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPostRequest {

	@NotNull
	@Min(value = 0)
	private Long price;

	@NotBlank
	private String name;
}

package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@ApiModel(description = "计算商品运费方法的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightCalculateRequest {

	@NotNull
	@Min(value = 0)
	private Long skuId;

	@NotNull
	@Min(value = 1)
	private Integer count;
}

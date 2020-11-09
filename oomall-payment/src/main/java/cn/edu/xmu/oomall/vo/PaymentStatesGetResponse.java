package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@ApiModel(description = "获取所以支付状态方法的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatesGetResponse {

	private Integer code;
	private String name;
}

package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-9
 * modified 2020-12-17
 */
@ApiModel(description = "创建支付单方法的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {

	private Long id;
	private Long paymentId;
	private Long amount;
	private Integer state;
	private String gmtCreate;
	private String gmtModified;
	private Long orderId;
	private Long aftersaleId;
}

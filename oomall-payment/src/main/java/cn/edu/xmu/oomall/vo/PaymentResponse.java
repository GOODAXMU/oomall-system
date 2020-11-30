package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.Payment;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@ApiModel(description = "支付单相关操作的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

	private Long id;
	private Long orderId;
	private Long amount;
	private Long actualAmount;
	private String payTime;
	private String payPattern;
	private String state;
	private String beginTime;
	private String endTime;
	private String gmtCreateTime;
	private String gmtModifiedTime;
}

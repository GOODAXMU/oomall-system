package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@ApiModel(description = "创建支付单方法的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundPostResponse {

	private Long id;
	private Long paymentId;
	private Long amount;
	private Integer state;
	private String gmtCreateTime;
	private String gmtModiTime;
}

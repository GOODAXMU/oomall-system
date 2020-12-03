package cn.edu.xmu.ooamll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AfterSaleDto {

	private Long orderId;
	private String orderSn;
	private Long skuId;
	private String skuName;
	private Long shopId;
}

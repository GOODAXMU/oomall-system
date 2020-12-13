package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-12-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStateDto implements Serializable {

	private Long orderId;
	private Long customerId;
	private Integer from;
	private Integer fromSub;
	private Integer to;
	private Integer toSub;
}

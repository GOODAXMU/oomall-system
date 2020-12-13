package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-12-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOrderDto implements Serializable {

	Long customerId;
	Long shopId;

	Integer quantity;
	Long orderItemId;

	String mobile;
	String consignee;
	Long regionId;
	String address;
}

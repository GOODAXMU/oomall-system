package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto implements Serializable {

	private Long customerId;

	private List<Long> skuIdList;
}

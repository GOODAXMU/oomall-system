package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-11-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest implements Serializable {

	private Long skuId;
	/**
	 * 库存修改量, 正数则增加库存
	 */
	private Integer quantity;
}

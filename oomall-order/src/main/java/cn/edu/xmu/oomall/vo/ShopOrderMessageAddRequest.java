package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 */
@ApiModel(description = "操作字段（状态）")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderMessageAddRequest {
	@NotNull
	private String message;
}

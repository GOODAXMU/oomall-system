package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-8
 */
@ApiModel(description = "计算商品运费方法的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightCalculateResponse {

	private Long freight;
}

package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author xincong yao
 * @date 2020-11-8
 */
@ApiModel(description = "获取运费模板概要方法的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelSummaryGetResponse {

	private Long id;
	private String name;
	private Integer type;
	private Boolean defaultModel;
	private LocalDateTime gmtCreate;
	private LocalDateTime gmtModified;
}

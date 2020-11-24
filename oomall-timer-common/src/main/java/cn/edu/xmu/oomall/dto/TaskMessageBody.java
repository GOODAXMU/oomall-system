package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessageBody implements Serializable {

	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime prepareTime;

	private String serviceClassName;
	private String serviceMethodName;
	private String returnTypeClassName;

	private List<Param> params;

	private String topic;
	private String tag;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Param implements Serializable {
		private Integer sequence;
		private String className;
		private String objectJson;
	}

	public boolean validate() {
		return startTime != null && prepareTime != null
				&& !StringUtils.isEmpty(serviceClassName)
				&& !StringUtils.isEmpty(serviceMethodName)
				&& !StringUtils.isEmpty(returnTypeClassName)
				&& params != null
				&& params.size() > 0
				&& !StringUtils.isEmpty(topic)
				&& !StringUtils.isEmpty(tag);
	}
}

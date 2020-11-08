package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

	private Integer code;
	private String message;
	private T data;

	public CommonResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}

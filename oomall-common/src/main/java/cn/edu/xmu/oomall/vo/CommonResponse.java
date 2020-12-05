package cn.edu.xmu.oomall.vo;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"errno","errmsg","data"})
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

	@JsonGetter(value = "errno")
	public Integer getCode() {
		return code;
	}

	@JsonGetter(value = "errmsg")
	public String getMessage() {
		return message;
	}

	public T getData() {
		return data;
	}

	@JsonSetter(value = "errno")
	public void setCode(Integer code) {
		this.code = code;
	}

	@JsonSetter(value = "errmsg")
	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(T data) {
		this.data = data;
	}
}

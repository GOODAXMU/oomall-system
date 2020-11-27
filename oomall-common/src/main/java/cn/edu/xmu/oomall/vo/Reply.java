package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author xincong yao
 * @date 2020-11-25
 */
@Data
@AllArgsConstructor
public class Reply<T> {

	private HttpStatus httpStatus;
	private ResponseStatus responseStatus;
	private T data;

	public boolean isOk() {
		return ResponseStatus.OK.equals(responseStatus);
	}

	public Reply() {
		this.responseStatus = ResponseStatus.OK;
	}

	public Reply(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Reply(HttpStatus httpStatus, ResponseStatus responseStatus) {
		this.httpStatus = httpStatus;
		this.responseStatus = responseStatus;
	}

	public Reply(T data) {
		this.responseStatus = ResponseStatus.OK;
		this.data = data;
	}
}

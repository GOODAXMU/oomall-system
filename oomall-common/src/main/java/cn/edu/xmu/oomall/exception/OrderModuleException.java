package cn.edu.xmu.oomall.exception;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderModuleException extends Exception {

	private HttpStatus httpStatus;

	private ResponseStatus responseStatus;

	public OrderModuleException(HttpStatus httpStatus, ResponseStatus responseStatus) {
		super(responseStatus.getReasonPhrase());
		this.httpStatus = httpStatus;
		this.responseStatus = responseStatus;
	}

	public OrderModuleException(ResponseStatus responseStatus) {
		super(responseStatus.getReasonPhrase());
		this.responseStatus = responseStatus;
	}
}

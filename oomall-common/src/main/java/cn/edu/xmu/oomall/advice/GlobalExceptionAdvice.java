package cn.edu.xmu.oomall.advice;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.vo.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeParseException;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(value = OrderModuleException.class)
	public CommonResponse<String> handleOrderModuleException(HttpServletRequest request,
															 HttpServletResponse response,
													OrderModuleException e) {
		if (e.getHttpStatus() != null) {
			response.setStatus(e.getHttpStatus().value());
		}
		return new CommonResponse<>(e.getResponseStatus().value(),
				e.getResponseStatus().getReasonPhrase());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public CommonResponse<String> handleMethodArgumentNotValidException(HttpServletRequest request,
																		HttpServletResponse response,
																		MethodArgumentNotValidException e) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());

		StringBuilder msg = new StringBuilder();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			msg.append(error.getDefaultMessage());
			msg.append(";");
		}

		return new CommonResponse<>(ResponseStatus.FIELD_INVALID.value(), msg.toString());
	}
}

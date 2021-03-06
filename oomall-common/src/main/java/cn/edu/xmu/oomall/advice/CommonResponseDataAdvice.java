package cn.edu.xmu.oomall.advice;

import cn.edu.xmu.oomall.annotation.IgnoreResponseAdvice;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.vo.CommonResponse;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

	private static Map<Integer, HttpStatus> STATUS_MAP = new HashMap<>();
	static {
		STATUS_MAP.put(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE.value(), HttpStatus.FORBIDDEN);
		STATUS_MAP.put(ResponseStatus.RESOURCE_ID_NOT_EXIST.value(), HttpStatus.NOT_FOUND);
		STATUS_MAP.put(ResponseStatus.ORDER_FORBID.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.FREIGHT_NAME_EXIST.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.REGION_EXIST.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.REFUND_EXCESS.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.REGION_NOT_REACH.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.OUT_OF_STOCK.value(), HttpStatus.OK);
		STATUS_MAP.put(ResponseStatus.DEFAULT_MODEL_EXISTED.value(), HttpStatus.OK);
	}

	/**
	 * 以下url请求的响应对象无需拦截包装
	 */
	private static Set<String> IGNORE_URL_SET = new HashSet<>();
	static {
		IGNORE_URL_SET.add("/swagger-resources/configuration/ui");
		IGNORE_URL_SET.add("/swagger-resources/configuration/security");
		IGNORE_URL_SET.add("/swagger-resources");
		IGNORE_URL_SET.add("/v2/api-docs");
	}



	@Override
	public boolean supports(MethodParameter methodParameter,
							Class<? extends HttpMessageConverter<?>> aClass) {
		if (methodParameter.getDeclaringClass().isAnnotationPresent(
				IgnoreResponseAdvice.class
		)) {
			return false;
		}

		if (methodParameter.getMethod().isAnnotationPresent(
				IgnoreResponseAdvice.class
		)) {
			return false;
		}

		return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
								  MediaType mediaType,
								  Class<? extends HttpMessageConverter<?>> aClass,
								  ServerHttpRequest serverHttpRequest,
								  ServerHttpResponse serverHttpResponse) {
		if (IGNORE_URL_SET.contains(serverHttpRequest.getURI().getPath())) {
			return o;
		}

		CommonResponse<Object> response =
				new CommonResponse<>(ResponseStatus.OK.value(),
						ResponseStatus.OK.getReasonPhrase());

		if (o == null) {
			return response;
		} else if (o instanceof CommonResponse) {
			return o;
		} else if (o instanceof Reply) {
			Reply r = (Reply) o;
			response.setData(r.getData());
			response.setCode(r.getResponseStatus().value());
			response.setMessage(r.getResponseStatus().getReasonPhrase());

			HttpStatus httpStatus = STATUS_MAP.get(r.getResponseStatus().value());
			if (httpStatus != null) {
				serverHttpResponse.setStatusCode(httpStatus);
			}

			if (r.getHttpStatus() != null) {
				serverHttpResponse.setStatusCode(r.getHttpStatus());
			}
			return response;
		} else {
			response.setData(o);
			return response;
		}
	}
}

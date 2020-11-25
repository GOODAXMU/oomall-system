package cn.edu.xmu.oomall.advice;

import cn.edu.xmu.oomall.annotation.IgnoreResponseAdvice;
import cn.edu.xmu.oomall.constant.OrderModuleStatus;
import cn.edu.xmu.oomall.vo.CommonResponse;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

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
				new CommonResponse<>(OrderModuleStatus.OK.value(),
						OrderModuleStatus.OK.getReasonPhrase());

		if (o == null) {
			return response;
		} else if (o instanceof CommonResponse) {
			return o;
		} else if (o instanceof Reply) {
			Reply r = (Reply) o;
			response.setData(r.getData());
			response.setCode(r.getOrderModuleStatus().value());
			response.setMessage(r.getOrderModuleStatus().getReasonPhrase());
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

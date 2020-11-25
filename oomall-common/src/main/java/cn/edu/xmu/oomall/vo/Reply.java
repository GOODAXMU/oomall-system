package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.constant.OrderModuleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author xincong yao
 * @date 2020-11-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply<T> {

	private HttpStatus httpStatus;
	private OrderModuleStatus orderModuleStatus;
	private T data;

	public Reply(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Reply(OrderModuleStatus orderModuleStatus) {
		this.orderModuleStatus = orderModuleStatus;
	}

	public Reply(T data) {
		this.data = data;
	}
}

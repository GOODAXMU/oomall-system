package cn.edu.xmu.oomall.exception;

import cn.edu.xmu.oomall.constant.OrderModuleStatus;
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

	private OrderModuleStatus orderModuleStatus;

	public OrderModuleException(HttpStatus httpStatus, OrderModuleStatus orderModuleStatus) {
		super(orderModuleStatus.getReasonPhrase());
		this.httpStatus = httpStatus;
		this.orderModuleStatus = orderModuleStatus;
	}

	public OrderModuleException(OrderModuleStatus orderModuleStatus) {
		super(orderModuleStatus.getReasonPhrase());
		this.orderModuleStatus = orderModuleStatus;
	}
}

package cn.edu.xmu.oomall.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutableTask {

	private Long id;

	private Object service;

	private Method serviceMethod;

	private List<Object> paramValues;

	private LocalDateTime startTime;

	public Object execute() throws InvocationTargetException, IllegalAccessException {
		return serviceMethod.invoke(service, paramValues.toArray());
	}
}

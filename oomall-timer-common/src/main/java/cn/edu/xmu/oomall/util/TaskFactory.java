package cn.edu.xmu.oomall.util;

import cn.edu.xmu.oomall.dto.TaskMessageBody;
import cn.edu.xmu.oomall.bo.ExecutableTask;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-22
 */
@Component
public class TaskFactory {

	@Autowired
	private ServiceFactory serviceFactory;

	public ExecutableTask getExecutableTask(TaskMessageBody message) throws ClassNotFoundException, NoSuchMethodException {
		ExecutableTask et = new ExecutableTask();

		et.setStartTime(message.getStartTime());
		et.setId(message.getId());

		Map<Integer, TaskMessageBody.Param> map = new HashMap<>(8);
		for (TaskMessageBody.Param p : message.getParams()) {
			map.put(p.getSequence(), p);
		}

		List<Class> paramTypes = new ArrayList<>();
		List<Object> paramValues = new ArrayList<>();
		for (int i = 0; i < map.size(); i++) {
			TaskMessageBody.Param p = map.get(i);
			Class c = Class.forName(p.getClassName());
			paramTypes.add(c);
			paramValues.add(JSON.parseObject(p.getObjectJson(), c));
		}

		et.setParamValues(paramValues);

		Object service = serviceFactory.get(message.getServiceClassName());
		et.setService(service);
		Method method = service.getClass().getMethod(message.getServiceMethodName(), paramTypes.toArray(new Class[0]));
		et.setServiceMethod(method);

		return et;
	}
}

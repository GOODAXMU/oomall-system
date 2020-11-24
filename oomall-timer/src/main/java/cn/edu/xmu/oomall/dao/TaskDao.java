package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.dto.TaskMessageBody;
import cn.edu.xmu.oomall.entity.MethodPo;
import cn.edu.xmu.oomall.entity.ParamTypePo;
import cn.edu.xmu.oomall.entity.ParamValuePo;
import cn.edu.xmu.oomall.entity.TaskPo;
import cn.edu.xmu.oomall.repository.MethodRepository;
import cn.edu.xmu.oomall.repository.ParamTypeRepository;
import cn.edu.xmu.oomall.repository.ParamValueRepository;
import cn.edu.xmu.oomall.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
@Component
public class TaskDao {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private MethodRepository methodRepository;
	@Autowired
	private ParamTypeRepository paramTypeRepository;
	@Autowired
	private ParamValueRepository paramValueRepository;

	public TaskMessageBody getTaskMessageBody(Long id) {
		TaskPo taskPo = taskRepository.findTaskById(id);
		return getTaskMessageBody(taskPo);
	}

	public TaskMessageBody getTaskMessageBody(TaskPo taskPo) {
		MethodPo methodPo = methodRepository.findMethodById(taskPo.getMethodId());
		List<ParamTypePo> paramTypePos = paramTypeRepository.findParamTypesByMethodId(methodPo.getId());
		List<ParamValuePo> paramValuePos = paramValueRepository.findParamValuesByTaskId(taskPo.getId());

		TaskMessageBody messageBody = new TaskMessageBody();
		messageBody.setId(taskPo.getId());
		messageBody.setStartTime(taskPo.getStartTime());
		messageBody.setPrepareTime(taskPo.getPrepareTime());
		messageBody.setServiceClassName(methodPo.getClassName());
		messageBody.setServiceMethodName(methodPo.getMethodName());
		messageBody.setReturnTypeClassName(methodPo.getReturnTypeClassName());

		messageBody.setTopic(taskPo.getTopic());
		messageBody.setTag(taskPo.getTag());

		Map<Long, ParamValuePo> map = new HashMap<>(8);
		for (ParamValuePo paramValuePo : paramValuePos) {
			map.put(paramValuePo.getParamTypeId(), paramValuePo);
		}
		List<TaskMessageBody.Param> params = new ArrayList<>();
		for (ParamTypePo paramTypePo : paramTypePos) {
			ParamValuePo paramValuePo = map.get(paramTypePo.getId());
			TaskMessageBody.Param p = new TaskMessageBody.Param(
					paramTypePo.getSequence(),
					paramTypePo.getClassName(),
					paramValuePo.getObjectJson()
			);
			params.add(p);
		}

		messageBody.setParams(params);

		return messageBody;
	}

	public List<TaskMessageBody> getTaskMessageBodies(LocalDateTime lower, LocalDateTime upper) {
		List<TaskPo> tasks = taskRepository.findTasksByPrepareTimeBetween(lower, upper);
		List<TaskMessageBody> messages = new ArrayList<>();
		for (TaskPo taskPo : tasks) {
			TaskMessageBody message = getTaskMessageBody(taskPo);
			messages.add(message);
		}
		return messages;
	}

	public boolean addTask(TaskMessageBody task) {
		if (task == null || !task.validate()) {
			return false;
		}

		MethodPo methodPo = new MethodPo();
		methodPo.setMethodName(task.getServiceMethodName());
		methodPo.setClassName(task.getServiceClassName());
		methodPo.setReturnTypeClassName(task.getReturnTypeClassName());
		methodPo = methodRepository.save(methodPo);

		TaskPo taskPo = new TaskPo();
		taskPo.setMethodId(methodPo.getId());
		taskPo.setStartTime(task.getStartTime());
		taskPo.setPrepareTime(task.getPrepareTime());
		taskPo.setTopic(task.getTopic());
		taskPo.setTag(task.getTag());
		taskPo = taskRepository.save(taskPo);

		List<ParamTypePo> paramTypePos = new ArrayList<>();
		for (TaskMessageBody.Param p : task.getParams()) {
			ParamTypePo paramTypePo = new ParamTypePo();
			paramTypePo.setClassName(p.getClassName());
			paramTypePo.setMethodId(methodPo.getId());
			paramTypePo.setSequence(p.getSequence());
			paramTypePos.add(paramTypePo);
		}
		paramTypePos = paramTypeRepository.saveAll(paramTypePos);

		Map<Integer, ParamTypePo> map = new HashMap<>(8);
		for (ParamTypePo p : paramTypePos) {
			map.put(p.getSequence(), p);
		}

		List<ParamValuePo> paramValuePos = new ArrayList<>();
		for (TaskMessageBody.Param p : task.getParams()) {
			ParamValuePo paramValuePo = new ParamValuePo();
			paramValuePo.setTaskId(taskPo.getId());
			paramValuePo.setParamTypeId(map.get(p.getSequence()).getId());
			paramValuePo.setObjectJson(p.getObjectJson());
			paramValuePos.add(paramValuePo);
		}
		paramValueRepository.saveAll(paramValuePos);

		return true;
	}
}

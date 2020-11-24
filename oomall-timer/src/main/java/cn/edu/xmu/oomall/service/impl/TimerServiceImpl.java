package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.dao.TaskDao;
import cn.edu.xmu.oomall.dto.TaskMessageBody;
import cn.edu.xmu.oomall.service.ITimerService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author xincong yao
 */
@DubboService(version = "${timer.version}")
public class TimerServiceImpl implements ITimerService {

	@Autowired
	private TaskDao taskDao;

	@Override
	public Boolean newTimerTask(TaskMessageBody message) {
		if (!message.validate()) {
			return false;
		}

		return taskDao.addTask(message);
	}

}

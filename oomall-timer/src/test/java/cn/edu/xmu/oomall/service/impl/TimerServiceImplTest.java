package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.OomallTimerApplication;
import cn.edu.xmu.oomall.dao.TaskDao;
import cn.edu.xmu.oomall.dto.TaskMessageBody;
import cn.edu.xmu.oomall.service.ITimerService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest(classes = OomallTimerApplication.class)
@RunWith(SpringRunner.class)
public class TimerServiceImplTest {

	@Autowired
	private ITimerService timerService;

	@Test
	public void newTimerTaskTest() throws NoSuchMethodException {
		TaskMessageBody message = new TaskMessageBody();
		message.setStartTime(LocalDateTime.now());
		message.setPrepareTime(LocalDateTime.now());
		Method m = TaskDao.class.getMethod("addTask", TaskMessageBody.class);
		message.setServiceMethodName(m.getName());
		message.setServiceClassName(m.getDeclaringClass().getName());
		message.setReturnTypeClassName(m.getReturnType().getName());

		message.setId(12231L);
		message.setTopic("topic");
		message.setTag("tag");

		TaskMessageBody.Param p = new TaskMessageBody.Param();
		p.setSequence(0);
		p.setClassName(TaskMessageBody.class.getName());
		p.setObjectJson(JSON.toJSONString(message));
		message.setParams(Collections.singletonList(p));
		timerService.newTimerTask(message);
	}
}

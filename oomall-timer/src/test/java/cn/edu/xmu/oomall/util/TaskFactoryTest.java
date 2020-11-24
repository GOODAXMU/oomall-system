package cn.edu.xmu.oomall.util;

import cn.edu.xmu.oomall.OomallTimerApplication;
import cn.edu.xmu.oomall.bo.ExecutableTask;
import cn.edu.xmu.oomall.dao.TaskDao;
import cn.edu.xmu.oomall.dto.TaskMessageBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

@SpringBootTest(classes = OomallTimerApplication.class)
@RunWith(SpringRunner.class)
public class TaskFactoryTest {

	@Autowired
	private TaskFactory taskFactory;

	@Autowired
	private TaskDao taskDao;

	@Test
	public void getExecutableTaskTest() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
		TaskMessageBody message = taskDao.getTaskMessageBody(38L);
		ExecutableTask task = taskFactory.getExecutableTask(message);
		task.execute();
	}
}

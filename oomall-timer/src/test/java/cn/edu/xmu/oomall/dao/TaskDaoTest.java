package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallTimerApplication;
import cn.edu.xmu.oomall.dto.TaskMessageBody;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = OomallTimerApplication.class)
@RunWith(SpringRunner.class)
public class TaskDaoTest {

	@Autowired
	private TaskDao taskDao;

	@Test
	public void getTaskTest() {
		TaskMessageBody t = taskDao.getTaskMessageBody(36L);
		System.out.println(JSON.toJSONString(t));
	}
}

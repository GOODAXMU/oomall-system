package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.OomallTimerApplication;
import cn.edu.xmu.oomall.entity.TaskPo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = OomallTimerApplication.class)
@RunWith(SpringRunner.class)
public class TaskRepositoryTest {

	@Autowired
	private TaskRepository taskRepository;

	@Test
	public void findTasksByPrepareTimeAfterTest() {
		List<TaskPo> ts = taskRepository.findTasksByPrepareTimeBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusSeconds(1));
		System.out.println(JSON.toJSONString(ts));
	}
}

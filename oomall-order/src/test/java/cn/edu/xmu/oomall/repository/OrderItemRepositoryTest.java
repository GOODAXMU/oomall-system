package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.OomallOrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = OomallOrderApplication.class)
@RunWith(SpringRunner.class)
public class OrderItemRepositoryTest {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Test
	public void findOrderIdByIdTest() {
		orderItemRepository.findOrderIdById(1L);
	}

	@Test
	public void findByOrderIdTest() {
		orderItemRepository.findByOrderId(1L);
	}
}

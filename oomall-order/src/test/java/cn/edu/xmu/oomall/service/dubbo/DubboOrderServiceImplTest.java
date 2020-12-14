package cn.edu.xmu.oomall.service.dubbo;

import cn.edu.xmu.oomall.OomallOrderApplication;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.repository.OrderRepository;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = OomallOrderApplication.class)
@RunWith(SpringRunner.class)
@Transactional
public class DubboOrderServiceImplTest {

	@Autowired
	private DubboOrderServiceImpl dubboOrderService;

	@Autowired
	private OrderRepository orderRepository;

	@Test
	public void orderCanBePaidTest() {
		Boolean r1 = dubboOrderService.orderCanBePaid(99999L);
		Assert.assertTrue(r1);

		Boolean r2 = dubboOrderService.orderCanBePaid(1L);
		Assert.assertFalse(r2);

		Boolean r3 = dubboOrderService.orderCanBePaid(Long.MAX_VALUE);
		Assert.assertFalse(r3);
	}

	@Test
	public void isCustomerOwnOrderTest() {
		Boolean r1 = dubboOrderService.isCustomerOwnOrderItem(11L, 99999L);
		Assert.assertTrue(r1);

		Boolean r2 = dubboOrderService.isCustomerOwnOrderItem(22L, 99999L);
		Assert.assertFalse(r2);

		Boolean r3 = dubboOrderService.isCustomerOwnOrderItem(11L, Long.MAX_VALUE);
		Assert.assertFalse(r3);
	}

	@Test
	public void getOrderItemTest() {
		OrderItemDto r1 = dubboOrderService.getOrderItem(99999L);
		Assert.assertEquals(
				"{\"discount\":100,\"id\":99999,\"orderId\":99999,\"price\":500,\"quantity\":3,\"skuId\":123}"
				, JSON.toJSONString(r1)
		);

		OrderItemDto r2 = dubboOrderService.getOrderItem(Long.MAX_VALUE);
		Assert.assertEquals(
				"null"
				, JSON.toJSONString(r2)
		);
	}

	@Test
	public void checkOrderPaidTest() {
		Long orderId = 99999L;
		dubboOrderService.checkOrderPaid(orderId, 641L);
		Integer r1 = orderRepository.findOrderStatusById(orderId);
		Assert.assertEquals((int) r1, OrderStatus.TO_BE_PAID.value());

		dubboOrderService.checkOrderPaid(orderId, 643L);
		Integer r2 = orderRepository.findOrderStatusById(orderId);
		Assert.assertEquals((int) r2, OrderStatus.TO_BE_PAID.value());

		dubboOrderService.checkOrderPaid(orderId, 642L);
		Integer r3 = orderRepository.findOrderStatusById(orderId);
		Assert.assertEquals((int) r3, OrderStatus.PAID.value());
	}


}

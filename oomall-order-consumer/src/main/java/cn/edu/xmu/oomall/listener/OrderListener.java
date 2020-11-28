package cn.edu.xmu.oomall.listener;

import cn.edu.xmu.ooamll.dto.OrderDto;
import cn.edu.xmu.ooamll.dto.OrderItemDto;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import cn.edu.xmu.oomall.repository.OrderRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.consumer.topic}", consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 10, consumerGroup = "${rocketmq.consumer.group}")
public class OrderListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void onMessage(String message) {
		log.info("onMessage: consume message = " + message);
		OrderDto dto = JSON.parseObject(message, OrderDto.class);
		OrderPo parent = OrderPo.toOrderPo(dto);

		parent = orderRepository.save(parent);

		List<OrderPo> children = new ArrayList<>();
		for (OrderDto sub : dto.getSubOrders()) {
			OrderPo t = OrderPo.toOrderPo(sub);
			t.setPid(parent.getId());
			children.add(t);
		}

		children = orderRepository.saveAll(children);

		int i = 0;
		for (OrderDto sub : dto.getSubOrders()) {
			List<OrderItemPo> items = new ArrayList<>();
			for (OrderItemDto item : sub.getOrderItems()) {
				OrderItemPo po = OrderItemPo.toOrderItemPo(item);
				po.setOrderId(children.get(i).getId());
				items.add(po);
			}
			orderItemRepository.saveAll(items);
			i++;
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		log.info("prepareStart: consumerGroup = " + defaultMQPushConsumer.getConsumerGroup());
	}
}

package cn.edu.xmu.oomall.listener;

import cn.edu.xmu.oomall.dto.OrderDto;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.processor.OrderPostProcessor;
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
@RocketMQMessageListener(topic = "${rocketmq.consumer.order.topic}", consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 10, consumerGroup = "${rocketmq.consumer.order.group}")
public class OrderListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderPostProcessor orderPostProcessor;

	@Override
	public void onMessage(String message) {
		log.info("onMessage: consume message = " + message);
		OrderDto dto = JSON.parseObject(message, OrderDto.class);
		OrderPo parent = OrderPo.toOrderPo(dto);

		parent = orderRepository.save(parent);

		if (dto.getOrderItems() == null) {
			return;
		}

		List<OrderItemPo> items = new ArrayList<>();
		for (OrderItemDto item : dto.getOrderItems()) {
			OrderItemPo po = OrderItemPo.toOrderItemPo(item);
			po.setOrderId(dto.getId());
			items.add(po);
		}
		List<OrderItemPo> orderItems = orderItemRepository.saveAll(items);

		orderPostProcessor.sendBeShareIdSetRequest(orderItems, parent.getCustomerId());
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		log.info("OrderListener prepareStart: consumerGroup = " + defaultMQPushConsumer.getConsumerGroup());
	}
}

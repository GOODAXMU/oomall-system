package cn.edu.xmu.oomall.listener;

import cn.edu.xmu.oomall.dto.ShareDto;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-12-7
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.consumer.share.topic}", consumeMode = ConsumeMode.CONCURRENTLY, consumeThreadMax = 10, consumerGroup = "${rocketmq.consumer.group}")
public class ShareListener implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public void onMessage(String message) {
		log.info("onMessage: consume message = " + message);
		ShareDto dto = JSON.parseObject(message, ShareDto.class);

		orderItemRepository.updateBeSharedIdById(dto.getOrderItemId(), dto.getBeSharedId());
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		log.info("ShareListener prepareStart: consumerGroup = " + defaultMQPushConsumer.getConsumerGroup());
	}
}

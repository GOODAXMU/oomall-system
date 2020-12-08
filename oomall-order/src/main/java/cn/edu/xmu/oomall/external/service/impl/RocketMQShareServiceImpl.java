package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.external.service.IShareService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Slf4j
@Component
public class RocketMQShareServiceImpl implements IShareService {

	@Resource
	private RocketMQTemplate rocketMQTemplate;

	private final static String TOPIC = "share-confirm";


	@Override
	public void sendShareMessage(OrderItem orderItem) {
		OrderItemDto dto = orderItem.toOrderItemDto();
		String json = JSON.toJSONString(dto);
		Message message = MessageBuilder.withPayload(json).build();
		log.info("sendOneWay: message = " + message + ", topic = " + TOPIC);
		rocketMQTemplate.sendOneWay(TOPIC, message);
	}
}

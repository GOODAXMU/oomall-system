package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IMessageSender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
@Slf4j
@Component
public class RocketMQSender implements IMessageSender {
	@Resource
	private RocketMQTemplate rocketMQTemplate;

	@Override
	public void sendOneWay(Object o, String topic) {
		String json = JSON.toJSONString(o);
		Message message = MessageBuilder.withPayload(json).build();
		log.info("sendOneWay: message = " + message + ", topic = " + topic);
		rocketMQTemplate.sendOneWay(topic, message);
	}

	@Override
	public void sendAsynchronous(Object o, String topic) {
		String json = JSON.toJSONString(o);
		log.info("sendAsynchronous: message = "+ json + ", topic = " + topic);
		rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(json).build(), new SendCallback() {
			@Override
			public void onSuccess(SendResult sendResult) {
				log.debug("sendAsynchronous: onSuccess result = " + sendResult);
			}

			@Override
			public void onException(Throwable throwable) {
				log.info("sendAsynchronous: onException e = "+ throwable.getMessage());
			}
		});
	}

	@Override
	public void sendSynchronous(Object o, String topic) {

	}
}

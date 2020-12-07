package cn.edu.xmu.oomall.processor;

import cn.edu.xmu.oomall.dto.ShareDto;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.sender.IMessageSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-7
 */
@Component
public class OrderPostProcessor {

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private static final String TOPIC = "share-pre";

	public void sendBeShareIdSetRequest(List<OrderItemPo> orderItems, Long customerId) {
		for (OrderItemPo oi : orderItems) {
			ShareDto r = new ShareDto();
			r.setOrderItemId(oi.getId());
			r.setCustomerId(customerId);
			r.setSkuId(oi.getGoodsSkuId());
			sender.sendOneWay(r, TOPIC);
		}
	}
}

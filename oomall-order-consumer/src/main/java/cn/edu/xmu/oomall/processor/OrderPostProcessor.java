package cn.edu.xmu.oomall.processor;

import cn.edu.xmu.oomall.dto.CartDto;
import cn.edu.xmu.oomall.dto.ShareDto;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.sender.IMessageSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-7
 */
@Component
public class OrderPostProcessor {

	@Resource(name = "rocketMQSender")
	private IMessageSender sender;

	private static final String SHARE_TOPIC = "share-request";

	private static final String CART_TOPIC = "cart";

	public void sendBeShareIdSetRequest(List<OrderItemPo> orderItems, Long customerId) {
		for (OrderItemPo oi : orderItems) {
			ShareDto r = new ShareDto();
			r.setOrderItemId(oi.getId());
			r.setCustomerId(customerId);
			r.setSkuId(oi.getGoodsSkuId());
			sender.sendOneWay(r, SHARE_TOPIC);
		}
	}

	public void sendRemoveCartItemsRequest(List<OrderItemPo> orderItems, Long customerId) {
		CartDto cartDto = new CartDto();
		cartDto.setCustomerId(customerId);
		List<Long> skuIds = new ArrayList<>();
		for (OrderItemPo oi : orderItems) {
			skuIds.add(oi.getGoodsSkuId());
		}
		cartDto.setSkuIdList(skuIds);

		sender.sendOneWay(cartDto, CART_TOPIC);
	}
}

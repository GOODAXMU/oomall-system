package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IDiscountService;
import cn.edu.xmu.goods.client.dubbo.OrderItemDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Component
public class WangDiscountServiceImpl implements IDiscountService {

	// todo 外部服务未配置
	// @DubboReference(version = "${oomall.external.discount-service.version}", cache = "false", async = true, timeout = 5000)
	private cn.edu.xmu.goods.client.IDiscountService discountService;

	@Override
	public Map<Long, Long> calcDiscount(List<OrderItem> orderItems) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}

		return discountService.calcDiscount(dto);
	}

	@Override
	public CompletableFuture<Map<Long, Long>> calcDiscountAsynchronous(List<OrderItem> orderItems) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}

		return RpcContext.getContext().asyncCall(
				() -> discountService.calcDiscount(dto)
		);
	}

	private OrderItemDTO toOrderItemDTO(OrderItem oi) {
		OrderItemDTO d = new OrderItemDTO();
		d.setId(oi.getId());
		d.setName(oi.getName());
		d.setOrderId(oi.getOrderId());
		d.setPrice(oi.getPrice());
		d.setQuantity(oi.getQuantity());
		d.setDiscount(oi.getDiscount());
		d.setSkuId(oi.getSkuId());
		d.setBeShareId(oi.getBeShareId());
		d.setCouponActivityId(oi.getCouponActivityId());

		return d;
	}
}

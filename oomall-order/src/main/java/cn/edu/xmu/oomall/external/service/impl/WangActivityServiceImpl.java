package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IActivityService;
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
public class WangActivityServiceImpl implements IActivityService {

	// todo 外部服务未配置
	// @DubboReference(version = "${oomall.external.activity-service.version}", cache = "false", async = true, timeout = 5000)
	private cn.edu.xmu.goods.client.IActivityService activityService;

	@Override
	public Map<Long, Long> validateActivity(List<OrderItem> orderItems, Long couponId) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}
		return activityService.validateActivity(dto, couponId);
	}

	@Override
	public CompletableFuture<Map<Long, Long>> validateActivityAsynchronous(List<OrderItem> orderItems, Long couponId) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}
		return RpcContext.getContext().asyncCall(
				() -> activityService.validateActivity(dto, couponId)
		);
	}

	@Override
	public Boolean useCoupon(Long couponId) {
		return false;
	}

	@Override
	public Long getGrouponId(Long skuId) {
		return null;
	}

	@Override
	public Long getPreSale(Long skuId) {
		return null;
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

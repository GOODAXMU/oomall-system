package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IActivityService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
@Component
public class SimulatedActivityServiceImpl implements IActivityService {

	@Override
	public Map<Long, Long> validateActivity(List<OrderItem> orderItems, Long couponId) {
		if (orderItems == null) {
			return new HashMap<>(1);
		}
		Map<Long, Long> map = new HashMap<>(orderItems.size());
		for (OrderItem oi : orderItems) {
			map.put(oi.getSkuId(), oi.getCouponActivityId());
		}

		return map;
	}

	@Override
	public CompletableFuture<Map<Long, Long>> validateActivityAsynchronous(List<OrderItem> orderItems, Long couponId) {
		return null;
	}

	@Override
	public Boolean useCoupon(Long couponId) {
		return true;
	}
}

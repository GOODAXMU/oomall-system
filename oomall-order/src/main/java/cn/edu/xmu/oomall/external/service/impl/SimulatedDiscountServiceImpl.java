package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IDiscountService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedDiscountServiceImpl implements IDiscountService {

	private static final String USE_COUPON = "coupon";
	private static final String DISCOUNT_PRICE = "discount";
	private static final String COUPON_ACTIVITY = "activity";


	@Override
	public Map<Long, Long> calcDiscount(List<OrderItem> orderItems) {
		return null;
	}

	@Override
	public CompletableFuture<Map<Long, Long>> calcDiscountAsynchronous(List<OrderItem> orderItems) {
		CompletableFuture<Map<Long, Long>> cf = new CompletableFuture<>();
		Map<Long, Long> map = new HashMap<>(orderItems.size());
		for (OrderItem oi : orderItems) {
			map.put(oi.getSkuId(), 3L);
		}
		cf.complete(map);
		return cf;
	}
}

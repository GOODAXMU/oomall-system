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
	public Map<String, Object> calcDiscount(List<OrderItem> orderItems, Long couponId) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(USE_COUPON, Collections.singletonList(true));
		map.put(DISCOUNT_PRICE, Collections.singletonList(10L));
		map.put(COUPON_ACTIVITY, Collections.singletonList(100001L));
		return map;
	}

	@Override
	public CompletableFuture<Map<String, Object>> calcDiscountAsynchronous(List<OrderItem> orderItems, Long couponId) {
		return new CompletableFuture<>();
	}

	@Override
	public List<Boolean> useCoupon(CompletableFuture<Map<String, Object>> c) {
		return Collections.singletonList(true);
	}

	@Override
	public List<Long> getDiscountPrice(CompletableFuture<Map<String, Object>> c) {
		return Collections.singletonList(10L);
	}

	@Override
	public List<Long> getCouponActivity(CompletableFuture<Map<String, Object>> c) {
		return Collections.singletonList(10001L);
	}

	@Override
	public List<Boolean> useCoupon(Map<String, Object> map) {
		if (map == null) {
			return Collections.emptyList();
		}
		Object o = map.get(USE_COUPON);
		return o != null ? (List<Boolean>) o : Collections.emptyList();
	}

	@Override
	public List<Long> getDiscountPrice(Map<String, Object> map) {
		return getLongList(map, DISCOUNT_PRICE);
	}

	@Override
	public List<Long> getCouponActivity(Map<String, Object> map) {
		return getLongList(map, COUPON_ACTIVITY);
	}

	private List<Long> getLongList(Map<String, Object> map, String key) {
		if (map == null) {
			return Collections.emptyList();
		}
		Object o = map.get(key);
		return o == null ? Collections.emptyList() : (List<Long>) o;
	}
}

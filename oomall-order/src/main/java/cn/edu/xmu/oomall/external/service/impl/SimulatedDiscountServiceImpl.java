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
	public List<OrderItem> calcDiscount(List<OrderItem> orderItems) {
		return null;
	}

	@Override
	public CompletableFuture<List<OrderItem>> calcDiscountAsynchronous(List<OrderItem> orderItems) {
		return null;
	}
}
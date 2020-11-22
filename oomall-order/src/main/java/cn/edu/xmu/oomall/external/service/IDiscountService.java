package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IDiscountService {

	/**
	 * 计算折扣
	 * @param orderItems
	 * @return
	 */
	List<OrderItem> calcDiscount(List<OrderItem> orderItems);

	/**
	 * 异步计算折扣
	 * @param orderItems
	 * @return
	 */
	CompletableFuture<List<OrderItem>> calcDiscountAsynchronous(List<OrderItem> orderItems);
}

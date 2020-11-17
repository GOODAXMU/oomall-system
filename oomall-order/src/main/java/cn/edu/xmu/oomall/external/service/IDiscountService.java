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
	 * @param couponId
	 * @return
	 */
	Map<String, Object> calcDiscount(List<OrderItem> orderItems, Long couponId);

	/**
	 * 异步计算折扣
	 * @param orderItems
	 * @param couponId
	 * @return
	 */
	CompletableFuture<Map<String, Object>> calcDiscountAsynchronous(List<OrderItem> orderItems, Long couponId);

	/**
	 * 查看是否使用优惠券
	 * @param c
	 * @return
	 */
	List<Boolean> useCoupon(CompletableFuture<Map<String, Object>> c);

	/**
	 * 查看优惠价格
	 * @param c
	 * @return
	 */
	List<Long> getDiscountPrice(CompletableFuture<Map<String, Object>> c);

	/**
	 * 查看参与的优惠活动
	 * @param c
	 * @return
	 */
	List<Long> getCouponActivity(CompletableFuture<Map<String, Object>> c);

	/**
	 * 查看是否使用优惠券
	 * @param map
	 * @return
	 */
	List<Boolean> useCoupon(Map<String, Object> map);

	/**
	 * 查看优惠价格
	 * @param map
	 * @return
	 */
	List<Long> getDiscountPrice(Map<String, Object> map);

	/**
	 * 查看参与的优惠活动
	 * @param map
	 * @return
	 */
	List<Long> getCouponActivity(Map<String, Object> map);
}

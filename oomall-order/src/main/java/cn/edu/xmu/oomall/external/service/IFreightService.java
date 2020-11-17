package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IFreightService {

	/**
	 * 计算订单项列表的运费
	 * 所有的订单项需在同一个商铺下
	 * @param orderItems
	 * @return
	 */
	Long calcAndGetFreightPrice(List<OrderItem> orderItems);

	/**
	 * 异步计算运费
	 * @param orderItems
	 * @return
	 */
	CompletableFuture<Long> calcFreightPriceAsynchronous(List<OrderItem> orderItems);

	/**
	 * 获取异步计算的运费
	 * @param cf
	 * @throws ExecutionException if this future completed exceptionally
	 * @throws InterruptedException if the current thread was interrupted
	 * @return
	 */
	Long getFreightPrice(CompletableFuture<Long> cf) throws ExecutionException, InterruptedException;
}

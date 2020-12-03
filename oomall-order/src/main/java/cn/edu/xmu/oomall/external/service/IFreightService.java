package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.vo.Reply;

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
	Long calcAndGetFreightPrice(List<OrderItem> orderItems, Long regionId, boolean isSeckill);

	/**
	 * 异步计算运费
	 * @param orderItems
	 * @return
	 */
	CompletableFuture<Long> calcFreightPriceAsynchronous(List<OrderItem> orderItems, Long regionId, boolean isSeckill);
}

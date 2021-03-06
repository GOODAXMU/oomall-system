package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-11-27
 */
public interface IActivityService {

	/**
	 * 校验sku是否适用与给定的优惠活动
	 * 以及优惠卷是否可用
	 * @param orderItems
	 * @param couponId
	 * @return skuId到couponActivityId的映射
	 */
	Map<Long, Long> validateActivity(List<OrderItem> orderItems, Long couponId);

	/**
	 * 异步校验sku是否适用与给定的优惠活动
	 * 以及优惠卷是否可用
	 * @param orderItems
	 * @param couponId
	 * @return skuId到couponActivityId的映射
	 */
	CompletableFuture<Map<Long, Long>> validateActivityAsynchronous(List<OrderItem> orderItems, Long couponId);

	/**
	 * 根据skuId获取团购活动id
	 * @param skuId
	 * @return 不存在返回null
	 */
	Long getGrouponId(Long skuId);

	/**
	 * 根据skuId获取预售活动id
	 * @param skuId
	 * @return 不存在返回null
	 */
	Long getPreSaleId(Long skuId);

	/**
	 * 获取预售活动的定金
	 * @param preSaleId
	 * @return
	 */
	Long getPreSaleDeposit(Long preSaleId);

	/**
	 * 获取预售活动的尾款
	 * @param preSaleId
	 * @return
	 */
	Long getPreSaleBalance(Long preSaleId);
}

package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface IGoodsService {

	/**
	 * 获取价格
	 * @param skuId
	 * @return
	 */
	Long getPrice(Long skuId);

	/**
	 * 设置OrderItems需要的sku信息
	 * 主要用于设置团购与预售订单项的sku信息
	 * @param orderItems
	 * @return
	 */
	void setSkuInformation(List<OrderItem> orderItems, Integer type);
}

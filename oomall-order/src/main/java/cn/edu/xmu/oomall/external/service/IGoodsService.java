package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface IGoodsService {

	/**
	 * 设置订单项的sku信息
	 * @param orderItems
	 * @return
	 */
	void setSkuInformation(List<OrderItem> orderItems, Integer type);
}

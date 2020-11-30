package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;

import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IShopService {

	/**
	 * 获取商铺消息
	 * @param skuId
	 * @return
	 */
	Shop getShop(Long skuId);

	/**
	 * 根据sku所属商铺划分orderItem
	 * 返回的OrderItem需要skuId, name, quantity, price
	 * @param orderItems
	 * @return
	 */
	Map<Shop, List<OrderItem>> classifySku(List<OrderItem> orderItems);
}

package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.OrderItem;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IInventoryService {

	/**
	 * 修改一批订单项对应的sku库存
	 * 部分sku库存不足不影响其他sku
	 * 返回不能为null
	 * @param orderItems 订单项列表
	 * @param type 订单类型
	 * @return 扣库存成功的orderItem列表
	 */
	List<OrderItem> modifyInventory(List<OrderItem> orderItems, Integer type);
}

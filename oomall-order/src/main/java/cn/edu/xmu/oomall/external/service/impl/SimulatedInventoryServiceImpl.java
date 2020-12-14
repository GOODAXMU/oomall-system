package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IInventoryService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedInventoryServiceImpl implements IInventoryService {

	@Override
	public List<OrderItem> modifyInventory(List<OrderItem> orderItems, Integer type, Long activityId) {
		return orderItems;
	}
}

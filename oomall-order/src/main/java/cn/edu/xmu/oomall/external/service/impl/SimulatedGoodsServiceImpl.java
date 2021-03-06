package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IGoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Service
public class SimulatedGoodsServiceImpl implements IGoodsService {

	@Override
	public void setSkuInformation(List<OrderItem> orderItems, Integer type) {
		for (OrderItem orderItem : orderItems) {
			orderItem.setName("Asku");
			orderItem.setPrice(12L);
		}
	}

}

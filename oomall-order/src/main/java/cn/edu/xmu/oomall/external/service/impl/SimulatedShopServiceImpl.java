package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.external.service.IShopService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedShopServiceImpl implements IShopService {

	@Override
	public Shop getShop(Long skuId) {
		Shop shop = new Shop();
		shop.setId(345L);
		shop.setName("super shop");
		shop.setGmtCreateTime(LocalDateTime.parse("2020-11-27T07:59:58.623756500").toString());
		shop.setGmtModiTime(LocalDateTime.parse("2020-11-27T07:59:58.623756500").toString());
		return shop;
	}

	@Override
	public Map<Shop, List<OrderItem>> classifySku(List<OrderItem> orderItems) {
		Map<Shop, List<OrderItem>> result = new HashMap<>(orderItems.size());
		for (OrderItem oi : orderItems) {
			oi.setPrice(123L);
			Shop shop = new Shop();
			shop.setId(oi.getSkuId() + 100);
			shop.setName("super shop " + (oi.getSkuId() + 100));
			shop.setGmtCreateTime(LocalDateTime.now().toString());
			shop.setGmtModiTime(LocalDateTime.now().toString());
			result.put(shop, Collections.singletonList(oi));
		}
		return result;
	}
}

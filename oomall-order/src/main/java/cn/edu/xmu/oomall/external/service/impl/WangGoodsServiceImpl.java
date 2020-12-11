package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.goods.client.dubbo.OrderItemDTO;
import cn.edu.xmu.goods.client.dubbo.PriceDTO;
import cn.edu.xmu.goods.client.dubbo.SkuDTO;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.external.service.IGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Component
public class WangGoodsServiceImpl implements IGoodsService {

	@DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", async = true, timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IGoodsService goodsService;

	@DubboReference(version = "${oomall.external.activity-service.version}", cache = "false", async = true, timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IActivityService activityService;

	@Override
	public void setSkuInformation(List<OrderItem> orderItems, Integer type) {
		List<OrderItemDTO> dtos = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			OrderItemDTO dto = new OrderItemDTO();
			dto.setSkuId(oi.getSkuId());
		}

		List<PriceDTO> prices;
		if (type == OrderType.NORMAL.value()) {
			prices = goodsService.getPriceAndName(dtos);
		} else {
			prices = activityService.getPriceAndName(dtos, type);
		}

		Map<Long, PriceDTO> map = new HashMap<>(8);
		for (PriceDTO p : prices) {
			map.put(p.getSkuId(), p);
		}

		for (OrderItem oi : orderItems) {
			PriceDTO price = map.get(oi.getSkuId());
			oi.setName(price.getName());
			long prePrice = price.getPrePrice() == null ? 0 : price.getPrePrice();
			long finalPrice = price.getFinalPrice() == null ? 0 : price.getFinalPrice();
			oi.setPrice(prePrice + finalPrice);
		}
	}
}

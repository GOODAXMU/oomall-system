package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.external.service.IShopService;
import cn.edu.xmu.goods.client.dubbo.OrderItemDTO;
import cn.edu.xmu.goods.client.dubbo.ShopDTO;
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
public class WangShopServiceImpl implements IShopService {

	@DubboReference(version = "${oomall.external.shop-service.version}", cache = "false", async = true, timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IShopService shopService;

	@DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", async = true, timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IGoodsService goodsService;

	@Override
	public Shop getShop(Long skuId) {
		ShopDTO shopDTO = shopService.getShopById(skuId);
		return toShop(shopDTO);
	}

	@Override
	public Map<Shop, List<OrderItem>> classifySku(List<OrderItem> orderItems) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}
		Map<ShopDTO, List<OrderItemDTO>> r = goodsService.classifySku(dto);

		Map<Long, OrderItem> orderItemMap = new HashMap<>();
		for (OrderItem oi : orderItems) {
			orderItemMap.put(oi.getSkuId(), oi);
		}
		Map<Shop, List<OrderItem>> map = new HashMap<>();
		for (Map.Entry<ShopDTO, List<OrderItemDTO>> e : r.entrySet()) {
			Shop shop = toShop(e.getKey());
			List<OrderItem> ois = new ArrayList<>();
			for (OrderItemDTO oiDTO : e.getValue()) {
				OrderItem oi = orderItemMap.get(oiDTO.getSkuId());
				oi.setPrice(oiDTO.getPrice());
				oi.setName(oiDTO.getName());
				ois.add(oi);
			}

			map.put(shop, ois);
		}

		return map;
	}

	private OrderItemDTO toOrderItemDTO(OrderItem oi) {
		OrderItemDTO d = new OrderItemDTO();
		d.setId(oi.getId());
		d.setName(oi.getName());
		d.setOrderId(oi.getOrderId());
		d.setPrice(oi.getPrice());
		d.setQuantity(oi.getQuantity());
		d.setDiscount(oi.getDiscount());
		d.setSkuId(oi.getSkuId());
		d.setBeShareId(oi.getBeShareId());
		d.setCouponActivityId(oi.getCouponActivityId());

		return d;
	}

	private Shop toShop(ShopDTO shopDTO) {
		Shop shop = new Shop();
		shop.setId(shopDTO.getId());
		shop.setName(shopDTO.getName());
		shop.setGmtCreateTime(shopDTO.getGmtCreateTime().toString());
		shop.setGmtModiTime(shopDTO.getGmtModiTime().toString());
		// todo shop.setState(shopDTO.getState());
		return shop;
	}
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.goods.client.dubbo.SkuDTO;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Component
public class WangGoodsServiceImpl implements IGoodsService {

	@DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", async = true, timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IGoodsService goodsService;

	@Override
	public Long getPrice(Long skuId) {
		return goodsService.getPrice(skuId);
	}

	@Override
	public void setSkuInformation(List<OrderItem> orderItems, Integer type) {
		// todo 使用效率更高的接口
		for (OrderItem orderItem : orderItems) {
			SkuDTO sku = goodsService.getSku(orderItem.getSkuId());
			orderItem.setPrice(sku.getPrice());
			orderItem.setName(sku.getName());
		}
	}
}

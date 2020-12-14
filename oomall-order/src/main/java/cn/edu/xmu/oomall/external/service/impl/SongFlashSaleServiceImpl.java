package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.goods.client.dubbo.OrderItemDTO;
import cn.edu.xmu.goods.client.dubbo.PriceDTO;
import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IFlashSaleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-11
 */
@Component
public class SongFlashSaleServiceImpl implements IFlashSaleService {

	@DubboReference(version = "${oomall.external.flash-sale-service.version}", cache = "false", timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IFlashSaleService flashSaleService;

	@DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", timeout = 5000, check = false)
	private cn.edu.xmu.goods.client.IGoodsService goodsService;

	@Override
	public Long getSeckillId(Long skuId) {
		return flashSaleService.getSeckillId(skuId);
	}

	@Override
	public void setPriceAndName(OrderItem orderItem) {
		Long price = flashSaleService.getPrice(orderItem.getSkuId());

		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setSkuId(orderItem.getSkuId());
		List<PriceDTO> priceDTOS = goodsService.getPriceAndName(Collections.singletonList(orderItemDTO));

		if (priceDTOS == null || priceDTOS.size() < 1) {
			return;
		}

		orderItem.setPrice(price);
		orderItem.setName(priceDTOS.get(0).getName());
	}
}

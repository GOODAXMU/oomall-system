package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IFlashSaleService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Component
public class SimulatedFlashSaleServiceImpl implements IFlashSaleService {

	@Override
	public Long getSeckillId(Long skuId) {
		return 12L;
	}

	@Override
	public void setPriceAndName(OrderItem orderItem) {
		orderItem.setPrice(888L);
		orderItem.setName("super goods");
	}
}

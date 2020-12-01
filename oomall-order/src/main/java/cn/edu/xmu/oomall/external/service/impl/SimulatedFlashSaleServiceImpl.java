package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IFlashSaleService;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Component
public class SimulatedFlashSaleServiceImpl implements IFlashSaleService {

	@Override
	public Boolean isSeckill(Long skuId) {
		return false;
	}

	@Override
	public Long getPrice(Long skuId) {
		return 888L;
	}
}

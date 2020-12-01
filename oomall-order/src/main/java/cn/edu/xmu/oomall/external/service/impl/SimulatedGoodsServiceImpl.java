package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IGoodsService;
import org.springframework.stereotype.Service;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Service
public class SimulatedGoodsServiceImpl implements IGoodsService {

	@Override
	public Long getPrice(Long skuId) {
		return 666L;
	}
}

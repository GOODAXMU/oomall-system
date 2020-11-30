package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.ISeckillService;
import org.springframework.stereotype.Service;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
@Service
public class SimulatedSeckillServiceImpl implements ISeckillService {

	@Override
	public Boolean deductInventory(Long skuId, Integer quantity) {
		return true;
	}
}

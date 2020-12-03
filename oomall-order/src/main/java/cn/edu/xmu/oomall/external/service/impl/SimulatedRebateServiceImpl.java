package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IRebateService;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedRebateServiceImpl implements IRebateService {

	@Override
	public Integer useRebate(Long customerId, Integer num) {
		return num;
	}
}

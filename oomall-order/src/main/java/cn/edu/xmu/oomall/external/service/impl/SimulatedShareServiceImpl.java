package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IShareService;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedShareServiceImpl implements IShareService {

	@Override
	public void sendShareMessage(Long beSharedId, Long orderItemId) {
	}

	@Override
	public Long getBeSharedId(Long customerId, Long skuId) {
		return 111L;
	}
}

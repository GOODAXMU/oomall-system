package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.constant.SeckillResponseStatus;
import cn.edu.xmu.oomall.dto.SeckillInventoryRequest;
import cn.edu.xmu.oomall.dto.SeckillResult;
import cn.edu.xmu.oomall.external.service.ISeckillService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-12-2
 */
@Component
public class GoodaSeckillServiceImpl implements ISeckillService {

	// todo 外部服务未配置
	// @DubboReference(version = "${oomall.external.seckill-service.version}")
	private cn.edu.xmu.oomall.service.ISeckillService seckillService;

	@Override
	public Boolean deductInventory(Long skuId, Integer quantity, Long seckillId) {
		SeckillInventoryRequest request = new SeckillInventoryRequest();
		request.setSkuId(skuId);
		request.setQuantity(-quantity);
		request.setSeckillId(seckillId);
		Map<String, Object> r = seckillService.modifyInventory(request);
		return SeckillResponseStatus.OK.equals(SeckillResult.getStatus(r));
	}
}

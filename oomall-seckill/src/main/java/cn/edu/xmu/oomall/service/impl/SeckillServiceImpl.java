package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.constant.SeckillResponseStatus;
import cn.edu.xmu.oomall.dto.SeckillResult;
import cn.edu.xmu.oomall.dao.SeckillDao;
import cn.edu.xmu.oomall.dto.*;
import cn.edu.xmu.oomall.service.ISeckillService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


/**
 * @author xincong yao
 * @date 2020-11-3
 */
@DubboService(version = "${seckill.version}")
public class SeckillServiceImpl implements ISeckillService {

	@Autowired
	private SeckillDao seckillDao;

	@Override
	public Map<String, Object> loadSeckill(SeckillLoadRequest request) {
		if (request == null || request.getSeckillId() == null) {
			return SeckillResult.getResult(SeckillResponseStatus.PARAM_ERROR);
		}

		return seckillDao.loadSeckill(request.getSeckillId(), request.getBatchSize());
	}

	@Override
	public Map<String, Object> unloadSeckill(SeckillUnloadRequest request) {
		if (request == null || request.getSeckillId() == null) {
			return SeckillResult.getResult(SeckillResponseStatus.PARAM_ERROR);
		}

		return seckillDao.unloadSeckill(request.getSeckillId());
	}

	@Override
	public Map<String, Object> modifySeckillStatus(SeckillStatusModifyRequest request) {
		if (request == null || request.getSeckillId() == null || request.getSwitchOn() == null) {
			return SeckillResult.getResult(SeckillResponseStatus.PARAM_ERROR);
		}

		return seckillDao.modifySeckillStatus(request.getSeckillId(), request.getSwitchOn());
	}

	@Override
	public Map<String, Object> modifyInventory(SeckillInventoryRequest request) {
		if (request == null || request.getSeckillId() == null
				|| request.getQuantity() == null || request.getSkuId() == null) {
			return SeckillResult.getResult(SeckillResponseStatus.PARAM_ERROR);
		}

		return seckillDao.modifyInventory(request.getSeckillId(), request.getSkuId(), request.getQuantity());
	}
}

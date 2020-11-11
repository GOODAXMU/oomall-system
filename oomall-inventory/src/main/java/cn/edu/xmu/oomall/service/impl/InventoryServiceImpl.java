package cn.edu.xmu.oomall.service.impl;

import cn.edu.xmu.oomall.constant.InventoryResponseStatus;
import cn.edu.xmu.oomall.dao.InventoryDao;
import cn.edu.xmu.oomall.dto.*;
import cn.edu.xmu.oomall.service.IInventoryService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


/**
 * @author xincong yao
 * @date 2020-11-3
 */
@DubboService(version = "${inventory.version}")
public class InventoryServiceImpl implements IInventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public Map<String, Object> modifyInventory(InventoryRequest request) {
		if (request == null || request.getQuantity() == null
				|| request.getSkuId() == null) {
			return InventoryResult.getResult(InventoryResponseStatus.PARAM_ERROR);
		}

		return inventoryDao.modifyInventory(request.getSkuId(), request.getQuantity());
	}
}

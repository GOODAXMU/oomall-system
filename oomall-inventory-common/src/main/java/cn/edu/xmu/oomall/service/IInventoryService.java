package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.InventoryRequest;

import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-11
 */
public interface IInventoryService {

	/**
	 * 修改库存
	 * @param request
	 * @return
	 */
	Map<String, Object> modifyInventory(InventoryRequest request);
}

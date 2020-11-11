package cn.edu.xmu.oomall.dto;

import cn.edu.xmu.oomall.constant.InventoryResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-11
 */
public class InventoryResult {

	private final static String statusIndex = "status";

	private final static String dataIndex = "data";

	public static Map<String, Object> getResult(InventoryResponseStatus status) {
		return getResult(status, null);
	}

	public static Map<String, Object> getResult(InventoryResponseStatus status, Object data) {
		Map<String, Object> r = new HashMap<>(2);
		r.put(statusIndex, status);
		r.put(dataIndex, data);
		return r;
	}
}

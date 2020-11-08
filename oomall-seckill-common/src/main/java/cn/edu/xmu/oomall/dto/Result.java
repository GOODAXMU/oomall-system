package cn.edu.xmu.oomall.dto;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
public class Result {

	private final static String statusIndex = "status";

	private final static String dataIndex = "data";

	public static Map<String, Object> getResult(ResponseStatus status) {
		return getResult(status, null);
	}

	public static Map<String, Object> getResult(ResponseStatus status, Object data) {
		Map<String, Object> r = new HashMap<>(2);
		r.put(statusIndex, status);
		r.put(dataIndex, data);
		return r;
	}

	public static ResponseStatus getStatus(Map<String, Object> map) {
		Object o = map.get(statusIndex);
		if (o instanceof ResponseStatus) {
			return (ResponseStatus) o;
		}
		return null;
	}

	public static Object getData(Map<String, Object> map) {
		return map.get(dataIndex);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Load {
		private Integer totalCount;
		private Integer successCount;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Unload {
		private Integer totalCount;
	}

}

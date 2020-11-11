package cn.edu.xmu.oomall.dto;

import cn.edu.xmu.oomall.constant.SeckillResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
public class SeckillResult {

	private final static String statusIndex = "status";

	private final static String dataIndex = "data";

	public static Map<String, Object> getResult(SeckillResponseStatus status) {
		return getResult(status, null);
	}

	public static Map<String, Object> getResult(SeckillResponseStatus status, Object data) {
		Map<String, Object> r = new HashMap<>(2);
		r.put(statusIndex, status);
		r.put(dataIndex, data);
		return r;
	}

	public static SeckillResponseStatus getStatus(Map<String, Object> map) {
		Object o = map.get(statusIndex);
		if (o instanceof SeckillResponseStatus) {
			return (SeckillResponseStatus) o;
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

package cn.edu.xmu.oomall.dto;

import cn.edu.xmu.oomall.constant.SeckillResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
public class SeckillResult {

	private final static String STATUS = "status";

	private final static String DATA = "data";

	public static Map<String, Object> getResult(SeckillResponseStatus status) {
		return getResult(status, null);
	}

	public static Map<String, Object> getResult(SeckillResponseStatus status, Object data) {
		Map<String, Object> r = new HashMap<>(2);
		r.put(STATUS, status);
		r.put(DATA, data);
		return r;
	}

	public static SeckillResponseStatus getStatus(Map<String, Object> map) {
		Object o = map.get(STATUS);
		if (o instanceof SeckillResponseStatus) {
			return (SeckillResponseStatus) o;
		}
		return null;
	}

	public static Object getData(Map<String, Object> map) {
		return map.get(DATA);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Load implements Serializable {
		private Integer totalCount;
		private Integer successCount;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Unload implements Serializable {
		private Integer totalCount;
	}

}

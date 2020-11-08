package cn.edu.xmu.oomall.cache.impl;

import cn.edu.xmu.oomall.cache.ICache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xincong yao
 * @date 2020-10-31
 */
@Component
public class LocalCache implements ICache {

	private Map<Object, Map<Object, Object>> map = new ConcurrentHashMap<>();

	@Override
	public void set(Object h, Object hk, Object o) {
		if (h == null || hk == null || o == null) {
			return;
		}
		Map<Object, Object> subMap = map.computeIfAbsent(h, k -> new ConcurrentHashMap<>());
		// 防止delete方法调用导致空指针异常
		try {
			subMap.put(hk, o);
		} catch (NullPointerException e) {
		}
	}

	@Override
	public Object get(Object h, Object hk) {
		if (h == null || hk == null) {
			return null;
		}
		Map<Object, Object> subMap = map.get(h);
		// 防止delete方法调用导致空指针异常
		try {
			return subMap.get(hk);
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public Long modify(Object h, Object hk, Long v) {
		if (h == null || hk == null || v == null) {
			return MODIFY_ERROR_CODE;
		}
		Map<Object, Object> subMap = map.get(h);
		if (subMap == null) {
			return MODIFY_ERROR_CODE;
		}
		AtomicReference<Long> result = new AtomicReference<>(SUCCESS);
		Long r = (Long) subMap.computeIfPresent(hk, (k, va) -> {
			Long t = (Long) va;
			if (t + v >= 0) {
				t += v;
			} else {
				result.set(MODIFY_ERROR_CODE);
			}
			return t;
		});
		return result.get().equals(SUCCESS) ? r : result.get();
	}

	@Override
	public Long fetch(Object h, Object hk) {
		if (h == null || hk == null) {
			return FETCH_ERROR_CODE;
		}
		Map<Object, Object> subMap = map.get(h);
		AtomicReference<Long> result = new AtomicReference<>(SUCCESS);
		// 防止delete方法调用导致空指针异常
		try {
			subMap.computeIfPresent(hk, (k, va) -> {
				result.set((Long) va);
				return 0;
			});
		} catch (NullPointerException e) {
			return FETCH_ERROR_CODE;
		}

		subMap.remove(hk);
		return result.get();
	}

	@Override
	public Long delete(Object h, Object... hk) {
		if (h == null) {
			return 0L;
		}
		if (hk == null || hk.length == 0) {
			return (long) map.remove(h).size();
		}
		Map<Object, Object> subMap = map.get(h);
		Long r = 0L;
		for (Object o : hk) {
			if (o != null) {
				try {
					if (subMap.remove(o) != null) {
						r++;
					}
				} catch (NullPointerException e) {
					return r;
				}
			}
		}
		return r;
	}
}

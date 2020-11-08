package cn.edu.xmu.oomall.cache.impl;

import cn.edu.xmu.oomall.cache.ICache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xincong yao
 * @date 2020-11-2
 */
@Component
@Slf4j
public class RedisCache implements ICache {

	@Value(value = "${redis.lock-prefix}")
	private String lockPrefix;

	@Value(value = "${redis.lock-expire-time}")
	private Long lockExpireTime;

	@Value(value = "${redis.lock-wait-time}")
	private Long lockWaitTime;

	@Value(value = "${redis.lock-attempt-times}")
	private Long lockAttemptTimes;

	private static DefaultRedisScript<Long> RELEASE_LOCK_SCRIPT = new DefaultRedisScript<>();
	private static DefaultRedisScript<Long> MODIFY_INVENTORY = new DefaultRedisScript<>();
	private static DefaultRedisScript<Long> FETCH_INVENTORY = new DefaultRedisScript<>();
	static {
		RELEASE_LOCK_SCRIPT.setResultType(Long.class);
		RELEASE_LOCK_SCRIPT.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
		MODIFY_INVENTORY.setResultType(Long.class);
		MODIFY_INVENTORY.setScriptText(
				"local t = redis.call('get', KEYS[1]) " +
						"if t == false " +
						"then return " + MODIFY_ERROR_CODE + " " +
						"else " +
						"local new = t + ARGV[1] " +
						"if new >= 0 " +
						"then redis.call('set', KEYS[1], new) return new " +
						"else return " + MODIFY_ERROR_CODE + " end end");
		FETCH_INVENTORY.setResultType(Long.class);
		FETCH_INVENTORY.setScriptText(
				"local t = redis.call('get', KEYS[1]) " +
						"if t == false " +
						"then return " + FETCH_ERROR_CODE + " " +
						"else redis.call('del', KEYS[1]) " +
						"local r = t + 0 " +
						"return r end");
	}

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public void set(Object h, Object hk, Object o) {
		if (h != null && hk != null && o != null) {
			redisTemplate.opsForValue().set(getKey(h, hk), o);
		}
	}

	@Override
	public Object get(Object h, Object hk) {
		if (h == null || hk == null) {
			return null;
		}
		return redisTemplate.opsForValue().get(getKey(h, hk));
	}

	@Override
	public Long modify(Object h, Object hk, Long v) {
		if (h == null || hk == null || v == null) {
			return -1L;
		}
		return (Long) redisTemplate.execute(
				MODIFY_INVENTORY,
				Collections.singletonList(getKey(h, hk)), v);
	}

	@Override
	public Long fetch(Object h, Object hk) {
		if (h == null || hk == null) {
			return FETCH_ERROR_CODE;
		}
		return (Long) redisTemplate.execute(FETCH_INVENTORY,
				Collections.singletonList(getKey(h, hk)));
	}

	@Override
	public Long delete(Object h, Object... hk) {
		if (h != null) {
			List<Object> list = new ArrayList<>();
			for (int i = 0; i < hk.length; i++) {
				if (hk[i] != null) {
					list.add(getKey(h, hk[i]));
				}
			}
			return redisTemplate.delete(list);
		}
		return 0L;
	}

	public String lock(Object id) {
		if (id == null) {
			return null;
		}
		String key = getKey(lockPrefix, id);
		String token = getUUID();
		boolean success;
		int count = 0;
		do {
			success = redisTemplate.opsForValue().setIfAbsent(key,
					token,
					lockExpireTime, TimeUnit.MILLISECONDS);
			if (count > lockAttemptTimes) {
				break;
			}
			if (count++ > 0) {
				try {
					Thread.sleep(lockWaitTime);
				} catch (InterruptedException e) {
					log.error("Unknown error while get redis lock");
				}
			}
		} while (!success);

		return success ? token : null;

	}

	public Boolean unlock(Object id, String token) {
		if (id == null || token == null) {
			return false;
		}
		String key = getKey(lockPrefix, id);
		return ((Long) redisTemplate.execute(
				RELEASE_LOCK_SCRIPT,
				Collections.singletonList(key),
				token)) != 0;
	}

	public static String getKey(Object... os) {
		StringBuilder sb = new StringBuilder();
		for (Object o : os) {
			sb.append(o).append(":");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getUUID() {
		return UUID.randomUUID().toString();
	}
}

package cn.edu.xmu.oomall.cache.impl;

import cn.edu.xmu.oomall.OomallSeckillApplicationTest;
import cn.edu.xmu.oomall.entity.FlashSalePo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xincong yao
 * @date 2020-11-2
 */
@SpringBootTest(classes = OomallSeckillApplicationTest.class)
@RunWith(SpringRunner.class)
public class RedisCacheTest {

	@Autowired
	private RedisCache redisCache;

	@Test
	public void setAndGetAndDeleteTest() {
		redisCache.set(1, 2, 3);
		Object o = redisCache.get(1, 2);
		Assert.assertEquals(o, 3);
		redisCache.delete(1, 2);
		o = redisCache.get(1, 2);
		Assert.assertNull(o);

		FlashSalePo po = new FlashSalePo();
		po.setId(111L);
		redisCache.set(po, po, po);
		Object o1 = redisCache.get(po, po);
		Assert.assertEquals(o1, po);
		redisCache.delete(po, po);
		o1 = redisCache.get(po, po);
		Assert.assertNull(o1);
	}

	@Test
	public void modifyTest() {
		redisCache.set(1, 2, 100L);
		Long a = redisCache.modify(1, 2, 101L);
		Long b = redisCache.modify(1, 2, -1000L);
		Long c = redisCache.modify(1123, 2123, 1000L);
		Assert.assertEquals((long) a, (long) 201L);
		Assert.assertEquals((long) b, -1L);
		Assert.assertEquals((long) c, -1L);

		redisCache.delete(1, 2);
	}

	@Test
	public void fetchTest() {
		redisCache.set(1, 2, 100L);
		Long a = redisCache.fetch(1, 2);
		Object o = redisCache.get(1, 2);
		Assert.assertEquals((long) a, 100L);
		Assert.assertNull(o);
	}

	@Test
	public void lockAndUnlockTest() {
		String token = redisCache.lock("seckill-item-id");
		String token1 = redisCache.lock("seckill-item-id");
		Boolean r1 = redisCache.unlock("seckill-item-id", "random");
		Boolean r2 = redisCache.unlock("seckill-item-id", token);
		Assert.assertNull(token1);
		Assert.assertFalse(r1);
		Assert.assertTrue(r2);
	}
}

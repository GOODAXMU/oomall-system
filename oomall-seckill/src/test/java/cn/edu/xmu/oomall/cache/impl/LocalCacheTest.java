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
public class LocalCacheTest {

	@Autowired
	private LocalCache localCache;

	@Test
	public void setAndGetAndDeleteTest() {
		localCache.set(1, 2, 3);
		Object o = localCache.get(1, 2);
		Assert.assertEquals(o, 3);
		localCache.delete(1, 2);
		o = localCache.get(1, 2);
		Assert.assertNull(o);

		FlashSalePo po = new FlashSalePo();
		po.setId(111L);
		localCache.set(po, po, po);
		Object o1 = localCache.get(po, po);
		Assert.assertEquals(o1, po);
		localCache.delete(po, po);
		o1 = localCache.get(po, po);
		Assert.assertNull(o1);
	}

	@Test
	public void modifyTest() {
		localCache.set(1, 2, 100L);
		Long a = localCache.modify(1, 2, 101L);
		Long b = localCache.modify(1, 2, -1000L);
		Assert.assertEquals((long) a, 201L);
		Assert.assertEquals((long) b, -1L);

		localCache.delete(1, 2);
	}

	@Test
	public void fetchTest() {
		localCache.set(1, 2, 100L);
		Long a = localCache.fetch(1, 2);
		Object o = localCache.get(1, 2);
		Assert.assertEquals((long) a, 100L);
		Assert.assertNull(o);
	}
}


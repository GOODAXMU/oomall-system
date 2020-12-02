package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.OomallSeckillApplicationTest;
import cn.edu.xmu.oomall.cache.ICache;
import cn.edu.xmu.oomall.constant.SeckillResponseStatus;
import cn.edu.xmu.oomall.dto.SeckillResult;
import cn.edu.xmu.oomall.repository.SeckillItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
@SpringBootTest(classes = OomallSeckillApplicationTest.class)
@RunWith(SpringRunner.class)
public class SeckillDaoTest {

	@Autowired
	private SeckillDao seckillDao;

	@Resource(name = "redisCache")
	private ICache seckillActivityCache;

	@Autowired
	private SeckillItemRepository seckillItemRepository;

	@Resource(name = "redisCache")
	private ICache seckillItemCache;

	@Value(value = "${cache.seckill-activity-prefix}")
	private String seckillActivityPrefix;

	@Value(value = "${cache.seckill-item-prefix}")
	private String seckillItemPrefix;

	@Value(value = "${seckill.load-factor}")
	private double loadFactor;

	@Test
	public void loadSeckillTest() {
		seckillDao.loadSeckill(12L, 4);
	}

	@Test
	public void unloadSeckillTest() {
		seckillDao.loadSeckill(12L, 4);
		int a = seckillItemRepository.findQuantityById(1L);
		seckillDao.unloadSeckill(12L);
		int b = seckillItemRepository.findQuantityById(1L);
		Assert.assertEquals(a + 4, b);
	}

	@Test
	public void modifyInventorySingleThreadTest() {
		long seckillId = 12L;
		long skuId = 1L;
		int batchSize = 10;
		seckillDao.loadSeckill(seckillId, batchSize);
		seckillDao.modifySeckillStatus(seckillId, true);

		int rest = batchSize;
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int quantity = -random.nextInt(20);
			Map<String, Object> r0 = seckillDao.modifyInventory(seckillId, skuId, quantity);
			int n0 = (int) seckillItemCache.get(seckillItemPrefix, skuId);
			Assert.assertEquals(SeckillResult.getStatus(r0), SeckillResponseStatus.OK);
			int times = 1;
			while (rest < Math.abs(quantity)) {
				rest += (1 + times * loadFactor) * batchSize;
				times++;
			}
			rest += quantity;
			Assert.assertEquals(rest, n0);
		}

		seckillDao.unloadSeckill(seckillId);
	}

	@Test
	public void modifyInventoryMultiThreadTest() throws InterruptedException {
		long seckillId = 12L;
		long skuId = 1L;
		int batchSize = 30;

		int origin = seckillItemRepository.findQuantityById(skuId);

		// 开启秒杀活动
		seckillDao.loadSeckill(seckillId, batchSize);
		seckillDao.modifySeckillStatus(seckillId, true);

		AtomicInteger total = new AtomicInteger();

		Thread.sleep(1000);

		for (int j = 0; j < 100; j++) {
			Thread thread = new Thread(() -> {
				int sum = 0;
				Random random = new Random();
				for (int i = 0; i < 10; i++) {
					int quantity = -random.nextInt(20);
					Map<String, Object> r0 = seckillDao.modifyInventory(seckillId, skuId, quantity);
					if (SeckillResult.getStatus(r0).equals(SeckillResponseStatus.OK)) {
						sum -= quantity;
					}
				}
				total.addAndGet(sum);
			});
			thread.start();
		}

		Thread.sleep(15000);

		seckillDao.unloadSeckill(seckillId);

		int actual = seckillItemRepository.findQuantityById(skuId);

		Assert.assertEquals(origin - total.get(), actual);

	}
}

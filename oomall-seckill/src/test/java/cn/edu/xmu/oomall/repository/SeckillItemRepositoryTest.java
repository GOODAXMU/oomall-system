package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.OomallSeckillApplicationTest;
import cn.edu.xmu.oomall.entity.FlashSaleItemPo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
@SpringBootTest(classes = OomallSeckillApplicationTest.class)
@RunWith(SpringRunner.class)
public class SeckillItemRepositoryTest {

	@Autowired
	private SeckillItemRepository seckillItemRepository;

	@Test
	public void findItemsBySaleIdTest() {
		Page<FlashSaleItemPo> result =
				seckillItemRepository.findItemsBySaleId(12L, PageRequest.of(0, 1));
	}

	@Test
	public void findIdListBySaleIdTest() {
		Page<Long> result =
				seckillItemRepository.findIdListBySaleId(12L, PageRequest.of(0, 1));
	}

	@Test
	public void fetchQuantityTest() {
		int number = 1;
		Optional<FlashSaleItemPo> o1 = seckillItemRepository.findById(2L);
		int a = seckillItemRepository.fetchQuantity(2L, number);
		Optional<FlashSaleItemPo> o2 = seckillItemRepository.findById(2L);
		Assert.assertEquals(a, 1L);
		Assert.assertEquals(o1.get().getQuantity() - number, (long) o2.get().getQuantity());
	}

	@Test
	public void findQuantityById() {
		int a = seckillItemRepository.findQuantityById(1L);
		Integer b = seckillItemRepository.findQuantityById(1222L);
		System.out.println(a + " " + b);
	}
}

package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.cache.ICache;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dto.Result;
import cn.edu.xmu.oomall.entity.FlashSaleItemPo;
import cn.edu.xmu.oomall.entity.FlashSalePo;
import cn.edu.xmu.oomall.repository.SeckillItemRepository;
import cn.edu.xmu.oomall.repository.SeckillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
@Component
public class SeckillDao {

	@Resource(name = "redisCache")
	private ICache seckillActivityCache;

	@Resource(name = "redisCache")
	private ICache seckillItemCache;

	private Map<Long, Boolean> seckillSwitch = new ConcurrentHashMap<>();

	@Autowired
	private SeckillRepository seckillRepository;

	@Autowired
	private SeckillItemRepository seckillItemRepository;

	@Value(value = "${cache.seckill-activity-prefix}")
	private String seckillActivityPrefix;

	@Value(value = "${cache.seckill-item-prefix}")
	private String seckillItemPrefix;

	@Value(value = "${seckill.load-factor}")
	private double loadFactor;

	@Value(value = "${seckill.attempt-times}")
	private int attemptTimes;

	private final static int PAGE_SIZE = 100;

	private final static int BATCH_SIZE = 100;

	/**
	 * 加载秒杀活动
	 * @param seckillId 不允许为空, 调用者需检查
	 * @param batchSize 允许为空, 允许为任意值
	 * @return
	 */
	public Map<String, Object> loadSeckill(Long seckillId, Integer batchSize) {
		// 在数据库中查找该秒杀活动
		Optional<FlashSalePo> o = seckillRepository.findById(seckillId);
		if (o.isEmpty()) {
			return Result.getResult(ResponseStatus.SECKILL_NOT_FOUND);
		}

		if (batchSize == null || batchSize <= 0) {
			batchSize = BATCH_SIZE;
		}

		// 秒杀活动写入缓存
		seckillActivityCache.set(seckillActivityPrefix, seckillId, batchSize);
		// 设置秒杀活动状态
		seckillSwitch.put(seckillId, false);

		// 获取所有的秒杀项并写入缓存
		int pageIndex = 0;
		int total = 0;
		int load = 0;
		Page<FlashSaleItemPo> page;
		Pageable pageable;
		do {
			pageable = PageRequest.of(pageIndex, PAGE_SIZE);
			pageIndex++;
			page = seckillItemRepository.
					findItemsBySaleId(seckillId, pageable);
			total += page.getContent().size();
			for (FlashSaleItemPo e : page.getContent()) {
				int number = Math.min(e.getQuantity(), batchSize);
				int r = seckillItemRepository
						.fetchQuantity(e.getId(), number);
				if (r > 0) {
					load++;
					seckillItemCache.set(seckillItemPrefix, e.getId(), number);
				}
			}
		} while (page.getContent().size() != 0);

		Result.Load result = new Result.Load();
		result.setTotalCount(total);
		result.setSuccessCount(load);
		return Result.getResult(ResponseStatus.OK, result);
	}

	/**
	 * 卸载秒杀活动
	 * @param seckillId 不允许为空, 调用者需检查
	 * @return
	 */
	public Map<String, Object> unloadSeckill(Long seckillId) {
		// 在数据库中查找该秒杀活动
		Optional<FlashSalePo> o = seckillRepository.findById(seckillId);
		if (o.isEmpty()) {
			return Result.getResult(ResponseStatus.SECKILL_NOT_FOUND);
		}

		// 移除秒杀活动缓存
		seckillActivityCache.delete(seckillActivityPrefix, seckillId);
		// 移除秒杀活动状态
		seckillSwitch.remove(seckillId);

		// 获取所有的秒杀项并移出缓存
		int pageIndex = 0;
		int total = 0;
		Page<Long> page;
		Pageable pageable;
		do {
			pageable = PageRequest.of(pageIndex, PAGE_SIZE);
			pageIndex++;
			page = seckillItemRepository.
					findIdListBySaleId(seckillId, pageable);
			total += page.getContent().size();
			for (Long e : page.getContent()) {
				int number = Math.toIntExact(seckillItemCache.fetch(seckillItemPrefix, e));
				seckillItemRepository.fetchQuantity(e, -number);
			}
		} while (page.getContent().size() != 0);

		Result.Unload result = new Result.Unload();
		result.setTotalCount(total);
		return Result.getResult(ResponseStatus.OK, result);
	}

	/**
	 * 修改秒杀活动状态
	 * @param seckillId 秒杀活动id
	 * @param switchOn  开启或关闭
	 * @return
	 */
	public Map<String, Object> modifySeckillStatus(Long seckillId, Boolean switchOn) {
		Boolean r = seckillSwitch.computeIfPresent(seckillId, (k, v) -> switchOn);
		if (r == null) {
			return Result.getResult(ResponseStatus.SECKILL_NOT_FOUND);
		}
		return Result.getResult(ResponseStatus.OK);
	}

	/**
	 * 修改商品sku库存
	 * @param seckillId
	 * @param skuId
	 * @param quantity
	 * @return
	 */
	public Map<String, Object> modifyInventory(Long seckillId, Long skuId, Integer quantity) {
		Boolean status = seckillSwitch.get(seckillId);
		if (status == null) {
			return Result.getResult(ResponseStatus.SECKILL_NOT_FOUND);
		}
		if (!status) {
			return Result.getResult(ResponseStatus.SECKILL_CLOSED);
		}

		int r = -1;
		int times = 0;
		while (r < 0) {
			// 尝试从缓存中扣库存
			r = Math.toIntExact(seckillItemCache
					.modify(seckillItemPrefix, skuId, Long.valueOf(quantity))
			);

			times++;
			if (times >= attemptTimes) {
				break;
			}

			// 缓存中库存不足
			if (r < 0) {
				// 获取默认的库存单次加载量
				int bs = (int) seckillActivityCache.get(seckillActivityPrefix, seckillId);

				// 失败次数过多需要加大加载量
				bs *=  (1 + times * loadFactor);

				// 获取剩余的库存量
				Integer num = seckillItemRepository.findQuantityById(skuId);

				/**
				 * 决定最终该从数据库中获取多少库存
				 * 数据库余量为0时不应该直接退出
				 * 在batchSize大的情况下, 且大批请求在缓存中库存不足时同时到来,
				 * 其他线程会加载大量库存到缓存中导致数据库库存不足
 				 */
				if (num != null && num > 0) {
					bs = Math.min(bs, num);

					// 从数据库中获取库存
					int t = seckillItemRepository.fetchQuantity(skuId, bs);
					if (t > 0) {
						// 在缓存中添加库存
						modifyInventory(seckillId, skuId, bs);
					}
				}
			}
		}

		return r < 0
				? Result.getResult(ResponseStatus.OUT_OF_INVENTORY)
				: Result.getResult(ResponseStatus.OK);
	}
}

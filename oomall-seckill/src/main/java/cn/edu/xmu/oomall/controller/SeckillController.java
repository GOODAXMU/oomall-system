package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.cache.ICache;
import cn.edu.xmu.oomall.dto.SeckillLoadRequest;
import cn.edu.xmu.oomall.dto.SeckillStatusModifyRequest;
import cn.edu.xmu.oomall.dto.SeckillUnloadRequest;
import cn.edu.xmu.oomall.repository.SeckillItemRepository;
import cn.edu.xmu.oomall.repository.SeckillRepository;
import cn.edu.xmu.oomall.service.impl.SeckillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-12-2
 */
@RestController
public class SeckillController {

	@Autowired
	private SeckillServiceImpl seckillService;

	@Resource(name = "redisCache")
	private ICache seckillActivityCache;

	@Resource(name = "redisCache")
	private ICache seckillItemCache;

	@Autowired
	private SeckillRepository seckillRepository;

	@Autowired
	private SeckillItemRepository seckillItemRepository;

	@Value(value = "${cache.seckill-activity-prefix}")
	private String seckillActivityPrefix;

	@Value(value = "${cache.seckill-item-prefix}")
	private String seckillItemPrefix;


	@GetMapping("/load")
	public Map<String, Object> loadAndStart(Long seckillId, Integer batchSize) {
		SeckillLoadRequest request = new SeckillLoadRequest();
		request.setSeckillId(seckillId);
		request.setBatchSize(batchSize);
		Map<String, Object> r = seckillService.loadSeckill(request);
		SeckillStatusModifyRequest request1 = new SeckillStatusModifyRequest();
		request1.setSeckillId(seckillId);
		request1.setSwitchOn(true);
		seckillService.modifySeckillStatus(request1);

		return r;
	}


	@GetMapping("/unload")
	public Map<String, Object> unloadAndClose(Long seckillId) {
		SeckillStatusModifyRequest request1 = new SeckillStatusModifyRequest();
		request1.setSeckillId(seckillId);
		request1.setSwitchOn(false);
		seckillService.modifySeckillStatus(request1);
		SeckillUnloadRequest request = new SeckillUnloadRequest();
		request.setSeckillId(seckillId);
		return seckillService.unloadSeckill(request);
	}

	@GetMapping("/checkAct")
	public Object checkAct(Long seckillId) {
		return seckillActivityCache.get(seckillActivityPrefix, seckillId);
	}

	@GetMapping("/checkItem")
	public Object checkItem(Long skuId) {
		return seckillItemCache.get(seckillItemPrefix, skuId);
	}
}

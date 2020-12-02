package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IFreightService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedFreightServiceImpl implements IFreightService {

	@Override
	public Long calcAndGetFreightPrice(List<OrderItem> orderItems, Long regionId, boolean isSeckill) {
		return 100L;
	}

	@Override
	public CompletableFuture<Long> calcFreightPriceAsynchronous(List<OrderItem> orderItems, Long regionId, boolean isSeckill) {
		CompletableFuture<Long> cf = new CompletableFuture<>();
		cf.complete(2L);
		return cf;
	}
}

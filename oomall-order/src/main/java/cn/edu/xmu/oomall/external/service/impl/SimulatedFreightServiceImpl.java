package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IFreightService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedFreightServiceImpl implements IFreightService {

	@Override
	public Long calcAndGetFreightPrice(List<OrderItem> orderItems) {
		return 100L;
	}

	@Override
	public CompletableFuture<Long> calcFreightPriceAsynchronous(List<OrderItem> orderItems) {
		return new CompletableFuture<>();
	}

	@Override
	public Long getFreightPrice(CompletableFuture<Long> cf) throws ExecutionException, InterruptedException {
		// return cf.get();
		return 100L;
	}
}

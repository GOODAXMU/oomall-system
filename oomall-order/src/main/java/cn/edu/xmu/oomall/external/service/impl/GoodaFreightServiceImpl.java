package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.dto.CalculateFreightRequest;
import cn.edu.xmu.oomall.external.service.IFreightService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author xincong yao
 * @date 2020-12-2
 */
@Service
public class GoodaFreightServiceImpl implements IFreightService {

	@DubboReference(version = "${oomall.freight.version}", async = true, timeout = 5000)
	private cn.edu.xmu.oomall.service.IFreightService freightService;

	@Override
	public Long calcAndGetFreightPrice(List<OrderItem> orderItems, Long regionId, boolean isSeckill) {
		if (isSeckill) {
			OrderItem oi = orderItems.get(0);
			CalculateFreightRequest request = new CalculateFreightRequest();
			request.setSkuId(oi.getSkuId());
			request.setCount(oi.getQuantity());
			return freightService.calActivityFreight(request, regionId);
		}

		List<CalculateFreightRequest> requests = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			CalculateFreightRequest request = new CalculateFreightRequest();
			request.setSkuId(oi.getSkuId());
			request.setCount(oi.getQuantity());

			requests.add(request);
		}
		return freightService.calFreight(requests, regionId);
	}

	@Override
	public CompletableFuture<Long> calcFreightPriceAsynchronous(List<OrderItem> orderItems, Long regionId, boolean isSeckill) {
		if (isSeckill) {
			OrderItem oi = orderItems.get(0);
			CalculateFreightRequest request = new CalculateFreightRequest();
			request.setSkuId(oi.getSkuId());
			request.setCount(oi.getQuantity());
			return RpcContext.getContext().asyncCall(
					() -> freightService.calActivityFreight(request, regionId)
			);
		}

		List<CalculateFreightRequest> requests = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			CalculateFreightRequest request = new CalculateFreightRequest();
			request.setSkuId(oi.getSkuId());
			request.setCount(oi.getQuantity());

			requests.add(request);
		}
		return RpcContext.getContext().asyncCall(
				() -> freightService.calFreight(requests, regionId)
		);
	}
}

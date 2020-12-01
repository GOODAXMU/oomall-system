package cn.edu.xmu.oomall.service.dubbo;

import cn.edu.xmu.ooamll.dto.AfterSaleDto;
import cn.edu.xmu.ooamll.service.IDubboOrderService;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import cn.edu.xmu.oomall.repository.OrderRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-30
 */
@DubboService(version = "${oomall.order.version}")
public class DubboOrderServiceImpl implements IDubboOrderService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public AfterSaleDto getAfterSaleByOrderItemId(Long orderItemId) {
		Optional<OrderItemPo> o = orderItemRepository.findById(orderItemId);
		if (o.isEmpty()) {
			return null;
		}
		OrderItemPo orderItemPo = o.get();

		OrderPo orderPo = orderRepository.findOrderSnAndShopIdById(orderItemPo.getOrderId());
		if (orderPo == null) {
			return null;
		}

		AfterSaleDto dto = new AfterSaleDto();
		dto.setOrderId(orderItemPo.getOrderId());
		dto.setOrderSn(orderPo.getOrderSn());
		dto.setShopId(orderPo.getShopId());
		dto.setSkuId(orderItemPo.getGoodsSkuId());
		dto.setSkuName(orderItemPo.getName());

		return dto;
	}
}

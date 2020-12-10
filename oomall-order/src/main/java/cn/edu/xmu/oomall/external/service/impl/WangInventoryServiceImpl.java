package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.external.service.IInventoryService;
import cn.edu.xmu.goods.client.dubbo.OrderItemDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Component
public class WangInventoryServiceImpl implements IInventoryService {

	// todo 外部服务未配置
	// @DubboReference(version = "${oomall.external.inventory-service.version}", cache = "false", async = true, timeout = 5000)
	private cn.edu.xmu.goods.client.IInventoryService inventoryService;


	@Override
	public List<OrderItem> modifyInventory(List<OrderItem> orderItems, Integer type) {
		List<OrderItemDTO> dto = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			dto.add(toOrderItemDTO(oi));
		}
		dto = inventoryService.modifyInventory(dto);

		orderItems = new ArrayList<>();
		for (OrderItemDTO oiDTO : dto) {
			OrderItem oi = toOrderItem(oiDTO);
			orderItems.add(oi);
		}

		return orderItems;
	}

	private OrderItem toOrderItem(OrderItemDTO dto) {
		OrderItem oi = new OrderItem();
		oi.setSkuId(dto.getSkuId());
		oi.setQuantity(dto.getQuantity());
		oi.setCouponActivityId(dto.getCouponActivityId());
		return oi;
	}

	private OrderItemDTO toOrderItemDTO(OrderItem oi) {
		OrderItemDTO d = new OrderItemDTO();
		d.setId(oi.getId());
		d.setName(oi.getName());
		d.setOrderId(oi.getOrderId());
		d.setPrice(oi.getPrice());
		d.setQuantity(oi.getQuantity());
		d.setDiscount(oi.getDiscount());
		d.setSkuId(oi.getSkuId());
		d.setBeShareId(oi.getBeShareId());
		d.setCouponActivityId(oi.getCouponActivityId());

		return d;
	}
}

package cn.edu.xmu.oomall.service.dubbo;

import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.dto.AfterSaleDto;
import cn.edu.xmu.oomall.dto.EffectiveShareDto;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.external.service.IActivityService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.service.IDubboOrderService;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import cn.edu.xmu.oomall.repository.OrderRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

	private IActivityService activityService;

	@Autowired
	private ServiceFactory serviceFactory;

	@PostConstruct
	public void init() {
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
	}

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

	@Override
	public Boolean isShopOwnOrder(Long shopId, Long orderId) {
		return orderRepository.findIdByIdAndShopId(orderId, shopId) != null;
	}

	@Override
	public Boolean isCustomerOwnOrder(Long customerId, Long orderId) {
		return orderRepository.findIdByIdAndCustomerId(orderId, customerId) != null;
	}

	@Override
	public Boolean isCustomerOwnOrderItem(Long customerId, Long orderItemId) {
		Long orderId = orderItemRepository.findOrderIdById(orderItemId);
		if (orderId == null) {
			return false;
		}

		Long cId = orderRepository.findCustomerIdById(orderId);

		return cId != null && cId.equals(customerId);
	}

	@Override
	public OrderItemDto getOrderItem(Long orderItemId) {
		Optional<OrderItemPo> orderItem = orderItemRepository.findById(orderItemId);
		if (orderItem.isEmpty()) {
			return null;
		}
		OrderItemPo po = orderItem.get();
		OrderItemDto dto = new OrderItemDto();
		dto.setId(po.getId());
		dto.setSkuId(po.getGoodsSkuId());
		dto.setOrderId(po.getOrderId());
		dto.setName(po.getName());
		dto.setQuantity(po.getQuantity());
		dto.setPrice(po.getPrice());
		dto.setDiscount(po.getDiscount());
		dto.setCouponActivityId(po.getCouponActivityId());
		dto.setBeShareId(po.getBeShareId());
		return dto;
	}

	@Override
	public Boolean orderCanBePaid(Long id) {
		Integer r = orderRepository.findOrderStatusById(id);
		int status = r == null ? -1 : r;
		return OrderStatus.TO_BE_PAID.value() == status;
	}

	@Override
	public void checkOrderPaid(Long id, Long amount) {
		// 获取可支付的订单
		OrderPo po = null;
		if (orderCanBePaid(id)) {
			Optional<OrderPo> o = orderRepository.findById(id);
			if (o.isPresent()) {
				po = o.get();
			}
		}
		if (po == null) {
			return;
		}

		if (OrderType.PRESALE.value() == po.getOrderType()) {
			if (OrderStatus.DEPOSIT_TO_BE_PAID.value() == po.getSubState()) {
				Long price = activityService.getPreSaleDeposit(po.getPresaleId());
				if (price.equals(amount)) {
					orderRepository.changeOrderSubStateWhenSubStateEquals(
							id,
							OrderStatus.DEPOSIT_PAID.value(),
							OrderStatus.DEPOSIT_TO_BE_PAID.value()
					);
				}
			} else if (OrderStatus.BALANCE_TO_BE_PAID.value() == po.getSubState()) {
				Long price = activityService.getPreSaleBalance(po.getPresaleId());
				if (price.equals(amount)) {
					orderRepository.changeOrderStateWhenStateEquals(
							id,
							OrderStatus.PAID.value(),
							OrderStatus.TO_BE_PAID.value()
					);
					orderRepository.changeOrderSubStateWhenSubStateEquals(
							id,
							OrderStatus.BALANCE_PAID.value(),
							OrderStatus.BALANCE_TO_BE_PAID.value()
					);
				}
			}
		} else if (OrderType.GROUPON.value() == po.getOrderType()) {
			Long price = Long.MAX_VALUE;
			if (po.getOriginPrice() != null) {
				long discount = po.getDiscountPrice() == null ? 0L : po.getDiscountPrice();
				price = po.getOriginPrice() - discount;
			}

			if (price.equals(amount)) {
				orderRepository.changeOrderStateWhenStateEquals(
						id,
						OrderStatus.PAID.value(),
						OrderStatus.TO_BE_PAID.value()
				);
				orderRepository.changeOrderSubStateWhenSubStateEquals(
						id,
						OrderStatus.GROUPON_TO_BE_JOIN.value(),
						OrderStatus.GROUPON_JOIN.value()
				);
			}
		} else {
			Long price = Long.MAX_VALUE;
			if (po.getOriginPrice() != null) {
				long discount = po.getDiscountPrice() == null ? 0L : po.getDiscountPrice();
				price = po.getOriginPrice() - discount;
			}

			if (price.equals(amount)) {
				orderRepository.changeOrderStateWhenStateEquals(
						id,
						OrderStatus.PAID.value(),
						OrderStatus.TO_BE_PAID.value()
				);
			}
		}
	}

	@Override
	public Long getOrderCanBeRefundPrice(Long id) {
		OrderPo po = orderRepository.findPrice(id);
		if (po == null || po.getOriginPrice() == null) {
			return 0L;
		}
		long price = po.getOriginPrice() == null ? 0L : po.getOriginPrice();
		long discount = po.getDiscountPrice() == null ? 0L : po.getDiscountPrice();
		return price - discount;
	}

	@Override
	public List<EffectiveShareDto> getEffectiveShareRecord() {
		List<EffectiveShareDto> dtos = new ArrayList<>();

		List<OrderPo> orders = orderRepository.findAllWhereStatusEqualsAndGmtModifiedBetween(
				OrderStatus.RECEIVED.value(),
				LocalDateTime.now().minusDays(8),
				LocalDateTime.now().minusDays(7)
		);

		for (OrderPo po : orders) {
			List<OrderItemPo> orderItems = orderItemRepository.findByOrderId(po.getId());
			for (OrderItemPo oi : orderItems) {
				if (oi.getBeShareId() != null) {
					EffectiveShareDto dto = new EffectiveShareDto();
					dto.setBeSharedId(oi.getBeShareId());
					dto.setQuantity(oi.getQuantity());
					long price = oi.getPrice() == null ? 0L : oi.getPrice();
					long discount = oi.getDiscount() == null ? 0L : oi.getDiscount();
					dto.setPrice(price - discount);
					dtos.add(dto);
				}
			}
		}

		return dtos;
	}
}

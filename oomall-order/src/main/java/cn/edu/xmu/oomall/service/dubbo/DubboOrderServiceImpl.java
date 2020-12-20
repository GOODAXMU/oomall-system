package cn.edu.xmu.oomall.service.dubbo;

import cn.edu.xmu.oomall.bo.OrderItem;
import cn.edu.xmu.oomall.bo.Shop;
import cn.edu.xmu.oomall.constant.DbOrderStatus;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dto.*;
import cn.edu.xmu.oomall.external.service.IActivityService;
import cn.edu.xmu.oomall.external.service.IShopService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.service.IDubboOrderService;
import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.repository.OrderItemRepository;
import cn.edu.xmu.oomall.repository.OrderRepository;
import cn.edu.xmu.oomall.util.OrderSnGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xincong yao
 * @date 2020-11-30
 */
@Slf4j
@DubboService(version = "${oomall.order.version}")
public class DubboOrderServiceImpl implements IDubboOrderService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;

	private IActivityService activityService;
	private IShopService shopService;

	@Autowired
	private ServiceFactory serviceFactory;

	@PostConstruct
	public void init() {
		activityService = (IActivityService) serviceFactory.get(IActivityService.class);
		shopService = (IShopService) serviceFactory.get(IShopService.class);
	}

	@Override
	public AfterSaleDto getAfterSaleByOrderItemId(Long orderItemId) {
		log.debug("getAfterSaleByOrderItemId: " + orderItemId);

		Optional<OrderItemPo> o = orderItemRepository.findById(orderItemId);
		if (o.isEmpty()) {
			return null;
		}
		OrderItemPo orderItemPo = o.get();

		Optional<OrderPo> op = orderRepository.findById(orderItemPo.getOrderId());
		if (op.isEmpty()) {
			return null;
		}

		OrderPo orderPo = op.get();

		if (orderPo.getState() == OrderStatus.COMPLETED.value()
				|| (orderPo.getBeDeleted() != null && orderPo.getBeDeleted() == DbOrderStatus.BE_DELETED.value())) {
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
		log.debug("isShopOwnOrder: " + shopId + ", " + orderId);

		Optional<OrderPo> op = orderRepository.findById(orderId);
		if (op.isEmpty()) {
			return null;
		}

		OrderPo po = op.get();

		return po.getShopId().equals(shopId);
	}

	@Override
	public Boolean isCustomerOwnOrder(Long customerId, Long orderId) {
		log.debug("isCustomerOwnOrder: " + customerId + ", " + orderId);

		Optional<OrderPo> op = orderRepository.findById(orderId);
		if (op.isEmpty()) {
			return null;
		}

		OrderPo po = op.get();

		return po.getCustomerId().equals(customerId);
	}

	@Override
	public Boolean isCustomerOwnOrderItem(Long customerId, Long orderItemId) {
		log.debug("isCustomerOwnOrderItem: " + customerId + ", " + orderItemId);

		Long orderId = orderItemRepository.findOrderIdById(orderItemId);
		if (orderId == null) {
			return null;
		}

		Optional<OrderPo> op = orderRepository.findById(orderId);
		if (op.isEmpty()) {
			return null;
		}

		OrderPo po = op.get();

		return po.getCustomerId().equals(customerId);
	}

	@Override
	public OrderItemDto getOrderItem(Long orderItemId) {
		log.debug("getOrderItem: " + orderItemId);
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
		log.debug("orderCanBePaid: " + id);
		Integer r = orderRepository.findOrderStatusById(id);
		int status = r == null ? -1 : r;
		return OrderStatus.TO_BE_PAID.value() == status;
	}

	@Override
	public void checkOrderPaid(Long id, Long amount) {
		log.debug("checkOrderPaid: " + id + ", " + amount);
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
			if (OrderStatus.NEW.value() == po.getSubState()) {
				Long price = activityService.getPreSaleDeposit(po.getPresaleId());
				if (price.equals(amount)) {
					orderRepository.changeOrderSubStateWhenSubStateEquals(
							id,
							OrderStatus.BALANCE_TO_BE_PAID.value(),
							OrderStatus.NEW.value()
					);
				}
			} else if (OrderStatus.BALANCE_TO_BE_PAID.value() == po.getSubState()) {
				Long price = activityService.getPreSaleBalance(po.getPresaleId());
				if (price.equals(amount)) {
					orderRepository.changeOrderStateWhenStateEquals(
							id,
							OrderStatus.TO_BE_RECEIVED.value(),
							OrderStatus.PAID.value(),
							OrderStatus.TO_BE_PAID.value(),
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
						OrderStatus.TO_BE_RECEIVED.value(),
						OrderStatus.GROUPON_THRESHOLD_TO_BE_REACH.value(),
						OrderStatus.TO_BE_PAID.value(),
						OrderStatus.NEW.value()
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
						OrderStatus.TO_BE_RECEIVED.value(),
						OrderStatus.PAID.value(),
						OrderStatus.TO_BE_PAID.value(),
						OrderStatus.NEW.value()
				);

				splitAndWriteOrder(po);
			}
		}
	}

	@Override
	public Long getOrderPresaleDeposit(Long id) {
		log.debug("getOrderPresaleDeposit: " + id);
		Long presaleId = orderRepository.findPreSaleIdById(id);
		if (presaleId == null) {
			return -1L;
		}
		Long deposit = activityService.getPreSaleDeposit(presaleId);
		return deposit == null ? -1L : deposit;
	}

	@Override
	public List<EffectiveShareDto> getEffectiveShareRecord() {
		log.debug("getEffectiveShareRecord, " + LocalDateTime.now());
		List<EffectiveShareDto> dtos = new ArrayList<>();

		List<OrderPo> orders = orderRepository.findAllWhereStatusEqualsAndGmtModifiedBetween(
				OrderStatus.COMPLETED.value(),
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

	private void splitAndWriteOrder(OrderPo parent) {
		// 获取订单下的订单项
		List<OrderItemPo> orderItemPos = orderItemRepository.findByOrderId(parent.getId());
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItemPo oip : orderItemPos) {
			orderItems.add(OrderItem.toOrderItem(oip));
		}

		long allPrice = 0L;
		for (OrderItem oi : orderItems) {
			allPrice += oi.getPrice() * oi.getQuantity();
		}

		Map<Shop, List<OrderItem>> shop2OrderItems = shopService.classifySku(orderItems);

		// 单店不拆单
		if (shop2OrderItems == null || shop2OrderItems.size() <= 1) {
			return;
		}

		for (Map.Entry<Shop, List<OrderItem>> e : shop2OrderItems.entrySet()) {
			long totalPrice = 0L;
			for (OrderItem oi : e.getValue()) {
				totalPrice += oi.getPrice() * oi.getQuantity();
			}

			OrderPo sub = new OrderPo();
			sub.setCustomerId(parent.getCustomerId());
			sub.setShopId(e.getKey().getId());
			sub.setOrderSn(OrderSnGenerator.createAndGetOrderSn());
			sub.setPid(parent.getId());
			sub.setConsignee(parent.getConsignee());
			sub.setRegionId(parent.getRegionId());
			sub.setAddress(parent.getAddress());
			sub.setMobile(parent.getMobile());
			sub.setMessage(parent.getMessage());
			sub.setOrderType(parent.getOrderType());
			sub.setFreightPrice(parent.getFreightPrice() * totalPrice / allPrice);
			sub.setCouponId(parent.getCouponId());
			sub.setCouponActivityId(parent.getCouponActivityId());
			sub.setDiscountPrice(parent.getDiscountPrice() * totalPrice / allPrice);
			sub.setOriginPrice(totalPrice);
			sub.setState(parent.getState());
			sub.setGmtCreate(LocalDateTime.now());

			sub = orderRepository.save(sub);

			for (OrderItem oi : e.getValue()) {
				orderItemRepository.changeOrderIdTo(oi.getId(), sub.getId());
			}
		}
	}

	@Override
	public Integer createExchangeOrder(ExchangeOrderDto dto) {
		log.debug("createExchangeOrder: " + dto);

		if (dto == null) {
			return ResponseStatus.INTERNAL_SERVER_ERR.value();
		}

		Optional<OrderItemPo> orderItemOp = orderItemRepository.findById(dto.getOrderItemId());
		OrderItemPo orderItemPo = orderItemOp.isEmpty() ? null : orderItemOp.get();

		if (orderItemPo == null) {
			return ResponseStatus.INTERNAL_SERVER_ERR.value();
		}

		Optional<OrderPo> op = orderRepository.findById(orderItemPo.getOrderId());
		if (op.isEmpty()) {
			return ResponseStatus.INTERNAL_SERVER_ERR.value();
		}

		OrderPo order = op.get();

		if (!order.getCustomerId().equals(dto.getCustomerId())
				|| !order.getShopId().equals(dto.getShopId())) {
			return ResponseStatus.INTERNAL_SERVER_ERR.value();
		}

		OrderPo exOrder = new OrderPo();
		exOrder.setCustomerId(dto.getCustomerId());
		exOrder.setShopId(dto.getShopId());
		exOrder.setMobile(dto.getMobile());
		exOrder.setRegionId(dto.getRegionId());
		exOrder.setAddress(dto.getAddress());
		exOrder.setConsignee(dto.getConsignee());

		exOrder = orderRepository.save(exOrder);
		log.debug("new orderId: " + exOrder.getId());

		OrderItemPo oi = new OrderItemPo();
		oi.setOrderId(exOrder.getId());
		oi.setGoodsSkuId(orderItemPo.getGoodsSkuId());
		oi.setQuantity(dto.getQuantity());
		oi.setName(orderItemPo.getName());
		oi.setPrice(orderItemPo.getPrice());

		oi = orderItemRepository.save(oi);
		log.debug("new orderItemId: " + oi.getId());

		return ResponseStatus.OK.value();
	}

	@Override
	public Boolean changeOrderState(OrderStateDto dto) {
		log.debug("changeOrderState: " + dto);
		int r = orderRepository.changeOrderStateWhenStateEquals(
				dto.getOrderId(),
				dto.getTo(), dto.getToSub(),
				dto.getFrom(), dto.getFromSub()
		);
		return r == 1;
	}

	@Override
	public Long priceOrderCanBePaid(Long customerId, Long orderId) {
		Optional<OrderPo> op = orderRepository.findById(orderId);
		if (op.isEmpty()) {
			return null;
		}

		OrderPo po = op.get();

		if (!po.getCustomerId().equals(customerId)) {
			return -1L;
		}

		if (po.getState() >= OrderStatus.TO_BE_RECEIVED.value()) {
			return 0L;
		}

		if (po.getOrderType() == OrderType.PRESALE.value()) {
			if (po.getSubState() == OrderStatus.NEW.value()) {
				return activityService.getPreSaleDeposit(po.getPresaleId());
			} else {
				return activityService.getPreSaleBalance(po.getPresaleId());
			}
		} else {
			Long o = po.getOriginPrice() == null ? 0L : po.getOriginPrice();
			Long d = po.getDiscountPrice() == null ? 0L : po.getDiscountPrice();
			Long f = po.getFreightPrice() == null ? 0L : po.getFreightPrice();
			return o - d + f;
		}
	}
}

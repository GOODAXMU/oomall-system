package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.dto.OrderDto;
import cn.edu.xmu.oomall.dto.OrderItemDto;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.constant.OrderType;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.vo.OrderPostRequest;
import cn.edu.xmu.oomall.vo.OrderPutRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	private Long id;
	private Customer customer;
	private Shop shop;
	private String orderSn;
	private List<Order> subOrders;
	private Long pid;
	private String consignee;
	private Long regionId;
	private String address;
	private String mobile;
	private String message;
	private Integer orderType;
	private Long freightPrice;
	private Long couponId;
	private Long couponActivityId;
	private Long discountPrice;
	private Long originPrice;
	private Long presaleId;
	private Long grouponId;
	private Long seckillId;
	private Long grouponDiscount;
	private Integer rebateNum;
	private LocalDateTime confirmTime;
	private String shipmentSn;
	private Integer state;
	private Integer subState;
	private Integer beDeleted;
	private List<OrderItem> orderItems;
	private LocalDateTime gmtCreated;
	private LocalDateTime gmtModified;


	public Order(Integer state, Integer subState) {
		this.state = state;
		this.subState = subState;
	}

	public Order createAndAddSubOrder(Shop shop, List<OrderItem> orderItems) {
		Order subOrder = new Order();

		subOrder.customer = this.customer;

		subOrder.shop = shop;

		subOrder.orderItems = new ArrayList<>();
		subOrder.orderItems.addAll(orderItems);

		subOrder.consignee = this.consignee;
		subOrder.regionId = this.regionId;
		subOrder.address = this.address;
		subOrder.mobile = this.mobile;
		subOrder.message = this.message;

		this.subOrders.add(subOrder);

		return subOrder;
	}

	public void calcAndSetOriginPrice() {
		long price = 0L;
		for (OrderItem oi : orderItems) {
			price += oi.getQuantity() * oi.getPrice();
		}
		originPrice = price;
	}

	public void calcAndSetDiscountPrice() {
		long t = 0L;
		for (OrderItem oi : orderItems) {
			t += oi.getDiscount() == null ? 0L : oi.getDiscount();
		}
		discountPrice = t;
	}

	public void calcAndSetRebateNum(Integer rebate) {
		rebateNum = rebate;
		for (Order o : subOrders) {
			o.rebateNum = (int) (1.0 * rebate * o.originPrice / originPrice);
		}
	}

	public Integer calcAndGetRebate() {
		if (originPrice == null) {
			return 0;
		}
		return Math.toIntExact(originPrice / 25L);
	}

	public void setOrderStatus(OrderStatus status, boolean withSubOrder) {
		state = status.value();
		if (withSubOrder && subOrders != null) {
			for (Order o : getSubOrders()) {
				o.setState(state);
			}
		}
	}

	public void setOrderType(OrderType type, boolean withSubOrder) {
		orderType = type.value();
		if (withSubOrder && subOrders != null) {
			for (Order sub : subOrders) {
				sub.setOrderType(type.value());
			}
		}
	}

	public void setCustomer(Customer c, boolean withSubOrder) {
		customer = c;
		if (withSubOrder && subOrders != null) {
			for (Order sub : subOrders) {
				sub.customer = c;
			}
		}
	}

	public static Order toOrder(OrderPo orderPo) {
		if (orderPo == null) {
			return null;
		}
		Order o = new Order();
		o.orderItems = new ArrayList<>();
		o.subOrders = new ArrayList<>();
		o.id = orderPo.getId();
		o.customer = new Customer(orderPo.getCustomerId());
		o.shop = new Shop(orderPo.getShopId());
		o.orderSn = orderPo.getOrderSn();
		o.pid = orderPo.getPid();
		o.couponActivityId = orderPo.getCouponActivityId();
		o.consignee = orderPo.getConsignee();
		o.regionId = orderPo.getRegionId();
		o.address = orderPo.getAddress();
		o.mobile = orderPo.getMobile();
		o.message = orderPo.getMessage();
		o.orderType = orderPo.getOrderType();
		o.freightPrice = orderPo.getFreightPrice();
		o.couponId = orderPo.getCouponId();
		o.discountPrice = orderPo.getDiscountPrice();
		o.originPrice = orderPo.getOriginPrice();
		o.presaleId = orderPo.getPresaleId();
		o.grouponId = orderPo.getGrouponId();
		o.grouponDiscount = orderPo.getGrouponDiscount();
		o.rebateNum = orderPo.getRebateNum();
		o.confirmTime = orderPo.getConfirmTime();
		o.shipmentSn = orderPo.getShipmentSn();
		o.state = orderPo.getState();
		o.subState = orderPo.getSubState();
		o.beDeleted = orderPo.getBeDeleted();
		o.gmtCreated = orderPo.getGmtCreate();
		o.gmtModified = orderPo.getGmtModified();
		return o;
	}

	public static Order toOrder(OrderPutRequest vo) {
		Order o = new Order();
		o.setCustomer(new Customer());
		o.setConsignee(vo.getConsignee());
		o.setAddress(vo.getAddress());
		o.setMobile(vo.getMobile());
		o.setRegionId(vo.getRegionId());
		return o;
	}

	public static Order toOrder(OrderPostRequest request) {
		Order o = new Order();
		o.customer = new Customer();
		o.shop = new Shop();
		o.subOrders = new ArrayList<>();
		o.consignee = request.getConsignee();
		o.regionId = request.getRegionId();
		o.address = request.getAddress();
		o.mobile = request.getMobile();
		o.message = request.getMessage();
		o.couponId = request.getCouponId();
		o.presaleId = request.getPresaleId();
		o.grouponId = request.getGrouponId();

		o.orderItems = new ArrayList<>();
		for (OrderPostRequest.OrderItem oi : request.getOrderItems()) {
			OrderItem noi = new OrderItem();
			noi.setSkuId(oi.getSkuId());
			noi.setQuantity(oi.getQuantity());
			noi.setCouponActivityId(oi.getCouponActId());
			o.orderItems.add(noi);
		}

		return o;
	}

	public OrderDto toOrderDto() {
		OrderDto dto = new OrderDto();
		dto.setId(id);
		dto.setCustomerId(customer == null ? null : customer.getId());
		dto.setShopId(shop == null ? null : shop.getId());
		dto.setOrderSn(orderSn);

		List<OrderItemDto> dtos = new ArrayList<>();
		for (OrderItem oi : orderItems) {
			OrderItemDto orderItemDto = oi.toOrderItemDto();
			dtos.add(orderItemDto);
		}
		dto.setOrderItems(dtos);

		dto.setPid(pid);
		dto.setConsignee(consignee);
		dto.setRegionId(regionId);
		dto.setAddress(address);
		dto.setMobile(mobile);
		dto.setMessage(message);
		dto.setOrderType(orderType);
		dto.setFreightPrice(freightPrice);
		dto.setCouponId(couponId);
		dto.setCouponActivityId(couponActivityId);
		dto.setDiscountPrice(discountPrice);
		dto.setOriginPrice(originPrice);
		dto.setPresaleId(presaleId);
		dto.setGrouponId(grouponId);
		dto.setGrouponDiscount(grouponDiscount);
		dto.setRebateNum(rebateNum);
		dto.setConfirmTime(confirmTime);
		dto.setShipmentSn(shipmentSn);
		dto.setState(state);
		dto.setSubState(subState);
		dto.setBeDeleted(beDeleted);

		return dto;
	}
}

package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.vo.OrderPostRequest;
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

	private String consignee;
	private Long regionId;
	private String address;
	private String mobile;
	private String message;

	private Integer orderType;

	private Long freightPrice;

	private Long couponId;

	private Long discountPrice;
	private Long originPrice;

	private Long presaleId;

	private Long grouponId;

	private Long grouponDiscount;

	private Integer rebateNum;

	private LocalDateTime confirmTime;

	private String shipmentSn;

	private Integer state;
	private Integer subState;

	private Integer beDeleted;

	private List<OrderItem> orderItems;


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

	public void calcAndSetParentOrderOriginPrice() {
		Long price = 0L;
		for (Order o : getSubOrders()) {
			price += o.getOriginPrice();
		}
		originPrice = price;
	}

	public void calcAndSetSubOrdersOriginPrice() {
		for (Order subOrder : getSubOrders()) {
			long price = 0L;
			for (OrderItem oi : subOrder.getOrderItems()) {
				price += oi.getQuantity() * oi.getPrice();
			}
			subOrder.setOriginPrice(price);
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
		if (withSubOrder) {
			for (Order o : getSubOrders()) {
				o.setState(state);
			}
		}
	}

	public String createAndGetOrderSn() {
		orderSn = UUID.randomUUID().toString();
		return orderSn;
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
			o.orderItems.add(noi);
		}

		return o;
	}
}

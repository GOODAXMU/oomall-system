package cn.edu.xmu.oomall.entity;

import cn.edu.xmu.oomall.bo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xincong yao
 * @date 2020-11-07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long customerId;

	private Long shopId;

	private String orderSn;

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

	private Long grouponDiscount;

	private Integer rebateNum;

	private LocalDateTime confirmTime;

	private String shipmentSn;

	private Integer state;

	@Column(name = "substate")
	private Integer subState;

	private Integer beDeleted;

	private LocalDateTime gmtCreated;

	private LocalDateTime gmtModified;

	public OrderPo(String orderSn, Long shopId) {
		this.orderSn = orderSn;
		this.shopId = shopId;
	}

	public static OrderPo toOrderPo(Order o) {
		if (o == null) {
			return null;
		}
		OrderPo po = new OrderPo();
		po.setId(o.getId());
		po.setCustomerId(o.getCustomer() == null ? null : o.getCustomer().getId());
		po.setShopId(o.getShop() == null ? null : o.getShop().getId());
		po.setOrderSn(o.getOrderSn());
		po.setPid(o.getPid());
		po.setConsignee(o.getConsignee());
		po.setRegionId(o.getRegionId());
		po.setAddress(o.getAddress());
		po.setMobile(o.getMobile());
		po.setMessage(o.getMessage());
		po.setOrderType(o.getOrderType());
		po.setFreightPrice(o.getFreightPrice());
		po.setCouponId(o.getCouponId());
		po.setCouponActivityId(o.getCouponActivityId());
		po.setDiscountPrice(o.getDiscountPrice());
		po.setOriginPrice(o.getOriginPrice());
		po.setPresaleId(o.getPresaleId());
		po.setGrouponId(o.getGrouponId());
		po.setGrouponDiscount(o.getGrouponDiscount());
		po.setRebateNum(o.getRebateNum());
		po.setConfirmTime(o.getConfirmTime());
		po.setShipmentSn(o.getShipmentSn());
		po.setState(o.getState());
		po.setSubState(o.getSubState());
		po.setBeDeleted(o.getBeDeleted());
		po.setGmtCreated(o.getGmtCreated());
		po.setGmtModified(o.getGmtModified());
		return po;
	}
}

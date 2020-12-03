package cn.edu.xmu.oomall.entity;

import cn.edu.xmu.oomall.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xincong yao
 * @date 2020-11-28
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

	public static OrderPo toOrderPo(OrderDto dto) {
		OrderPo po = new OrderPo();
		po.setId(dto.getId());
		po.setCustomerId(dto.getCustomerId());
		po.setShopId(dto.getShopId());
		po.setOrderSn(dto.getOrderSn());
		po.setPid(dto.getPid());
		po.setConsignee(dto.getConsignee());
		po.setRegionId(dto.getRegionId());
		po.setAddress(dto.getAddress());
		po.setMobile(dto.getMobile());
		po.setMessage(dto.getMessage());
		po.setOrderType(dto.getOrderType());
		po.setFreightPrice(dto.getFreightPrice());
		po.setCouponId(dto.getCouponId());
		po.setCouponActivityId(dto.getCouponActivityId());
		po.setDiscountPrice(dto.getDiscountPrice());
		po.setOriginPrice(dto.getOriginPrice());
		po.setPresaleId(dto.getPresaleId());
		po.setGrouponId(dto.getGrouponId());
		po.setGrouponDiscount(dto.getGrouponDiscount());
		po.setRebateNum(dto.getRebateNum());
		po.setConfirmTime(dto.getConfirmTime());
		po.setShipmentSn(dto.getShipmentSn());
		po.setState(dto.getState());
		po.setSubState(dto.getSubState());
		po.setBeDeleted(dto.getBeDeleted());
		po.setGmtCreated(LocalDateTime.now());
		po.setGmtModified(LocalDateTime.now());

		return po;
	}
}

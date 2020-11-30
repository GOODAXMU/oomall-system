package cn.edu.xmu.oomall.entity;

import cn.edu.xmu.ooamll.dto.OrderItemDto;
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
@Table(name = "order_item")
public class OrderItemPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long orderId;

	private Long goodsSkuId;

	private Integer quantity;

	private Long price;

	private Long discount;

	private String name;

	private Long couponId;

	private Long couponActivityId;

	private Long beShareId;

	private LocalDateTime gmtCreated;

	private LocalDateTime gmtModified;

	public static OrderItemPo toOrderItemPo(OrderItemDto dto) {
		OrderItemPo po = new OrderItemPo();
		po.setId(dto.getId());
		po.setOrderId(dto.getOrderId());
		po.setGoodsSkuId(dto.getSkuId());
		po.setQuantity(dto.getQuantity());
		po.setPrice(dto.getPrice());
		po.setDiscount(dto.getDiscount());
		po.setName(dto.getName());
		po.setCouponActivityId(dto.getCouponActivityId());
		po.setBeShareId(dto.getBeShareId());
		po.setGmtCreated(LocalDateTime.now());
		po.setGmtModified(LocalDateTime.now());

		return po;
	}
}

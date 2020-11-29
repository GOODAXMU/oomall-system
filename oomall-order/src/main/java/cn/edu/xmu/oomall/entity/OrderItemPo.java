package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author zhibin lan
 * @date 2020-11-07
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
}

package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Wang Zhizhou
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
}

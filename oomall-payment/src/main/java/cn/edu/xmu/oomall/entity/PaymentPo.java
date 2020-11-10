package cn.edu.xmu.oomall.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author yan song
 * @date 2020-11-07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_payment")
public class PaymentPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long amount;

	private Long actualAmount;

	private Integer paymentPattern;

	private LocalDateTime payTime;

	private String paySn;

	private LocalDateTime beginTime;

	private LocalDateTime endTime;

	private Long orderId;

	private Integer state;
}

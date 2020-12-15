package cn.edu.xmu.oomall.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author yan song
 * @date 2020-11-07
 * @author Wang Zhizhou
 * modified 2020/12/15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class PaymentPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long aftersaleId;

	private Long amount;

	private Long actualAmount;

	private String paymentPattern;

	private LocalDateTime payTime;

	private String paySn;

	private LocalDateTime beginTime;

	private LocalDateTime endTime;

	private Long orderId;

	private Integer state;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	public void setGmt() {
		this.gmtCreate = LocalDateTime.now().withNano(0);
		this.gmtModified = LocalDateTime.now().withNano(0);
	}
}

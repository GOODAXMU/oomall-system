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
 * modified 2020/12/14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refund")
public class RefundPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long paymentId;

	private Long amount;

	private Long orderId;

	private Long aftersaleId;

	private Integer state;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	public void setGmt() {
		this.gmtCreate = LocalDateTime.now();
		this.gmtModified = LocalDateTime.now();
	}
}

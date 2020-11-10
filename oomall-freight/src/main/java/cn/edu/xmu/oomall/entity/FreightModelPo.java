package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author jianheng huang
 * @date 2020-11-7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_freight_model")
public class FreightModelPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long shopId;

	private String name;

	private String defaultModel;

	private Integer type;

	private Integer unit;

}

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
@Table(name = "order_weight_freight_model")
public class WeightFreightModelPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long freightModelId;

	private Integer firstWeight;

	private Integer firstWeightFreight;

	private Integer tenPrice;

	private Integer fiftyPrice;

	private Integer hundredPrice;

	private Integer trihunPrice;

	private Integer abovePrice;

	private Long regionId;


}

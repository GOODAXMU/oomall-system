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

	private Long firstWeight;

	private Long firstWeightFreight;

	private Long tenPrice;

	private Long fiftyPrice;

	private Long hundredPrice;

	private Long trihunPrice;

	private Long abovePrice;

	private Long regionId;


}

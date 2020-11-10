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
@Table(name = "order_piece_freight_model")
public class PieceFreightModelPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long freightModelId;

	private Integer firstItems;

	private Long firstItemsPrice;

	private Integer additionalItems;

	private Long additionalItemsPrice;

	private Long regionId;


}

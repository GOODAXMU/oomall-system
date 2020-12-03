package cn.edu.xmu.oomall.entity;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jianheng huang
 * @date 2020-11-7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "piece_freight_model")
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

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	public PieceFreightModelPo(PieceFreightModel pieceFreightModel){
		id = pieceFreightModel.getId();
		firstItems = pieceFreightModel.getFirstItem();
		firstItemsPrice = pieceFreightModel.getAdditionalItemsPrice();
		additionalItems = pieceFreightModel.getAdditionalItems();
		regionId = pieceFreightModel.getRid();
		gmtModified = LocalDateTime.now();
	}

}

package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yan song
 * @date 2020-11-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceFreightQueryResponse {
	private Long id;

	private Long regionId;

	private Integer firstItem;

	private Long firstItemPrice;

	private Integer additionalItems;

	private Long additionalItemPrice;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	public PieceFreightQueryResponse(PieceFreightModelPo pieceFreightModelPo){
		id = pieceFreightModelPo.getId();
		regionId = pieceFreightModelPo.getRegionId();
		firstItem = pieceFreightModelPo.getFirstItems();
		firstItemPrice = pieceFreightModelPo.getFirstItemsPrice();
		additionalItems = pieceFreightModelPo.getAdditionalItems();
		additionalItemPrice = pieceFreightModelPo.getAdditionalItemsPrice();
	}

	public List<PieceFreightQueryResponse> PieceFreightQueryResponseListTransfer(List<PieceFreightModelPo> pieceFreightModelPos){
		List<PieceFreightQueryResponse> pieceFreightQueryResponses = new ArrayList<>();
		for(PieceFreightModelPo p : pieceFreightModelPos){
			pieceFreightQueryResponses.add(new PieceFreightQueryResponse(p));
		}
		return pieceFreightQueryResponses;
	}
}
package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yan song
 * @date 2020-11-29
 */
public class PieceFreightQueryResponse {
	@NotNull
	@Min(value = 0)
	private Long id;

	@NotNull
	@Min(value = 0)
	private Long regionId;

	@NotNull
	@Min(value = 0)
	private Integer firstItem;

	@NotNull
	@Min(value = 0)
	private Long firstItemPrice;

	@NotNull
	@Min(value = 0)
	private Integer additionalItems;

	@NotNull
	@Min(value = 0)
	private Long additionalItemPrice;

	@NotNull
	private LocalDateTime gmtCreate;

	@NotNull
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
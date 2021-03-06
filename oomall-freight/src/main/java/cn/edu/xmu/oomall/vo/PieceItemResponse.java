package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Wang Zhizhou
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceItemResponse {
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
    private Long additionalItemsPrice;

    @NotNull
    private String gmtCreate;

    @NotNull
    private String gmtModified;

    public PieceItemResponse(PieceFreightModelPo pieceFreightModelPo){
        id = pieceFreightModelPo.getId();
        regionId = pieceFreightModelPo.getRegionId();
        firstItem = pieceFreightModelPo.getFirstItems();
        firstItemPrice = pieceFreightModelPo.getFirstItemsPrice();
        additionalItems = pieceFreightModelPo.getAdditionalItems();
        additionalItemsPrice = pieceFreightModelPo.getAdditionalItemsPrice();
        gmtCreate = LocalDateTime.now().toString();
        gmtModified = LocalDateTime.now().toString();
    }
    public PieceItemResponse(PieceFreightModel pieceFreightModel){
        id = pieceFreightModel.getId();
        regionId = pieceFreightModel.getRid();
        firstItem = pieceFreightModel.getFirstItem();
        firstItemPrice = pieceFreightModel.getFirstItemPrice();
        additionalItems = pieceFreightModel.getAdditionalItems();
        additionalItemsPrice = pieceFreightModel.getAdditionalItemsPrice();
        gmtCreate = LocalDateTime.now().toString();
        gmtModified = LocalDateTime.now().toString();
    }
}

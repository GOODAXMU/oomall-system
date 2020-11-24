package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceFreightModel {

    private Long id;

    private Long rid;

    private Integer firstItem;

    private Long firstItemPrice;

    private Integer additionalItems;

    private Long additionalItemsPrice;

    /**
     * 构造函数
     * @param pieceFreightModelPo Po对象
     */
    public PieceFreightModel(PieceFreightModelPo pieceFreightModelPo){
        id = pieceFreightModelPo.getId();
        rid = pieceFreightModelPo.getRegionId();
        firstItem = pieceFreightModelPo.getFirstItems();
        firstItemPrice = pieceFreightModelPo.getFirstItemsPrice();
        additionalItems = pieceFreightModelPo.getAdditionalItems();
        additionalItemsPrice = pieceFreightModelPo.getAdditionalItemsPrice();
    }
}

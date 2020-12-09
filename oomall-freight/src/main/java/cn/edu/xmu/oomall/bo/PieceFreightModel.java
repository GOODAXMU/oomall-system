package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.vo.PieceFreightModelModifyRequest;
import cn.edu.xmu.oomall.vo.PieceItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-23
 */

/**
 * modified by yan song
 *
 * @date 2020-12-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PieceFreightModel {

    private Long id;

    private Long rid;

    private Long freightModelId;

    private Integer firstItem;

    private Long firstItemPrice;

    private Integer additionalItems;

    private Long additionalItemsPrice;

    /**
     * 构造函数
     *
     * @param pieceFreightModelPo Po对象
     */
    public PieceFreightModel(PieceFreightModelPo pieceFreightModelPo) {
        freightModelId = pieceFreightModelPo.getFreightModelId();
        id = pieceFreightModelPo.getId();
        rid = pieceFreightModelPo.getRegionId();
        firstItem = pieceFreightModelPo.getFirstItems();
        firstItemPrice = pieceFreightModelPo.getFirstItemsPrice();
        additionalItems = pieceFreightModelPo.getAdditionalItems();
        additionalItemsPrice = pieceFreightModelPo.getAdditionalItemsPrice();
    }

    /**
     * 构造函数
     *
     * @param pieceFreightModelInfo Po对象,
     * @param id                    Long
     */
    public PieceFreightModel(PieceItemRequest pieceFreightModelInfo, Long id) {
        this.freightModelId = id;
        rid = pieceFreightModelInfo.getRegionId();
        firstItem = pieceFreightModelInfo.getFirstItem();
        firstItemPrice = pieceFreightModelInfo.getFirstItemPrice();
        additionalItems = pieceFreightModelInfo.getAdditionalItems();
        additionalItemsPrice = pieceFreightModelInfo.getAdditionalItemPrice();
    }

    /**
     * 构造函数
     *
     * @param pieceFreightModelinfo Po对象,id Long
     */
    public PieceFreightModel(PieceFreightModelModifyRequest pieceFreightModelInfo, Long id) {
        this.id = id;
        rid = pieceFreightModelInfo.getRegionId();
        firstItem = pieceFreightModelInfo.getFirstItem();
        firstItemPrice = pieceFreightModelInfo.getFirstItemPrice();
        additionalItems = pieceFreightModelInfo.getAdditionalItems();
        additionalItemsPrice = pieceFreightModelInfo.getAdditionalItemPrice();
    }

    /**
     * @param pieceFreightModelPos Po对象列表
     */
    public List<PieceFreightModel> pieceFreightModelPoListToBoList(List<PieceFreightModelPo> pieceFreightModelPos) {
        List<PieceFreightModel> pieceFreightModels = new ArrayList<>();
        for (PieceFreightModelPo w : pieceFreightModelPos) {
            pieceFreightModels.add(new PieceFreightModel(w));
        }
        return pieceFreightModels;
    }
}

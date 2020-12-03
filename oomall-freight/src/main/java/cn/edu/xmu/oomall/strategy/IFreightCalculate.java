package cn.edu.xmu.oomall.strategy;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.bo.WeightFreightModel;

import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */
public interface IFreightCalculate {

    /**
     * 计算运费
     *
     * @param items
     * @param weightFreightModels
     * @param pieceFreightModels
     * @return Long 运费
     */
    Long calculateFreight(List<PurchaseItem> items,
                          List<WeightFreightModel> weightFreightModels,
                          List<PieceFreightModel> pieceFreightModels);


    /**
     * 用计重模板计算运费
     *
     * @param item
     * @param weightFreightModel
     * @return Long 运费
     */
    Long calActivityFreightByWeight(PurchaseItem item, WeightFreightModel weightFreightModel);

    /**
     * 用计件模板计算运费
     *
     * @param item
     * @param pieceFreightModel
     * @return Long 运费
     */
    Long calActivityFreightByPiece(PurchaseItem item, PieceFreightModel pieceFreightModel);
}

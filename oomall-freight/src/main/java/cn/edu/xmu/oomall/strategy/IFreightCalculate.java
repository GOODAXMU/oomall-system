package cn.edu.xmu.oomall.strategy;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.bo.WeightFreightModel;

import java.util.List;

public interface IFreightCalculate {

    Long calculateFreight(List<PurchaseItem> items,
                                 List<WeightFreightModel> weightFreightModels,
                                 List<PieceFreightModel> pieceFreightModels);


    Long calActivityFreightByWeight(PurchaseItem item, WeightFreightModel weightFreightModel);

    Long calActivityFreightByPiece(PurchaseItem item, PieceFreightModel pieceFreightModel);
}

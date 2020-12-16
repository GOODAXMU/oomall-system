package cn.edu.xmu.oomall.strategy.impl;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.strategy.IFreightCalculate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewFreightCalculateImpl implements IFreightCalculate {
    @Value(value = "${oomall.kg}")
    private Long kg;

    @Value("${oomall.ten}")
    private Long ten;

    @Value(value = "${oomall.fifty}")
    private Long fifty;

    @Value(value = "${oomall.hundry}")
    private Long hundry;

    @Value(value = "${oomall.tri-hundry}")
    private Long trihundry;

    @Override
    public Long calculateFreight(List<PurchaseItem> items,
                                 List<WeightFreightModel> weightFreightModels,
                                 List<PieceFreightModel> pieceFreightModels) {
        Long piecePrice = calculateByPieceModel(pieceFreightModels, items);
        Long weightPrice = calculateByWeightModel(weightFreightModels, items);
        return Long.max(piecePrice, weightPrice);
    }


    public Long calculateByWeightModel(List<WeightFreightModel> weightFreightModels, List<PurchaseItem> items) {
        Long maxPrice = Long.valueOf(0);

        Long totalWeight = Long.valueOf(0);

        //获取总重量
        for (PurchaseItem item : items) {
            totalWeight += item.getWeight() * item.getCount();
        }

        for (WeightFreightModel weightFreightModel : weightFreightModels) {
            Long curPrice = weightFreightModel.getFirstWeightFreight();
            weightFreightModel.setFirstWeight(weightFreightModel.getFirstWeight() * weightFreightModel.getUnit());
            if (totalWeight <= weightFreightModel.getFirstWeight() * kg) {
            } else if (totalWeight <= ten * kg) {
                curPrice += (totalWeight - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
            } else if (totalWeight <= fifty * kg) {
                curPrice += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
                curPrice += (totalWeight - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
            } else if (totalWeight <= hundry * kg) {
                curPrice += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
                curPrice += (totalWeight - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
            } else if (totalWeight <= trihundry * kg) {
                curPrice += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
                curPrice += (hundry * kg - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
                curPrice += (totalWeight - hundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getTrihunPrice();
            } else {
                curPrice += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
                curPrice += (hundry * kg - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
                curPrice += (trihundry * kg - hundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getTrihunPrice();
                curPrice += (totalWeight - trihundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getAbovePrice();
            }
            if (curPrice > maxPrice) {
                maxPrice = curPrice;
            }
        }
        return maxPrice;
    }

    public Long calculateByPieceModel(List<PieceFreightModel> pieceFreightModels, List<PurchaseItem> items) {
        Long maxPrice = Long.valueOf(0);
        Long totalCount = Long.valueOf(0);
        for (PurchaseItem item : items) {
            totalCount += item.getCount();
        }
        for (PieceFreightModel pieceFreightModel : pieceFreightModels) {
            Long curPrice = pieceFreightModel.getFirstItemPrice();
            if (totalCount > pieceFreightModel.getFirstItem()) {
                curPrice += (int) Math.ceil((totalCount - pieceFreightModel.getFirstItem()) * 1.0 / pieceFreightModel.getAdditionalItems())
                        * pieceFreightModel.getAdditionalItemsPrice();
            }
            if (curPrice > maxPrice) {
                maxPrice = curPrice;
            }
        }
        return maxPrice;
    }

    @Override
    public Long calActivityFreightByWeight(PurchaseItem item, WeightFreightModel weightFreightModel) {
        Long price = weightFreightModel.getFirstWeightFreight();

        Long weight = item.getWeight() * item.getCount();
        if (weight <= weightFreightModel.getFirstWeight() * kg) {
        } else if (weight <= ten * kg) {
            price += (item.getWeight() - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
        } else if (weight <= fifty * kg) {
            price += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
            price += (item.getWeight() - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
        } else if (weight <= hundry * kg) {
            price += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
            price += (item.getWeight() - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
        } else if (weight <= trihundry * kg) {
            price += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
            price += (hundry * kg - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
            price += (item.getWeight() - hundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getTrihunPrice();
        } else {
            price += (ten * kg - weightFreightModel.getFirstWeight() * kg) / weightFreightModel.getUnit() * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / weightFreightModel.getUnit() * weightFreightModel.getFiftyPrice();
            price += (hundry * kg - fifty * kg) / weightFreightModel.getUnit() * weightFreightModel.getHundredPrice();
            price += (trihundry * kg - hundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getTrihunPrice();
            price += (item.getWeight() - trihundry * kg) / weightFreightModel.getUnit() * weightFreightModel.getAbovePrice();
        }
        return price;
    }

    @Override
    public Long calActivityFreightByPiece(PurchaseItem item, PieceFreightModel pieceFreightModel) {
        Long price = pieceFreightModel.getFirstItemPrice();
        if (item.getCount() > pieceFreightModel.getFirstItem()) {
            price += (int) Math.ceil((item.getCount() - pieceFreightModel.getFirstItem()) * 1.0 / pieceFreightModel.getAdditionalItems())
                    * pieceFreightModel.getAdditionalItemsPrice();
        }
        return price;
    }
}

package cn.edu.xmu.oomall.strategy.impl;


import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.strategy.IFreightCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */
@Component
public class FreightCalculateImpl implements IFreightCalculate {

    @Value(value = "${oomall.kg}")
    private Long kg;

    @Value("${oomall.half-kg}")
    private Long halfKg;

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

        for(PurchaseItem item : items){
            totalWeight += item.getWeight();
        }

        for (WeightFreightModel weightFreightModel : weightFreightModels) {
            Long curPrice = Long.valueOf(0);
            if (totalWeight <= weightFreightModel.getFirstWeight()) {
                curPrice = weightFreightModel.getFirstWeightFreight();
                break;
            } else if (totalWeight <= ten * kg) {
                curPrice += weightFreightModel.getFirstWeightFreight();
                curPrice += (totalWeight - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                break;
            } else if (totalWeight <= fifty * kg) {
                curPrice += weightFreightModel.getFirstWeightFreight();
                curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                curPrice += (totalWeight - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                break;
            } else if (totalWeight <= hundry * kg) {
                curPrice += weightFreightModel.getFirstWeightFreight();
                curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                curPrice += (totalWeight - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                break;
            } else if (totalWeight <= trihundry * kg) {
                curPrice += weightFreightModel.getFirstWeightFreight();
                curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                curPrice += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                curPrice += (totalWeight - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
                break;
            } else {
                curPrice += weightFreightModel.getFirstWeightFreight();
                curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                curPrice += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                curPrice += (trihundry * kg - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
                curPrice += (totalWeight - trihundry * kg) / halfKg * weightFreightModel.getAbovePrice();
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
        for(PurchaseItem item: items){
            totalCount += item.getCount();
        }
        for (PieceFreightModel pieceFreightModel : pieceFreightModels) {
            Long curPrice = Long.valueOf(0);
            if (totalCount <= pieceFreightModel.getFirstItem()) {
                curPrice = pieceFreightModel.getFirstItemPrice();
            } else {
                curPrice += pieceFreightModel.getFirstItemPrice();
                curPrice += (totalCount - pieceFreightModel.getFirstItem() + 1) / pieceFreightModel.getAdditionalItems()
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
        Long price = Long.valueOf(0);
        if (item.getWeight() <= weightFreightModel.getFirstWeight()) {
            price = weightFreightModel.getFirstWeightFreight();
        } else if (item.getWeight() <= ten * kg) {
            price += weightFreightModel.getFirstWeightFreight();
            price += (item.getWeight() - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
        } else if (item.getWeight() <= fifty * kg) {
            price += weightFreightModel.getFirstWeightFreight();
            price += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
            price += (item.getWeight() - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
        } else if (item.getWeight() <= hundry * kg) {
            price += weightFreightModel.getFirstWeightFreight();
            price += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
            price += (item.getWeight() - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
        } else if (item.getWeight() <= trihundry * kg) {
            price += weightFreightModel.getFirstWeightFreight();
            price += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
            price += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
            price += (item.getWeight() - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
        } else {
            price += weightFreightModel.getFirstWeightFreight();
            price += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
            price += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
            price += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
            price += (trihundry * kg - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
            price += (item.getWeight() - trihundry * kg) / halfKg * weightFreightModel.getAbovePrice();
        }
        return price;
    }

    @Override
    public Long calActivityFreightByPiece(PurchaseItem item, PieceFreightModel pieceFreightModel) {
        Long price = Long.valueOf(0);
        if (item.getCount() <= pieceFreightModel.getFirstItem()) {
            price = pieceFreightModel.getFirstItemPrice();
        } else {
            price += pieceFreightModel.getFirstItemPrice();
            price += (item.getCount() - pieceFreightModel.getFirstItem() + 1) / pieceFreightModel.getAdditionalItems()
                    * pieceFreightModel.getAdditionalItemsPrice();
        }
        return price;
    }
}

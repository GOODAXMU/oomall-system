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

    public Long calculateFreight(List<PurchaseItem> items,
                                 List<WeightFreightModel> weightFreightModels,
                                 List<PieceFreightModel> pieceFreightModels) {
        Long piecePrice = calculateByPieceModel(pieceFreightModels, items);
        Long weightPrice = calculateByWeightModel(weightFreightModels, items);
        return piecePrice > weightPrice ? piecePrice : weightPrice;
    }


    public Long calculateByWeightModel(List<WeightFreightModel> weightFreightModels, List<PurchaseItem> items) {
        Long maxPrice = Long.valueOf(0);
        for (WeightFreightModel weightFreightModel : weightFreightModels) {
            Long curPrice = Long.valueOf(0);
            for (PurchaseItem item : items) {
                if (item.getWeight() <= weightFreightModel.getFirstWeight()) {
                    curPrice = weightFreightModel.getFirstWeightFreight();
                    break;
                } else if (item.getWeight() <= ten * kg) {
                    curPrice += weightFreightModel.getFirstWeightFreight();
                    curPrice += (item.getWeight() - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                    break;
                } else if (item.getWeight() <= fifty * kg) {
                    curPrice += weightFreightModel.getFirstWeightFreight();
                    curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                    curPrice += (item.getWeight() - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                    break;
                } else if (item.getWeight() <= hundry * kg) {
                    curPrice += weightFreightModel.getFirstWeightFreight();
                    curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                    curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                    curPrice += (item.getWeight() - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                    break;
                } else if (item.getWeight() <= trihundry * kg) {
                    curPrice += weightFreightModel.getFirstWeightFreight();
                    curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                    curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                    curPrice += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                    curPrice += (item.getWeight() - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
                    break;
                } else {
                    curPrice += weightFreightModel.getFirstWeightFreight();
                    curPrice += (ten * kg - weightFreightModel.getFirstWeight()) / halfKg * weightFreightModel.getTenPrice();
                    curPrice += (fifty * kg - ten * kg) / halfKg * weightFreightModel.getFiftyPrice();
                    curPrice += (hundry * kg - fifty * kg) / halfKg * weightFreightModel.getHundredPrice();
                    curPrice += (trihundry * kg - hundry * kg) / halfKg * weightFreightModel.getTrihunPrice();
                    curPrice += (item.getWeight() - trihundry * kg) / halfKg * weightFreightModel.getAbovePrice();
                }
            }
            if (curPrice > maxPrice)
                maxPrice = curPrice;
        }
        return maxPrice;
    }

    public Long calculateByPieceModel(List<PieceFreightModel> pieceFreightModels, List<PurchaseItem> items) {
        Long maxPrice = Long.valueOf(0);
        for (PieceFreightModel pieceFreightModel : pieceFreightModels) {
            Long curPrice = Long.valueOf(0);
            for (PurchaseItem item : items) {
                if (item.getCount() <= pieceFreightModel.getFirstItem()) {
                    curPrice = pieceFreightModel.getFirstItemPrice();
                } else {
                    curPrice += pieceFreightModel.getFirstItemPrice();
                    curPrice += (item.getCount() - pieceFreightModel.getFirstItem() + 1) / pieceFreightModel.getAdditionalItems()
                            * pieceFreightModel.getAdditionalItemsPrice();
                }
            }
            if (curPrice > maxPrice)
                maxPrice = curPrice;
        }
        return maxPrice;
    }
}

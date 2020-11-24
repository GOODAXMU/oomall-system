package cn.edu.xmu.oomall.service;


import cn.edu.xmu.oomall.bo.*;
import cn.edu.xmu.oomall.dao.FreightDao;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.strategy.IFreightCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */

@Service
public class FreightService {
    @Autowired
    private FreightDao freightDao;

    @Autowired
    private ServiceFactory serviceFactory;

    private IGoodService goodService;
    private IFreightCalculate freightCalculate;

    @Value("${oomall.freight.model.weight}")
    private int weightModel;

    @Value("${oomall.freight.model.piece}")
    private int pieceModel;

    @PostConstruct
    public void init() {
        goodService = (IGoodService) serviceFactory.get(IGoodService.class);
        freightCalculate = (IFreightCalculate) serviceFactory.get(IFreightCalculate.class);
    }

    /**
     * 计算运费
     *
     * @param purchaseItems
     * @param rid
     * @return
     */
    public Long calFreight(List<PurchaseItem> purchaseItems, Long rid) {

        //获取所有商品的运费模板并获取购买项重量
        List<FreightModel> freightModels = new ArrayList<>();
        for (PurchaseItem item : purchaseItems) {
            FreightModel freightModel = getFreightModel(item.getSkuId());
            freightModels.add(freightModel);
            item.setWeight(goodService.getGoodsSkuWeightById(item.getSkuId()) * freightModel.getUnit());
        }

        //没有定义模板则使用默认运费模板
        if (null == freightModels) {
            for (PurchaseItem item : purchaseItems) {
                freightModels.add(getDefaultFreightModel(item.getSkuId()));
            }
        }

        //获取重量运费模板和计件运费模板
        List<WeightFreightModel> weightFreightModels = new ArrayList<>();
        List<PieceFreightModel> pieceFreightModels = new ArrayList<>();
        for (FreightModel freightModel : freightModels) {
            if (freightModel.getType() == weightModel) {
                WeightFreightModel weightFreightModel = getWeightFreightModel(freightModel, rid);
                if (null != weightFreightModel)
                    weightFreightModels.add(weightFreightModel);
            } else if (freightModel.getType() == pieceModel) {
                PieceFreightModel pieceFreightModel = getPieceFreightModel(freightModel, rid);
                if (null != pieceFreightModel)
                    pieceFreightModels.add(pieceFreightModel);
            }
        }

        //计算
        return freightCalculate.calculateFreight(purchaseItems, weightFreightModels, pieceFreightModels);

    }


    /**
     * 获取运费模板
     *
     * @param skuId
     * @return
     */
    public FreightModel getFreightModel(Long skuId) {
        Long freightModelId = goodService.getFreightModelId(skuId);
        FreightModel freightModel = freightDao.getFreightModelById(freightModelId);
        return freightModel;
    }

    /**
     * 获取默认运费模板
     *
     * @param skuId
     * @return
     */
    public FreightModel getDefaultFreightModel(Long skuId) {
        Long shopId = goodService.getShopId(skuId);
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setShopId(shopId);
        return freightDao.getFreightModelByPo(freightModelPo);
    }

    /**
     * 获取重量运费模板
     *
     * @param freightModel
     * @return
     */
    public WeightFreightModel getWeightFreightModel(FreightModel freightModel, Long rid) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setFreightModelId(freightModel.getId());
        weightFreightModelPo.setRegionId(rid);
        return freightDao.getWeightFreightModelByPo(weightFreightModelPo);
    }

    /**
     * 获取计件运费模板
     *
     * @param freightModel
     * @return
     */
    public PieceFreightModel getPieceFreightModel(FreightModel freightModel, Long rid) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setFreightModelId(freightModel.getId());
        pieceFreightModelPo.setRegionId(rid);
        return freightDao.getPieceFreightModelByPo(pieceFreightModelPo);
    }
}

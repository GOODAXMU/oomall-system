package cn.edu.xmu.oomall.service;


import cn.edu.xmu.oomall.bo.*;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.FreightDao;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.strategy.IFreightCalculate;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.FreightModelDefineResponse;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */

@Service
@Slf4j
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
     * @author zhibin lan
     */
    public Reply<Long> calFreight(List<PurchaseItem> purchaseItems, Long rid) {

        //获取所有商品的运费模板并获取购买项重量
        List<FreightModel> freightModels = new ArrayList<>();
        for (PurchaseItem item : purchaseItems) {
            FreightModel freightModel = freightDao.getFreightModelById(goodService.getFreightModelId(item.getSkuId())).getData();
            freightModels.add(freightModel);
            item.setWeight(goodService.getGoodsSkuWeightById(item.getSkuId()) * freightModel.getUnit());
        }

        //没有定义模板则使用商家默认运费模板
        if (null == freightModels) {
            for (PurchaseItem item : purchaseItems) {
                freightModels.add(freightDao.getDefaultFreightModel(goodService.getShopId(item.getSkuId())));
            }
        }

        //获取重量运费模板和计件运费模板
        List<WeightFreightModel> weightFreightModels = new ArrayList<>();
        List<PieceFreightModel> pieceFreightModels = new ArrayList<>();
        for (FreightModel freightModel : freightModels) {
            freightModel.setRid(rid);
            if (freightModel.getType() == weightModel) {
                WeightFreightModel weightFreightModel = freightDao.getWeightFreightModel(freightModel);
                if (null != weightFreightModel)
                    weightFreightModels.add(weightFreightModel);
            } else if (freightModel.getType() == pieceModel) {
                PieceFreightModel pieceFreightModel = freightDao.getPieceFreightModel(freightModel);
                if (null != pieceFreightModel)
                    pieceFreightModels.add(pieceFreightModel);
            }
        }

        //计算
        return new Reply<>(freightCalculate.calculateFreight(purchaseItems, weightFreightModels, pieceFreightModels));

    }

    /**
     * 定义运费模板
     *
     * @param freightModel
     * @return
     * @author zhibin lan
     */
    @Transactional
    public Reply<FreightModel> defineFreightModel(FreightModel freightModel) {
        Reply<FreightModel> reply = freightDao.createFreightModel(freightModel);
        if (reply.getResponseStatus() != ResponseStatus.OK)
            return new Reply<>(reply.getResponseStatus());
        return reply;
    }

    /**
     * 分页查询运费模板
     *
     * @param pageInfo
     * @param name     模板名称
     * @param shopId   店铺id
     * @return
     * @author zhibin lan
     */
    public Reply<List<FreightModel>> findAllFreightModels(PageInfo pageInfo, String name, Long shopId) {
        return freightDao.findAllFreightModels(pageInfo, name, shopId);
    }

    /**
     * 查询指定运费模板
     *
     * @param id
     * @return
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id) {
        return freightDao.getFreightModelById(id);
    }

    /**
     * 更新运费模板
     *
     * @param freightModel
     * @return
     * @author zhibin lan
     */
    @Transactional
    public Reply updateFreightModel(FreightModel freightModel) {
        return freightDao.updateFreightModel(freightModel);
    }

    /**
     * 克隆运费模板
     *
     * @param id     模板id
     * @param shopId 商铺id
     * @return
     * @author zhibin lan
     */
    @Transactional
    public Reply<FreightModel> cloneFreightModel(Long id, Long shopId) {
        return freightDao.cloneFreightModel(id, shopId);
    }

    /**
     * 删除运费模板
     *
     * @param id     模板id
     * @param shopId 商铺id
     * @return
     * @author zhibin lan
     */
    public Reply deleteFreightModel(Long id, Long shopId) {
        Reply reply = freightDao.deleteFreightModel(id, shopId);
        if (!reply.isOk())
            return reply;
        freightDao.deleteWeightFreightModel(id);
        freightDao.deletePieceFreightModel(id);
        goodService.deleteGoodsFreightModel(id, shopId);
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 设置默认运费模板
     *
     * @param id     模板id
     * @param shopId 商铺id
     * @return
     * @author zhibin lan
     */
    public Reply defineDefaultFreightModel(Long id, Long shopId) {
        return freightDao.defineDefaultFreightModel(id, shopId);
    }
}

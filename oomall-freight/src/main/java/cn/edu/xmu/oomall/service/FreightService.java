package cn.edu.xmu.oomall.service;


import cn.edu.xmu.oomall.bo.*;
import cn.edu.xmu.oomall.constant.ModelType;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.dao.FreightDao;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.external.service.IRegionService;
import cn.edu.xmu.oomall.external.util.ServiceFactory;
import cn.edu.xmu.oomall.strategy.IFreightCalculate;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.FreightModelDefineResponse;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private IRegionService regionService;

    private IFreightCalculate freightCalculate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${oomall.redis.prifix}")
    private String prifix;

    @Value("${oomall.redis.delimiter}")
    private String delimiter;

    @PostConstruct
    public void init() {
        goodService = (IGoodService) serviceFactory.get(IGoodService.class);
        freightCalculate = (IFreightCalculate) serviceFactory.get(IFreightCalculate.class);
        regionService = (IRegionService) serviceFactory.get(IRegionService.class);
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
            if (freightModel != null && !freightModels.contains(freightModel)) {
                freightModels.add(freightModel);
            }
            item.setWeight(goodService.getGoodsSkuWeightById(item.getSkuId()));
        }

        List<Long> shopIds = new ArrayList<>();

        //没有定义模板则使用商家默认运费模板
        if (freightModels.isEmpty()) {
            for (PurchaseItem item : purchaseItems) {
                Long shopId = goodService.getShopId(item.getSkuId());
                if (shopId != null && !shopIds.contains(shopId)) {
                    shopIds.add(shopId);
                    FreightModel freightModel = freightDao.getDefaultFreightModel(shopId);
                    if (freightModel != null) {
                        freightModels.add(freightModel);
                    }
                }
            }
        }

        //商家都没有默认运费模板使用平台默认运费模板
        if (freightModels.isEmpty()) {
            freightModels.add(freightDao.getDefaultFreightModel(0L));
            if (freightModels.isEmpty()) {
                //平台也没有则不可达
                return new Reply<>(ResponseStatus.REGION_NOT_REACH);
            }
        }

        //获取重量运费模板和计件运费模板
        List<WeightFreightModel> weightFreightModels = new ArrayList<>();
        List<PieceFreightModel> pieceFreightModels = new ArrayList<>();
        for (FreightModel freightModel : freightModels) {
            freightModel.setRid(rid);
            if (freightModel.getType().equals(ModelType.WEIGHT_MODEL.type())) {
                Reply<WeightFreightModel> reply = freightDao.getWeightFreightModel(freightModel);

                //如果没找到尝试寻找其上级地区
                if (!reply.isOk()) {
                    Long pRid = regionService.getSuperiorRegionId(rid);
                    while (pRid != null && !pRid.equals(-1L)) {
                        freightModel.setRid(pRid);
                        reply = freightDao.getWeightFreightModel(freightModel);
                        if (reply.isOk()) {
                            break;
                        }
                        pRid = regionService.getSuperiorRegionId(pRid);
                    }
                }
                WeightFreightModel weightFreightModel = reply.getData();
                if (null != weightFreightModel) {
                    weightFreightModel.setUnit(freightModel.getUnit());
                    weightFreightModels.add(weightFreightModel);
                }
            } else if (freightModel.getType().equals(ModelType.PIECE_MODEL.type())) {
                Reply<PieceFreightModel> reply = freightDao.getPieceFreightModel(freightModel);
                //如果没找到尝试寻找其上级地区
                if (!reply.isOk()) {
                    Long pRid = regionService.getSuperiorRegionId(rid);
                    while (pRid != null && !pRid.equals(-1L)) {
                        freightModel.setRid(pRid);
                        reply = freightDao.getPieceFreightModel(freightModel);
                        if (reply.isOk()) {
                            break;
                        }
                        pRid = regionService.getSuperiorRegionId(pRid);
                    }
                }
                PieceFreightModel pieceFreightModel = reply.getData();
                if (null != pieceFreightModel) {
                    pieceFreightModel.setUnit(freightModel.getUnit());
                    pieceFreightModels.add(pieceFreightModel);
                }
            }
        }

        if (pieceFreightModels.isEmpty() && weightFreightModels.isEmpty()) {
            return new Reply<>(ResponseStatus.REGION_NOT_REACH);
        }
        //计算
        return new Reply<>(freightCalculate.calculateFreight(purchaseItems, weightFreightModels, pieceFreightModels));
    }

    /**
     * 计算秒杀活动运费
     *
     * @param purchaseItem
     * @param rid
     * @return
     * @author zhibin lan
     */
    public Reply<Long> calActivityFreight(PurchaseItem purchaseItem, Long rid) {

        String key = prifix + delimiter + purchaseItem.getSkuId() + delimiter + rid;
        if (!redisTemplate.hasKey(key)) {
            log.debug("not in redis");
            //redis中未存储则计算运费
            FreightModel freightModel = freightDao.getFreightModelById(goodService.getFreightModelId(purchaseItem.getSkuId())).getData();
            purchaseItem.setWeight(goodService.getGoodsSkuWeightById(purchaseItem.getSkuId()));

            //商品没有模板
            if (null == freightModel) {
                freightModel = freightDao.getDefaultFreightModel(goodService.getShopId(purchaseItem.getSkuId()));
                //商家没有模板
                if (null == freightModel) {
                    freightModel = freightDao.getDefaultFreightModel(0L);
                    //平台没有模板
                    if (null == freightModel) {
                        return new Reply<>(ResponseStatus.REGION_NOT_REACH);
                    }
                }
            }
            freightModel.setRid(rid);

            Long freight = Long.valueOf(0);
            if (freightModel.getType().equals(ModelType.WEIGHT_MODEL.type())) {
                Reply<WeightFreightModel> reply = freightDao.getWeightFreightModel(freightModel);
                if (!reply.isOk()) {
                    return new Reply<>(reply.getResponseStatus());
                }
                WeightFreightModel weightFreightModel = reply.getData();
                if (null != weightFreightModel) {
                    weightFreightModel.setUnit(freightModel.getUnit());
                    freight = freightCalculate.calActivityFreightByWeight(purchaseItem, weightFreightModel);
                }
            } else if (freightModel.getType().equals(ModelType.PIECE_MODEL.type())) {
                Reply<PieceFreightModel> reply = freightDao.getPieceFreightModel(freightModel);
                if (!reply.isOk()) {
                    return new Reply<>(reply.getResponseStatus());
                }
                PieceFreightModel pieceFreightModel = reply.getData();
                if (null != pieceFreightModel) {
                    pieceFreightModel.setUnit(freightModel.getUnit());
                    freight = freightCalculate.calActivityFreightByPiece(purchaseItem, pieceFreightModel);
                }
            }
            redisTemplate.opsForValue().set(key, freight);
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
            return new Reply<>(freight);
        }
        log.debug("in redis");
        return new Reply<>(Long.parseLong((redisTemplate.opsForValue().get(key)).toString()));
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
        if (reply.getResponseStatus() != ResponseStatus.OK) {
            return new Reply<>(reply.getResponseStatus());
        }
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
     * @param shopId
     * @param uShopId
     * @return
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id, Long shopId, Long uShopId) {
        return freightDao.getFreightModelById(id, shopId, uShopId);
    }

    /**
     * 查询指定运费模板
     *
     * @param id
     * @param shopId
     * @return
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id, Long shopId) {
        return freightDao.getFreightModelById(id, shopId);
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
     * @param uShopId
     * @return
     * @author zhibin lan
     */
    @Transactional
    public Reply updateFreightModel(FreightModel freightModel, Long uShopId) {
        return freightDao.updateFreightModel(freightModel, uShopId);
    }

    /**
     * 克隆运费模板
     *
     * @param id      模板id
     * @param shopId  商铺id
     * @param uShopId 用户所属商铺id
     * @return
     * @author zhibin lan
     */
    @Transactional
    public Reply<FreightModel> cloneFreightModel(Long id, Long shopId, Long uShopId) {
        return freightDao.cloneFreightModel(id, shopId, uShopId);
    }

    /**
     * 删除运费模板
     *
     * @param id      模板id
     * @param shopId  商铺id
     * @param uShopId 用户所属商铺id
     * @return
     * @author zhibin lan
     */
    public Reply deleteFreightModel(Long id, Long shopId, Long uShopId) {
        Reply reply = freightDao.deleteFreightModel(id, shopId, uShopId);
        if (!reply.isOk()) {
            return reply;
        }
        freightDao.deleteWeightFreightModel(id);
        freightDao.deletePieceFreightModel(id);
        goodService.deleteGoodsFreightModel(id, shopId);
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 设置默认运费模板
     *
     * @param id      模板id
     * @param shopId  商铺id
     * @param uShopId 用户所属商铺id
     * @return
     * @author zhibin lan
     */
    public Reply defineDefaultFreightModel(Long id, Long shopId, Long uShopId) {
        return freightDao.defineDefaultFreightModel(id, shopId, uShopId);
    }
}

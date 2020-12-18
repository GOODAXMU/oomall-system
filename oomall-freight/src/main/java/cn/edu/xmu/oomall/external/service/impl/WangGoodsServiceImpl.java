package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IGoodService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
@Component
public class WangGoodsServiceImpl implements IGoodService {

    @DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", timeout = 5000, check = false)
    private cn.edu.xmu.goods.client.IGoodsService goodsService;

    @Override
    public Long getShopId(Long skuid) {
        Long shopId = goodsService.getShopIdBySKUId(skuid);
        if (shopId == null) {
            return -1L;
        }
        return shopId;
    }

    @Override
    public Long getGoodsSkuWeightById(Long skuid) {
        return goodsService.getGoodWeightBySku(skuid);
    }

    @Override
    public Long getFreightModelId(Long skuid) {
        return goodsService.getFreightModelIdBySku(skuid);
    }

    @Override
    public Boolean deleteGoodsFreightModel(Long modelId, long shopId) {
        return goodsService.deleteFreightModelIdBySku(modelId, shopId);
    }
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.goods.client.dubbo.ShopDTO;
import cn.edu.xmu.oomall.external.service.IGoodService;

public class WangGoodsServiceImpl implements IGoodService {

    // todo 外部服务未配置
    // @DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", async = true, timeout = 5000)
    private cn.edu.xmu.goods.client.IGoodsService goodsService;

    @Override
    public Long getShopId(Long skuid) {
        return 1L;
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
        return goodsService.deleteFreightModelIdBySku(modelId,shopId);
    }
}

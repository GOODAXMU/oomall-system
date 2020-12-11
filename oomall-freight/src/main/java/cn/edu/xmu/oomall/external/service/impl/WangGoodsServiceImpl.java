package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IGoodService;

public class WangGoodsServiceImpl implements IGoodService {

    // todo 外部服务未配置
    // @DubboReference(version = "${oomall.external.goods-service.version}", cache = "false", async = true, timeout = 5000)
    private cn.edu.xmu.goods.client.IGoodsService goodsService;

    @Override
    public Long getShopId(Long skuid) {
        return null;
    }

    @Override
    public Long getGoodsSkuWeightById(Long skuid) {
        return null;
    }

    @Override
    public Long getFreightModelId(Long skuid) {
        return null;
    }

    @Override
    public Boolean deleteGoodsFreightModel(Long modelId, long shopId) {
        return null;
    }
}

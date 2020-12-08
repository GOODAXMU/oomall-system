package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.vo.Reply;
import cn.xmu.edu.goods.client.IGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class SimulatedGoodsServiceImpl implements IGoodService {

    @DubboReference(version = "${oomall.goods.version}")
    private IGoodsService goodsService;

    @Override
    public Long getShopId(Long skuid) {
        return Long.valueOf(1);
    }

    @Override
    public Long getGoodsSkuWeightById(Long skuid) {
        return Long.valueOf(2);
    }

    @Override
    public Long getFreightModelId(Long skuid) {
        return Long.valueOf(9);
    }

    @Override
    public Reply deleteGoodsFreightModel(Long modelId, long shopId){
        return new Reply(ResponseStatus.OK);
    }
}

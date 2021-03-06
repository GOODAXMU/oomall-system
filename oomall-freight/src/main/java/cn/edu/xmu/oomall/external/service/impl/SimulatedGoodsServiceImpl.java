package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.external.service.IGoodService;
import cn.edu.xmu.oomall.vo.Reply;
import org.springframework.stereotype.Component;

@Component
public class SimulatedGoodsServiceImpl implements IGoodService {

    @Override
    public Long getShopId(Long skuid) {
        return Long.valueOf(1);
    }

    @Override
    public Long getGoodsSkuWeightById(Long skuid) {
        return Long.valueOf(12);
    }

    @Override
    public Long getFreightModelId(Long skuid) {
        return Long.valueOf(11);
    }

    @Override
    public Boolean deleteGoodsFreightModel(Long modelId, long shopId){
        return true;
    }
}

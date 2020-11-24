package cn.edu.xmu.oomall.external.service;


import cn.edu.xmu.oomall.bo.GoodsSku;

/**
 * @author zhibin lan
 * @date 2020-11-23
 */
public interface IGoodService {

    /**
     * 获取sku的商铺id
     *
     * @param skuid
     * @return
     */
    Long getShopId(Long skuid);


    Long getGoodsSkuWeightById(Long skuid);

    /**
     * 获取sku的模板id
     *
     * @param skuid
     * @return
     */
    Long getFreightModelId(Long skuid);
}

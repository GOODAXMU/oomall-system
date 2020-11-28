package cn.edu.xmu.oomall.external.service;


import cn.edu.xmu.oomall.bo.GoodsSku;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.vo.Reply;

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


    /**
     * 获取sku的模板重量
     *
     * @param skuid
     * @return
     */
    Long getGoodsSkuWeightById(Long skuid);

    /**
     * 获取sku的模板id
     *
     * @param skuid
     * @return
     */
    Long getFreightModelId(Long skuid);

    /**
     * 获取sku的模板id
     *
     * @param modelId 运费模板id
     * @param shopId  商铺模板id
     * @return
     */
    Reply deleteGoodsFreightModel(Long modelId, long shopId);
}

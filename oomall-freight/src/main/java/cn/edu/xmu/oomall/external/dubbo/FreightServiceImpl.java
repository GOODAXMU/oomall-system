package cn.edu.xmu.oomall.external.dubbo;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.dto.CalculateFreightRequest;
import cn.edu.xmu.oomall.dto.FreightModelDto;
import cn.edu.xmu.oomall.service.FreightService;
import cn.edu.xmu.oomall.service.IFreightService;
import cn.edu.xmu.oomall.vo.Reply;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-3
 */
@DubboService(version = "${oomall.freight.version}")
public class FreightServiceImpl implements IFreightService {
    @Autowired
    FreightService freightService;

    /**
     * 计算普通订单运费
     *
     * @param calculateFreightRequestList 由skuId和counts数量组成的请求对象
     * @return Long 运费
     */
    @Override
    public Long calFreight(List<CalculateFreightRequest> calculateFreightRequestList, Long rid) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (CalculateFreightRequest calculateFreightRequest : calculateFreightRequestList) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setCount(calculateFreightRequest.getCount());
            purchaseItem.setSkuId(calculateFreightRequest.getSkuId());
        }
        Reply<Long> r = freightService.calFreight(purchaseItems, rid);
        return r.getData() == null ? -1L : r.getData();
    }

    /**
     * 计算秒杀活动订单运费
     *
     * @param calculateFreightRequest 由skuId和counts数量组成的请求对象
     * @return Long 运费
     */
    @Override
    public Long calActivityFreight(CalculateFreightRequest calculateFreightRequest, Long rid) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setCount(calculateFreightRequest.getCount());
        purchaseItem.setSkuId(calculateFreightRequest.getSkuId());
        Reply<Long> r = freightService.calActivityFreight(purchaseItem, rid);
        return r.getData() == null ? -1L : r.getData();
    }

    /**
     * 获取运费模板
     *
     * @param id 运费模板id
     * @return FreightModelDto 运费模板
     */
    @Override
    public FreightModelDto getFreightModel(Long id) {
        FreightModel freightModel = freightService.getFreightModelById(id).getData();
        if (freightModel == null) {
            return null;
        }
        return freightModel.createFreightModelDto();
    }
}

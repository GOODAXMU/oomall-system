package cn.edu.xmu.oomall.external.dubbo;

import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.dto.CalculateFreightRequest;
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
public class freightServiceImpl implements IFreightService {
    @Autowired
    FreightService freightService;

    /**
     * @param calculateFreightRequestList 由skuId和counts数量组成的请求对象
     * @return Long 运费
     */
    public Reply<Long> calculateFreight(List<CalculateFreightRequest> calculateFreightRequestList, Long rid) {
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (CalculateFreightRequest calculateFreightRequest : calculateFreightRequestList) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setCount(calculateFreightRequest.getCount());
            purchaseItem.setSkuId(calculateFreightRequest.getSkuId());
        }
        return freightService.calFreight(purchaseItems, rid);
    }
}

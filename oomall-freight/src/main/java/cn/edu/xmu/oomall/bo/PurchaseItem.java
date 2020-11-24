package cn.edu.xmu.oomall.bo;


import cn.edu.xmu.oomall.vo.FreightCalculateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItem {

    private Long skuId;

    private Integer count;

    private Long weight;

    public PurchaseItem(FreightCalculateRequest freightCalculateRequest){
        skuId = freightCalculateRequest.getSkuId();
        count = freightCalculateRequest.getCount();
    }

}

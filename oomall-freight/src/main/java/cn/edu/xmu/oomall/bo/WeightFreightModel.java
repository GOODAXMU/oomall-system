package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhibin lan
 * @date 2020-11-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightFreightModel {

    private Long id;

    private Long firstWeight;

    private Long firstWeightFreight;

    private Long tenPrice;

    private Long fiftyPrice;

    private Long hundredPrice;

    private Long trihunPrice;

    private Long abovePrice;

    private Long rid;

    /**
     * 构造函数
     * @param weightFreightModelPo Po对象
     */
    public WeightFreightModel(WeightFreightModelPo weightFreightModelPo){
        id = weightFreightModelPo.getId();
        firstWeight = weightFreightModelPo.getFirstWeight();
        firstWeightFreight = weightFreightModelPo.getFirstWeightFreight();
        tenPrice = weightFreightModelPo.getTenPrice();
        fiftyPrice = weightFreightModelPo.getFiftyPrice();
        hundredPrice = weightFreightModelPo.getHundredPrice();
        trihunPrice = weightFreightModelPo.getTrihunPrice();
        abovePrice = weightFreightModelPo.getAbovePrice();
        rid  = weightFreightModelPo.getRegionId();
    }
}

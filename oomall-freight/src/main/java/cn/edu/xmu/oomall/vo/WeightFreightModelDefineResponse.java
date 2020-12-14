package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.WeightFreightModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author yan song
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightFreightModelDefineResponse {
    private Long firstWeight;
    private Long firstWeightPrice;
    private Long tenPrice;
    private Long fiftyPrice;
    private Long hundredPrice;
    private Long triHundredPrice;
    private Long abovePrice;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    
    public WeightFreightModelDefineResponse(WeightFreightModel weightFreightModel){
        firstWeight = weightFreightModel.getFirstWeight();
        firstWeightPrice = weightFreightModel.getFirstWeightFreight();
        tenPrice = weightFreightModel.getTenPrice();
        fiftyPrice = weightFreightModel.getFiftyPrice();
        hundredPrice = weightFreightModel.getHundredPrice();
        triHundredPrice = weightFreightModel.getTrihunPrice();
        abovePrice = weightFreightModel.getAbovePrice();
        gmtCreate = LocalDateTime.now();
        gmtModified = LocalDateTime.now();
    }
}

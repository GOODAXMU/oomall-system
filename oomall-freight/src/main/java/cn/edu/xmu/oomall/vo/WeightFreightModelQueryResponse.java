package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yan song
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightFreightModelQueryResponse {
    private Long id;
    private Long firstWeight;
    private Long firstWeightPrice;
    private Long tenPrice;
    private Long fiftyPrice;
    private Long hundredPrice;
    private Long triHundredPrice;
    private Long abovePrice;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public WeightFreightModelQueryResponse(WeightFreightModelPo weightFreightModelPo){
        id = weightFreightModelPo.getId();
        firstWeight = weightFreightModelPo.getFirstWeight();
        firstWeightPrice = weightFreightModelPo.getFirstWeightFreight();
        tenPrice = weightFreightModelPo.getTenPrice();
        fiftyPrice = weightFreightModelPo.getFiftyPrice();
        hundredPrice = weightFreightModelPo.getHundredPrice();
        triHundredPrice = weightFreightModelPo.getTrihunPrice();
        abovePrice = weightFreightModelPo.getAbovePrice();
        gmtCreate = LocalDateTime.now();
        gmtModified = LocalDateTime.now();
    }

    public List<WeightFreightModelQueryResponse> WeightFreightModelQueryResponseListTransfer(List<WeightFreightModelPo> weightFreightModelPos){
        List<WeightFreightModelQueryResponse> weightFreightModelQueryResponses = new ArrayList<>();
        for(WeightFreightModelPo w : weightFreightModelPos){
            weightFreightModelQueryResponses.add(new WeightFreightModelQueryResponse(w));
        }
        return weightFreightModelQueryResponses;
    }
}

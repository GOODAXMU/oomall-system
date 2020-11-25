package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yan song
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightFreightModelQueryResponse {
    private Integer id;
    private Integer firstWeight;
    private Integer firstWeightPrice;
    private Integer tenPrice;
    private Integer fiftyPrice;
    private Integer hundredPrice;
    private Integer triHundredPrice;
    private Integer abovePrice;
    private String gmtCreate;
    private String gmtModified;
}

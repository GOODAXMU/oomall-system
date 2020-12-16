package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightItemRequest {

    @Min(value = 0)
    private Long regionId;

    @Min(value = 0)
    private Long firstWeight;

    @Min(value = 0)
    private Long firstWeightPrice;

    @Min(value = 0)
    private Long tenPrice;

    @Min(value = 0)
    private Long fiftyPrice;

    @Min(value = 0)
    private Long hundredPrice;

    @Min(value = 0)
    private Long trihunPrice;

    @Min(value = 0)
    private Long abovePrice;

}

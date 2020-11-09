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

    @NotNull
    @Min(value = 0)
    private Long regionId;

    @NotNull
    @Min(value = 0)
    private Integer firstWeight;

    @NotNull
    @Min(value = 0)
    private Long firstWeightPrice;

    @NotNull
    @Min(value = 0)
    private Long tenPrice;

    @NotNull
    @Min(value = 0)
    private Long fiftyPrice;

    @NotNull
    @Min(value = 0)
    private Long hundredPrice;

    @NotNull
    @Min(value = 0)
    private Long trihunPrice;

    @NotNull
    @Min(value = 0)
    private Long abovePrice;

}

package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
/**
 * @author yan song
 * @date 2020-11-9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightFreightModelDefineRequest {
    @NotNull
    @Min(value = 0)
    private Integer firstWeight;

    @NotNull
    @Min(value = 0)
    private Integer firstWeightFreight;

    @NotNull
    @Min(value = 0)
    private Integer tenPrice;

    @NotNull
    @Min(value = 0)
    private Integer fiftyPrice;

    @NotNull
    @Min(value = 0)
    private Integer hundredPrice;

    @NotNull
    @Min(value = 0)
    private Integer trihunPrice;

    @NotNull
    @Min(value = 0)
    private Integer abovePrice;

    @NotNull
    @Min(value = 0)
    private Integer regionId;
}

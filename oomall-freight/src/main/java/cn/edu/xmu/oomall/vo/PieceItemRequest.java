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
public class PieceItemRequest {
    @NotNull
    @Min(value = 0)
    private Long regionId;

    @NotNull
    @Min(value = 0)
    private Integer firstItem;

    @NotNull
    @Min(value = 0)
    private Long firstItemPrice;

    @NotNull
    @Min(value = 0)
    private Integer additionalItems;

    @NotNull
    @Min(value = 0)
    private Long additionalItemsPrice;
}

package cn.edu.xmu.oomall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yan song
 * @date 2020-12-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PieceFreightModelModifyRequest {
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
    private Long additionalItemPrice;

}

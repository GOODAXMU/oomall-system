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
    @Min(value = 0)
    private Long regionId;

    @Min(value = 0)
    private Integer firstItem;

    @Min(value = 0)
    private Long firstItemPrice;

    @Min(value = 0)
    private Integer additionalItems;

    @Min(value = 0)
    private Long additionalItemsPrice;

}

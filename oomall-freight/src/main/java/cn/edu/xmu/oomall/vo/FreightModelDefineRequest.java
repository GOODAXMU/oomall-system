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
public class FreightModelDefineRequest {
    @NotNull
    private String name;

    @NotNull
    @Min(value = 0)
    private Integer type;

    @NotNull
    @Min(value = 0)
    private Long unit;
}

package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@ApiModel(description = "运费模板修改请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelPutRequest {

    @NotNull
    private String name;

    @Min(value = 0)
    private Long unit;
}

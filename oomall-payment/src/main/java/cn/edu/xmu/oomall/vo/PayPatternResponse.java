package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wang Zhizhou
 * create 2020/11/28
 * modified 2020/11/28
 */
@ApiModel(description = "获取所有支付渠道的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPatternResponse {

    private String payPattern;
    private String name;
}

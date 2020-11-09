package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "获得订单状态的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStateGetResponse {
    private Long code;
    private String name;
}

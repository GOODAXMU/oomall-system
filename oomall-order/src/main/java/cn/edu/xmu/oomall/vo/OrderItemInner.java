package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "订单项")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemInner {
    private Long skuId;
    private Integer quantity;
}

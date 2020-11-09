package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "获得订单的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetResponse {
    private Long id;
    private Long customerId;
    private Long shopId;
    private Long pid;
    private Integer orderType;
    private Integer state;
    private Integer subState;
    private Integer gmtCreateTime;
    private Integer originalPrice;
    private Integer discountPrice;
    private Integer freightPrice;
}

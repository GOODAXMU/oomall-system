package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan, Jianheng HUANG
 * @date 2020-11-09
 */
@ApiModel(description = "获取订单简略内容的响应对象")
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
    private Long gmtCreateTime;
    private Long originalPrice;
    private Long discountPrice;
    private Long freightPrice;
}

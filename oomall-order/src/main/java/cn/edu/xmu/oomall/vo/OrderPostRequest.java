package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "订单创建的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostRequest {
    private OrderItemInner orderItems;
    private String consignee;
    private Long regionId;
    private String address;
    private Integer mobile;
    private String message;
    private Long couponId;
    private Long couponActivityId;
    private Long presaleId;
    private Long grouponId;
}

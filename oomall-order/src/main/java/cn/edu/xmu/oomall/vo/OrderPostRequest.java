package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-09
 */
@ApiModel(description = "订单创建的请求对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostRequest {

    @ApiModel(description = "订单项")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private Long skuId;
        private Integer quantity;
    }

    private List<OrderItem> orderItems;
    private String consignee;
    private Long regionId;
    private String address;
    private String mobile;
    private String message;
    private Long couponId;
    /**
     * 一个订单只能用一个优惠活动？
     */
    private Long couponActivityId;
    private Long presaleId;
    private Long grouponId;
}

package cn.edu.xmu.oomall.vo;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-09
 * modified by xincong yao, 2020-12-3
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
        @NotNull
        @Min(value = 1)
        private Long skuId;
        @NotNull
        @Min(value = 1)
        private Integer quantity;
        private Long couponActId;
    }

    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderItem> orderItems;
    @NotEmpty
    private String consignee;
    @NotNull
    @Min(value = 1)
    private Long regionId;
    @NotEmpty
    private String address;
    @NotEmpty
    private String mobile;
    private String message;
    private Long couponId;
    private Long presaleId;
    private Long grouponId;
}

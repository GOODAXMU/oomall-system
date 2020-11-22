package cn.edu.xmu.oomall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhibin lan, Jianheng HUANG
 * @date 2020-11-09
 */
@ApiModel(description = "获取订单的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetResponse {
    private Long id;
    private Customer customer;
    private Shop shop;
    private Long pid;
    private Integer orderType;
    private Integer state;
    private Integer subState;
    private Long gmtCreateTime;

    private Long originalPrice;
    private Long discountPrice;
    private Long freightPrice;

    private String message;
    private String consignee;

    private Long couponId;

    private Long grouponId;

    private List<OrderItem> orderItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Customer {
        private Long id;
        private String userName;
        private String realName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Shop {
        private Long id;
        private String name;
        private String gmtCreateTime;
        private String gmtModiTime;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class OrderItem {
        private Long skuId;
        private Long orderId;
        private String name;
        private Integer quantity;
        private Long price;
        private Long discount;
        private Long couponActivityId;
        private Long beSharedId;
    }
}

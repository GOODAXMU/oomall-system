package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.Order;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 * <p>
 * modified by: xincong yao, date: 2020-11-25
 * modified by: Jianheng HUANG, date: 2020-12-17
 */
@ApiModel(description = "获取订单详细内容的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailGetResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Customer {
        private Long id;
        private String userName;
        private String name;

        static Customer toCustomer(cn.edu.xmu.oomall.bo.Customer c) {
            if (c == null) {
                return null;
            }
            Customer customer = new Customer();
            customer.setId(c.getId());
            customer.setName(c.getRealName());
            customer.setUserName(c.getUserName());
            return customer;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Shop {
        private Long id;
        private String name;
        private Integer state;
        private String gmtCreate;
        private String gmtModified;

        static Shop toShop(cn.edu.xmu.oomall.bo.Shop s) {
            if (s == null) {
                return null;
            }
            Shop shop = new Shop();
            shop.setId(s.getId());
            shop.setName(s.getName());
            shop.setState(s.getState());
            shop.setGmtCreate(s.getGmtCreateTime());
            shop.setGmtModified(s.getGmtModiTime());
            return shop;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        private Long skuId;
        private Long orderId;
        private String name;
        private Integer quantity;
        private Long price;
        private Long discount;
        private Long couponActId;
        private Long beSharedId;

        static OrderItem toOrderItem(cn.edu.xmu.oomall.bo.OrderItem oi) {
            if (oi == null) {
                return null;
            }
            OrderItem r = new OrderItem();
            r.setSkuId(oi.getSkuId());
            r.setOrderId(oi.getOrderId());
            r.setName(oi.getName());
            r.setQuantity(oi.getQuantity());
            r.setPrice(oi.getPrice());
            r.setDiscount(oi.getDiscount());
            r.setCouponActId(oi.getCouponActivityId());
            r.setBeSharedId(oi.getBeShareId());
            return r;
        }
    }

    private Long id;
    private String orderSn;
    private Customer customer;
    private Shop shop;
    private Long pid;
    private Integer orderType;
    private Integer state;
    private Integer subState;

    private String gmtCreate;
    private String gmtModified;
    private String confirmTime;

    private Long originPrice;
    private Long discountPrice;
    private Long freightPrice;
    private Long rebateNum;

    private String message;
    private Long regionId;
    private String address;
    private String mobile;
    private String consignee;

    private Long couponId;
    private Long grouponId;
    private Long presaleId;
    private String shipmentSn;

    private List<OrderItem> orderItems;

    public void setAll(Order o) {
        if (o == null) {
            return;
        }
        this.id = o.getId();
        this.orderSn = o.getOrderSn();
        this.customer = Customer.toCustomer(o.getCustomer());
        this.shop = Shop.toShop(o.getShop());
        this.pid = o.getPid();
        this.orderType = o.getOrderType();
        this.state = o.getState();
        this.subState = o.getSubState();

        if (o.getGmtCreated() != null) {
            this.gmtCreate = o.getGmtCreated().toString();
        }
        if (o.getGmtModified() != null) {
            this.gmtModified = o.getGmtModified().toString();
        }
        if (o.getConfirmTime() != null) {
            this.confirmTime = o.getConfirmTime().toString();
        }

        this.originPrice = o.getOriginPrice();
        this.discountPrice = o.getDiscountPrice();
        this.freightPrice = o.getFreightPrice();
        this.message = o.getMessage();
        this.regionId = o.getRegionId();
        this.address = o.getAddress();
        this.mobile = o.getMobile();
        this.consignee = o.getConsignee();
        this.couponId = o.getCouponId();
        this.grouponId = o.getGrouponId();
        this.presaleId = o.getPresaleId();
        this.shipmentSn = o.getShipmentSn();
        this.orderSn = o.getOrderSn();

        List<OrderItem> orderItems = new ArrayList<>();
        for (cn.edu.xmu.oomall.bo.OrderItem bo : o.getOrderItems()) {
            orderItems.add(OrderItem.toOrderItem(bo));
        }
        this.orderItems = orderItems;
    }

}

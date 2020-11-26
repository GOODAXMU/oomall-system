package cn.edu.xmu.oomall.vo;

import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.util.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhibin lan, Jianheng HUANG
 * @date 2020-11-09
 */
@ApiModel(description = "获取订单的响应对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryGetResponse {

    private Integer page;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    private List<OrderSummary> list;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class OrderSummary {
        private Long id;
        private Long customerId;
        private Long shopId;
        private Long pid;
        private Integer orderType;
        private Integer state;
        private Integer subState;
        private Long gmtCreate;

        private Long originalPrice;
        private Long discountPrice;
        private Long freightPrice;
    }

    public void setPageInfo(PageInfo pageInfo) {
        if (pageInfo == null) {
            return;
        }
        this.page = pageInfo.getPage();
        this.pageSize = pageInfo.getPageSize();
        this.pages = pageInfo.getPages();
        this.total = pageInfo.getTotal();
    }

    public void setSummaryList(List<Order> orders) {
        List<OrderSummary> summaries = new ArrayList<>();
        for (Order o : orders) {
            OrderSummary summary = new OrderSummary();
            summary.setId(o.getId());
            summary.setCustomerId(o.getCustomer().getId());
            summary.setShopId(o.getShop().getId());
            summary.setPid(o.getPid());
            summary.setOrderType(o.getOrderType());
            summary.setState(o.getState());
            summary.setSubState(o.getSubState());
            summary.setGmtCreate(o.getGmtCreated().toEpochSecond(ZoneOffset.UTC));
            summary.setOriginalPrice(o.getOriginPrice());
            summary.setDiscountPrice(o.getDiscountPrice());
            summary.setFreightPrice(o.getFreightPrice());

            summaries.add(summary);
        }
        this.list = summaries;
    }

}

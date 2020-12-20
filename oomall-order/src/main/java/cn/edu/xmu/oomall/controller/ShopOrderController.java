package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.annotation.Audit;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.entity.OrderPo;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.service.ShopOrderService;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jianheng HUANG
 * @date 2020-11-9
 * @modified 2020-12-12
 */
@Api(value = "供店家访问的订单api")
@Validated
@RestController
@RequestMapping("/shops")
public class ShopOrderController {

    @Autowired
    private ShopOrderService shopOrderService;

    @Audit
    @ApiOperation(value = "店家查询商户所有订单 (概要)。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "shopId", value = "商户id (店员只能查询本商铺)", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "customerId", value = "查询的购买者用户id", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderSn", value = "订单编号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "beginTime", value = "开始时间", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "页码", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页数目", required = false),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{shopId}/orders", produces = "application/json;charset=UTF-8")
    public Reply<OrderSummaryGetResponse> getAllShopOrders(
            @PathVariable @Min(value = 1) Long shopId,
            @RequestParam(required = false) @Min(value = 1) Long customerId,
            @RequestParam(required = false) String orderSn,
            @RequestParam(required = false) String beginTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) throws OrderModuleException {
        PageInfo pageInfo = new PageInfo(page, pageSize);

        if (beginTime != null) {
            beginTime = beginTime.replace(' ', 'T');
        }

        if (endTime != null) {
            endTime = endTime.replace(' ', 'T');
        }

        if ((beginTime != null && !beginTime.contains("T"))
                || (endTime != null && !endTime.contains("T"))) {
            throw new OrderModuleException(HttpStatus.BAD_REQUEST, cn.edu.xmu.oomall.constant.ResponseStatus.FIELD_INVALID);
        }

        Reply<Object> r = shopOrderService.getShopOrders(shopId, customerId, orderSn,
                beginTime == null ? null : LocalDateTime.parse(beginTime),
                endTime == null ? null : LocalDateTime.parse(endTime),
                pageInfo, false);

        if (!(r.getData() instanceof List)) {
            return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
        }

        OrderSummaryGetResponse vo = new OrderSummaryGetResponse();
        vo.setSummaryList((List<Order>) (r.getData()));
        vo.setPageInfo(pageInfo);

        return new Reply<>(vo);
    }

    @Audit
    @ApiOperation(value = "店家修改订单 (留言)。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "shopId", value = "商户id (店员只能查询本商铺)", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "指定订单号", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "ShopOrderMessageAddRequest", name = "message", value = "操作字段 (状态)", required = true),

    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
    public Reply<Object> addShopOrderMessage(
            @PathVariable @Min(value = 1) Long shopId,
            @PathVariable @Min(value = 1) Long id,
            @RequestBody @Valid ShopOrderMessageAddRequest orderInfo) {
        String message = orderInfo.getMessage();
        return shopOrderService.addShopOrderMessage(shopId, id, message);
    }

    @Audit
    @ApiOperation(value = "店家查询店内订单完整信息（普通，团购，预售）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "shopId", value = "商户id (店员只能操作本商铺)", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "订单id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Reply<OrderDetailGetResponse> getShopOrderDetails(
            @PathVariable @Min(value = 1) Long shopId,
            @PathVariable @Min(value = 1) Long id) {
        Reply<Order> r = shopOrderService.getShopOrderById(shopId, id);
        Order o = r.getData();
        if (o == null) {
            return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
        }

        OrderDetailGetResponse vo = new OrderDetailGetResponse();
        vo.setAll(o);
        return new Reply<>(vo);
    }

    @Audit
    @ApiOperation(value = "管理员取消本店铺订单。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "shopId", value = "商户id (店员只能操作本商铺)", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "订单id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/{shopId}/orders/{id}", produces = "application/json;charset=UTF-8")
    public Reply<Object> cancelShopOrder(
            @PathVariable @Min(value = 1) Long shopId,
            @PathVariable @Min(value = 1) Long id) {
        return shopOrderService.cancelShopOrder(shopId, id);
    }

    @Audit
    @ApiOperation(value = "店家对订单标记发货。")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "shopId", value = "商户id (店员只能操作本商铺)", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "订单id", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "ShopOrderDeliverPutRequest", name = "body", value = "指定发货资讯", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(value = "/{shopId}/orders/{id}/deliver", produces = "application/json;charset=UTF-8")
    public Reply<Object> markShopOrderDeliver(
            @PathVariable @Min(value = 1) Long shopId,
            @PathVariable @Min(value = 1) Long id,
            @RequestBody @Valid ShopOrderDeliverPutRequest body) {
        String sn = body.getFreightSn();
        return shopOrderService.markShopOrderDelivered(shopId, id, sn);
    }

}

package cn.edu.xmu.oomall.controller;


import cn.edu.xmu.oomall.vo.*;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-9
 */
@Api(value = "供买家访问的订单api")
@Validated
@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    @ApiOperation(value = "获得订单所有状态",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/states")
    public List<OrderStateGetResponse> getAllOrdersStates(){
        return null;
    }

    @ApiOperation(value = "买家查询名下订单（概要）",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderSn", value = "订单编号", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "state", value = "订单状态", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "页码", required = false),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "每页数目", required = false),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("")
    public List<OrderGetResponse> getAllOrders(
            @RequestParam(required = false) String orderSn, @RequestParam(required = false) Integer state,
            @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize){
        return null;
    }

    @ApiOperation(value = "买家申请建立订单（普通，团购，预售）",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "OrderCreatePostRequest", name = "orderInfo", value = "指定新订单的资料", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("")
    public OrderDetailGetResponse createOrders(@RequestParam(required = true) OrderPostRequest orderInfo){
        return null;
    }

    @ApiOperation(value = "买家查询订单完整信息（普通，团购，预售）",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "订单id", value = "id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("{id}")
    public OrderDetailGetResponse createOrders(@RequestParam(required = true) Long id){
        return null;
    }

    @ApiOperation(value = "买家修改本人名下订单",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "OrderUpdatePutRequest", name = "userReceiveInfo", value = "操作字段 (状态)", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("{id}")
    public Object updateSelfOrder(@RequestParam(required = true) OrderPutRequest userReceiveInfo){
        return null;
    }

    @ApiOperation(value = "买家修改本人名下订单",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "订单id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("{id}")
    public Object deleteSelfOrder(@RequestParam(required = true) Long id){
        return null;
    }

    @ApiOperation(value = "买家标记确认收货",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "指定需要返回订单概要信息的订单号", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("{id}/confirm")
    public Object confirmOrder(@RequestParam(required = true) Long id){
        return null;
    }

    @ApiOperation(value = "买家将团购订单转为普通订单",  produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "订单id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("{id}/groupon-normal")
    public Object transferOrder(@RequestParam(required = true) Long id){
        return null;
    }
}

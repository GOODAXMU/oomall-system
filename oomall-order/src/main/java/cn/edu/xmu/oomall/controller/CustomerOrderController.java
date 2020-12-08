package cn.edu.xmu.oomall.controller;


import cn.edu.xmu.oomall.annotation.Audit;
import cn.edu.xmu.oomall.annotation.LoginUser;
import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.constant.OrderStatus;
import cn.edu.xmu.oomall.service.IOrderService;
import cn.edu.xmu.oomall.service.CustomerOrderService;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-9
 */
@Api(value = "供买家访问的订单api")
@Validated
@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

	@Autowired
	private CustomerOrderService customerOrderService;

	@Resource(name = "normalOrderServiceImpl")
	private IOrderService normalOrderService;
	@Resource(name = "grouponOrderServiceImpl")
	private IOrderService grouponOrderService;
	@Resource(name = "seckillOrderServiceImpl")
	private IOrderService seckillOrderService;
	@Resource(name = "presaleOrderServiceImpl")
	private IOrderService presaleOrderService;


	@ApiOperation(value = "获得订单所有状态", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/states", produces = "application/json;charset=UTF-8")
	public Reply<List<OrderStateGetResponse>> getAllOrdersStates() {
		List<OrderStateGetResponse> r = new ArrayList<>();
		for (OrderStatus os : OrderStatus.values()) {
			r.add(new OrderStateGetResponse(os.value(), os.getDesc()));
		}

		return new Reply<>(r);
	}


	@Audit
	@ApiOperation(value = "买家查询名下订单（概要）", produces = "application/json;charset=UTF-8")
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
	@GetMapping(value = "", produces = "application/json;charset=UTF-8")
	public Reply<OrderSummaryGetResponse> getAllOrders(
			@RequestParam(required = false) String orderSn,
			@RequestParam(required = false) Integer state,
			@RequestParam(required = false) String beginTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "20") Integer pageSize,
			@LoginUser Long customerId) {
		PageInfo pageInfo = new PageInfo(page, pageSize);
		List<Order> os = customerOrderService.getOrders(
				customerId, orderSn, state,
				beginTime == null ? null : LocalDateTime.parse(beginTime),
				endTime == null ? null : LocalDateTime.parse(endTime),
				pageInfo, false).getData();

		OrderSummaryGetResponse vo = new OrderSummaryGetResponse();
		vo.setSummaryList(os);
		vo.setPageInfo(pageInfo);

		return new Reply<>(vo);
	}


	@Audit
	@ApiOperation(value = "买家申请建立订单（普通，团购，预售）", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "OrderCreatePostRequest", name = "orderInfo", value = "指定新订单的资料", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "", produces = "application/json;charset=UTF-8")
	public Reply<OrderDetailGetResponse> createOrder(
			@RequestBody @Valid OrderPostRequest request,
			@LoginUser Long customerId
	) throws InterruptedException, ExecutionException {
		Order order = Order.toOrder(request);
		order.getCustomer().setId(customerId);

		IOrderService orderService = getOrderService(order);

		Reply<Order> reply = orderService.createOrder(order);
		if (!reply.isOk()) {
			return new Reply<>(reply.getHttpStatus(), reply.getResponseStatus());
		}

		Order o = reply.getData();
		OrderDetailGetResponse vo = new OrderDetailGetResponse();
		vo.setAll(o);
		return new Reply<>(vo);
	}


	@Audit
	@ApiOperation(value = "买家查询订单完整信息（普通，团购，预售）", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "path", dataType = "int", name = "订单id", value = "id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "{id}", produces = "application/json;charset=UTF-8")
	public Reply<OrderDetailGetResponse> getOrderDetails(
			@PathVariable @NotNull @Min(value = 1) Long id,
			@LoginUser Long customerId) {
		Reply<Order> r = customerOrderService.getOrderByIdAndCustomerId(id, customerId);
		Order o = r.getData();
		if (o == null) {
			return new Reply<>(r.getHttpStatus(), r.getResponseStatus());
		}

		OrderDetailGetResponse vo = new OrderDetailGetResponse();
		vo.setAll(o);

		return new Reply<>(vo);
	}


	@Audit
	@ApiOperation(value = "买家修改本人名下订单", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "OrderUpdatePutRequest", name = "userReceiveInfo", value = "操作字段 (状态)", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "{id}", produces = "application/json;charset=UTF-8")
	public Reply<Object> updateSelfOrder(
			@RequestBody @Valid OrderPutRequest request,
			@PathVariable @NotNull @Min(value = 1) Long id,
			@LoginUser Long customerId) {
		Order o = Order.toOrder(request);
		o.getCustomer().setId(customerId);
		o.setId(id);
		return customerOrderService.updateOrderDeliveryInformation(o);
	}


	@Audit
	@ApiOperation(value = "买家删除本人名下订单", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping(value = "{id}", produces = "application/json;charset=UTF-8")
	public Object deleteSelfOrder(
			@PathVariable @NotNull @Min(value = 1) Long id,
			@LoginUser Long customerId) {
		return customerOrderService.deleteOrCancelSelfOrder(id, customerId);
	}


	@Audit
	@ApiOperation(value = "买家标记确认收货", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "指定需要返回订单概要信息的订单号", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping(value = "{id}/confirm", produces = "application/json;charset=UTF-8")
	public Reply<Object> confirmOrder(
			@PathVariable @NotNull @Min(value = 1) Long id,
			@LoginUser Long customerId) {
		return customerOrderService.confirmOrder(id, customerId);
	}


	@Audit
	@ApiOperation(value = "买家将团购订单转为普通订单", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "Token", required = true),
			@ApiImplicitParam(paramType = "body", dataType = "int", name = "id", value = "订单id", required = true)
	})
	@ApiResponses({
			@ApiResponse(code = 0, message = "成功"),
	})
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping(value = "{id}/groupon-normal", produces = "application/json;charset=UTF-8")
	public Reply<Object> transferOrder(
			@PathVariable @NotNull @Min(value = 1) Long id,
			@LoginUser Long customerId) {
		return customerOrderService.groupon2Normal(id, customerId);
	}

	/**
	 * 如果是秒杀活动, 为order设置seckillId
	 * @param order
	 * @return
	 */
	private IOrderService getOrderService(Order order) {
		int size = order.getOrderItems().size();
		Long skuId = order.getOrderItems().get(0).getSkuId();

		if (order.getPresaleId() != null && size == 1) {
			if (customerOrderService.getPreSaleId(skuId) != null) {
				return presaleOrderService;
			}
		}
		if (order.getGrouponId() != null && size == 1) {
			if (customerOrderService.getGrouponId(skuId) != null) {
				return grouponOrderService;
			}
		}
		if (size == 1 && order.getOrderItems().get(0).getQuantity() == 1) {
			Long seckillId = customerOrderService.getSeckillId(skuId);
			if (seckillId != null && seckillId >= 0) {
				order.setSeckillId(seckillId);
				return seckillOrderService;
			}
		}

		return normalOrderService;
	}
}

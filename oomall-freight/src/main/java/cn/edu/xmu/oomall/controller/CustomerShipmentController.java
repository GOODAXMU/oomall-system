package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.vo.FreightCalculateRequest;
import cn.edu.xmu.oomall.vo.FreightCalculateResponse;
import cn.edu.xmu.oomall.vo.FreightModelSummaryGetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-10-26
 */
@Api(value = "供买家访问的运费api")
@Validated
@RestController
@RequestMapping("/freight-models")
public class CustomerShipmentController {

	@ApiOperation(value = "计算一批商品的运费")
	@PostMapping(value = "/{id}/price", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.CREATED)
	public List<FreightCalculateResponse> calculateFreight(
			@Valid @RequestBody List<FreightCalculateRequest> items,
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}

	@ApiOperation(value = "获取运费模板概要")
	@GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public FreightModelSummaryGetResponse getFreightModelSummary(
			@NotNull @Min(value = 0) @PathVariable Long id) {
		return null;
	}

}

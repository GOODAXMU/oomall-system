package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.annotation.Audit;
import cn.edu.xmu.oomall.annotation.Depart;
import cn.edu.xmu.oomall.annotation.LoginUser;
import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.service.FreightService;
import cn.edu.xmu.oomall.vo.FreightCalculateRequest;
import cn.edu.xmu.oomall.vo.FreightModelSummaryGetResponse;
import cn.edu.xmu.oomall.vo.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-10-26
 */
@Api(value = "供买家访问的运费api")
@Validated
@RestController
@Slf4j
public class CustomerShipmentController {

    @Autowired
    private FreightService freightService;

    @ApiOperation(value = "计算一批商品的运费")
    @PostMapping(value = "/region/{rid}/price", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reply<Long> calculateFreight(
            @Valid @RequestBody List<FreightCalculateRequest> items,
            @NotNull @Min(value = 0) @PathVariable Long rid) {

        List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
        for (FreightCalculateRequest item : items) {
            purchaseItems.add(new PurchaseItem(item));
        }
        return freightService.calFreight(purchaseItems, rid);
    }

    @ApiOperation(value = "获取运费模板概要")
    @GetMapping(value = "/shops/{shopId}/freightmodels/{id}", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    @Audit
    public Reply<FreightModelSummaryGetResponse> getFreightModelSummary(
            @NotNull @Min(value = 0) @PathVariable Long id,
            @PathVariable Long shopId,@Depart Long uShopId) {
        Reply<FreightModel> freightModelReply = freightService.getFreightModelById(id, shopId,uShopId);
        if (!freightModelReply.isOk()) {
            return new Reply<>(freightModelReply.getResponseStatus());
        }
        return new Reply<>(freightModelReply.getData().createSummaryGetResponse());
    }

}

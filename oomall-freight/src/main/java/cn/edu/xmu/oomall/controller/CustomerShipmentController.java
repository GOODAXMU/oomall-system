package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PurchaseItem;
import cn.edu.xmu.oomall.service.FreightService;
import cn.edu.xmu.oomall.vo.FreightCalculateRequest;
import cn.edu.xmu.oomall.vo.Reply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        return new Reply<>(freightService.calFreight(purchaseItems, rid).getData());
    }

    @ApiOperation(value = "获取运费模板概要")
    @GetMapping(value = "freightmodels/{id}", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Reply getFreightModelSummary(
            @NotNull @Min(value = 0) @PathVariable Long id) {
        Reply<FreightModel> freightModelReply = freightService.getFreightModelById(id);
        if (!freightModelReply.isOk())
            return freightModelReply;
        return new Reply<>(freightModelReply.getData().createSummaryGetResponse());
    }

}

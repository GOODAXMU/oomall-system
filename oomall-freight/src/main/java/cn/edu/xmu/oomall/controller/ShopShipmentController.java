package cn.edu.xmu.oomall.controller;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.service.FreightService;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhibin lan
 * @date 2020-11-9
 */
@RestController
@Slf4j
public class ShopShipmentController {

    @Autowired
    private FreightService freightService;

    @ApiOperation(value = "管理员定义店铺运费模板")
    @PostMapping(value = "/shops/{id}/freightmodels", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reply<FreightModelDefineResponse> defineFreightModel(
            @Valid @RequestBody FreightModelDefineRequest freightModelInfo,
            @NotNull @Min(value = 0) @PathVariable Long id) {
        FreightModel freightModel = new FreightModel(freightModelInfo);
        Reply<FreightModel> reply = freightService.defineFreightModel(freightModel);
        if (!reply.isOk())
            return new Reply<>(reply.getResponseStatus());
        return new Reply(reply.getData().createDefineResponse());
    }


    @ApiOperation(value = "获得店铺中商品的运费模板")
    @GetMapping(value = "/shops/{id}/freightmodels", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Reply<FreightModelGetResponse> getFreightModels(
            @NotNull @Min(value = 0) @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        log.debug("getFreightModel: page = " + page + "  pageSize =" + pageSize);
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        PageInfo pageInfo = new PageInfo(page, pageSize);
        Reply<List<FreightModel>> listReply = freightService.findAllFreightModels(pageInfo, name, id);
        FreightModelGetResponse freightModelGetResponse = new FreightModelGetResponse(pageInfo, listReply.getData());
        return new Reply(freightModelGetResponse);
    }

    @ApiOperation(value = "管理员克隆店铺的运费模板")
    @PostMapping(value = "/shops/{shopId}/freightmodels/{id}/clone", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Reply<FreightModelCloneResponse> cloneFreightModel(
            @NotNull @Min(value = 0) @PathVariable Long id,
            @NotNull @Min(value = 0) @PathVariable Long shopId) {
        Reply<FreightModel> reply = freightService.cloneFreightModel(id, shopId);
        if (!reply.isOk())
            return new Reply<>(reply.getResponseStatus());
        return new Reply<FreightModelCloneResponse>(reply.getData().createCloneResponse());
    }

    @ApiOperation(value = "管理员修改店铺的运费模板")
    @PutMapping(value = "/shops/{shopId}/freightmodels/{id}", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Reply modifyFreightModel(
            @NotNull @Min(value = 0) @PathVariable Long id,
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @Valid @RequestBody FreightModelPutRequest freightModelPutRequest) {
        FreightModel freightModel = new FreightModel(freightModelPutRequest);
        freightModel.setShopId(shopId);
        freightModel.setId(id);
        return freightService.updateFreightModel(freightModel);
    }

    @ApiOperation(value = "删除运费模板，需同步删除与商品的")
    @DeleteMapping(value = "/shops/{shopId}/freightmodels/{id}", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Object deleteFreightModel(
            @NotNull @Min(value = 0) @PathVariable Long id,
            @NotNull @Min(value = 0) @PathVariable Long shopId) {
        return freightService.deleteFreightModel(id, shopId);
    }

    @ApiOperation(value = "店家或管理员为商铺定义默认运费模板")
    @PostMapping(value = "/shops/{shopId}/freightmodels/{id}/default", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object defineDefaultFreightModel(
            @NotNull @Min(value = 0) @PathVariable Long id, @PathVariable Long shopId) {
        return freightService.defineDefaultFreightModel(id, shopId);
    }

    @ApiOperation(value = "管理员定义重量模板明细")
    @PostMapping(value = "/shops/{shopId}/freightmodels/{id}/weightItems", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public WeightFreightModelDefineResponse defineWeightFreightModelInfo(
            @Valid @RequestBody WeightFreightModelDefineRequest freightModelInfo,
            @NotNull @Min(value = 0) @PathVariable Long id, @PathVariable Long shopId) {
        return null;
    }

    @ApiOperation(value = "店家或管理员查询某个运费模板的明细")
    @GetMapping(value = "/shops/{shopId}/freightmodels/{id}/weightItems", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public WeightFreightModelQueryResponse weightFreightModelInfoQuery(
            @NotNull @Min(value = 0) @PathVariable Long id, @PathVariable Long shopId) {
        return null;
    }

    @ApiOperation(value = "管理员定义件数模板明细")
    @PostMapping(value = "/shops/{shopId}/freightmodels/{id}/pieceItems", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PieceItemResponse createPieceItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id,
            @Valid @RequestBody PieceItemRequest info) {
        return null;
    }

    @ApiOperation(value = "管理员查询件数模板明细")
    @GetMapping(value = "/shops/{shopId}/freightmodels/{id}/pieceItems", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public PieceItemResponse getPieceItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id) {
        return null;
    }

    @ApiOperation(value = "管理员修改件数模板明细")
    @PutMapping(value = "/shops/{shopId}/pieceItems/{id}/", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Object modifyPieceItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id,
            @Valid @RequestBody PieceItemRequest info) {
        return null;
    }

    @ApiOperation(value = "管理员删除件数模板明细")
    @DeleteMapping(value = "/shops/{shopId}/pieceItems/{id}/", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Object deletePieceItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id) {
        return null;
    }

    @ApiOperation(value = "管理员修改重量模板明细")
    @PutMapping(value = "/shops/{shopId}/weightItems/{id}/", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Object modifyWeightItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id,
            @Valid @RequestBody WeightItemRequest info) {
        return null;
    }

    @ApiOperation(value = "管理员删除重量模板明细")
    @DeleteMapping(value = "/shops/{shopId}/weightItems/{id}/", produces = "application/json;charset=UTF-8")
    @ResponseStatus(value = HttpStatus.OK)
    public Object deleteWeightItemsModel(
            @NotNull @Min(value = 0) @PathVariable Long shopId,
            @NotNull @Min(value = 0) @PathVariable Long id) {
        return null;
    }
}

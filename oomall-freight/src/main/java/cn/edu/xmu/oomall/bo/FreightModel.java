package cn.edu.xmu.oomall.bo;


import cn.edu.xmu.oomall.dto.FreightModelDto;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.vo.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author zhibin lan
 * @date 2020-11-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModel {

    private Long id;

    private Long shopId;

    private Long rid;

    private String name;

    private Integer type;

    private Integer isDefault;

    private Long unit;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 模板类型
     */
    public enum ModelType {

        //计重
        WEIGHT_MODEL(0),

        //计件
        PIECE_MODEL(1);

        /**
         * 类型
         */
        private final Integer type;

        public Integer type() {
            return this.type;
        }

        ModelType(Integer type) {
            this.type = type;
        }
    }

    /**
     * 构造函数
     *
     * @param freightModelPo Po对象
     */
    public FreightModel(FreightModelPo freightModelPo) {
        id = freightModelPo.getId();
        name = freightModelPo.getName();
        type = freightModelPo.getType();
        shopId = freightModelPo.getShopId();
        unit = freightModelPo.getWeightUnit();
        isDefault = freightModelPo.getDefaultModel();
        gmtCreate = freightModelPo.getGmtCreate();
        gmtModified = freightModelPo.getGmtModified();
    }

    /**
     * 构造函数
     *
     * @param freightModelDefineRequest Vo对象
     */
    public FreightModel(FreightModelDefineRequest freightModelDefineRequest) {
        name = freightModelDefineRequest.getName();
        type = freightModelDefineRequest.getType();
        unit = freightModelDefineRequest.getUnit();
    }

    /**
     * 构造函数
     *
     * @param freightModelPutRequest Vo对象
     */
    public FreightModel(FreightModelPutRequest freightModelPutRequest) {
        name = freightModelPutRequest.getName();
        unit = freightModelPutRequest.getUnit();
    }

    /**
     * 生成FreightModelPo对象
     *
     * @return FreightModelPo
     */
    public FreightModelPo createPo() {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setId(id);
        freightModelPo.setShopId(shopId);
        freightModelPo.setDefaultModel(isDefault);
        freightModelPo.setGmtCreate(gmtCreate);
        freightModelPo.setGmtModified(gmtModified);
        freightModelPo.setName(name);
        freightModelPo.setType(type);
        freightModelPo.setWeightUnit(unit);
        return freightModelPo;
    }

    /**
     * 生成FreightModelVo对象
     *
     * @return FreightModelPo
     */
    public FreightModelDefineResponse createDefineResponse() {
        FreightModelDefineResponse freightModelDefineResponse = new FreightModelDefineResponse();
        if (isDefault == null) {
            freightModelDefineResponse.setDefaultModel(false);
        } else {
            freightModelDefineResponse.setDefaultModel(isDefault == 1 ? true : false);
        }
        freightModelDefineResponse.setGmtCreate(gmtCreate.toString());
        freightModelDefineResponse.setGmtModified(gmtModified.toString());
        freightModelDefineResponse.setUnit(unit);
        freightModelDefineResponse.setId(id);
        freightModelDefineResponse.setName(name);
        freightModelDefineResponse.setType(type);
        return freightModelDefineResponse;
    }

    public FreightModelSummaryGetResponse createSummaryGetResponse() {
        FreightModelSummaryGetResponse freightModelSummaryGetResponse = new FreightModelSummaryGetResponse();
        freightModelSummaryGetResponse.setDefaultModel(isDefault == null? false : isDefault == 1);
        freightModelSummaryGetResponse.setGmtCreate(gmtCreate.toString());
        freightModelSummaryGetResponse.setGmtModified(gmtModified.toString());
        freightModelSummaryGetResponse.setId(id);
        freightModelSummaryGetResponse.setName(name);
        freightModelSummaryGetResponse.setUnit(unit);
        freightModelSummaryGetResponse.setType(type);
        return freightModelSummaryGetResponse;
    }

    public FreightModelCloneResponse createCloneResponse() {
        FreightModelCloneResponse freightModelCloneResponse = new FreightModelCloneResponse();
        freightModelCloneResponse.setDefaultModel(isDefault == 1);
        freightModelCloneResponse.setGmtCreate(gmtCreate.toString());
        freightModelCloneResponse.setGmtModified(gmtModified.toString());
        freightModelCloneResponse.setId(id);
        freightModelCloneResponse.setName(name);
        freightModelCloneResponse.setType(type);
        freightModelCloneResponse.setUnit(unit);
        return freightModelCloneResponse;
    }

    public FreightModelDto createFreightModelDto() {
        FreightModelDto freightModelDto = new FreightModelDto();
        freightModelDto.setDefaultModel(isDefault == 1);
        freightModelDto.setGmtCreate(gmtCreate);
        freightModelDto.setGmtModified(gmtModified);
        freightModelDto.setId(id);
        freightModelDto.setName(name);
        freightModelDto.setType(type);
        freightModelDto.setUnit(unit);
        return freightModelDto;
    }
}

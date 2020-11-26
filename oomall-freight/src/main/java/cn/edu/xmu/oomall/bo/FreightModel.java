package cn.edu.xmu.oomall.bo;


import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.vo.FreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.FreightModelDefineResponse;
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

    private String name;

    private Integer type;

    private Boolean isDefault;

    private Long unit;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

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
     * 生成FreightModelPo对象
     *
     * @return FreightModelPo
     */
    public FreightModelPo createPo() {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setId(id);
        freightModelPo.setShopId(shopId);
        freightModelPo.setDefaultModel(isDefault);
        freightModelPo.setGmtCreated(gmtCreated);
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
        freightModelDefineResponse.setDefaultModel(isDefault);
        freightModelDefineResponse.setId(id);
        freightModelDefineResponse.setName(name);
        freightModelDefineResponse.setType(type);
        return freightModelDefineResponse;
    }

}

package cn.edu.xmu.oomall.bo;


import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.vo.FreightModelDefineRequest;
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

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    /**
     * 构造函数
     * @param freightModelPo Po对象
     */
    public FreightModel(FreightModelPo freightModelPo){
        id = freightModelPo.getId();
        name = freightModelPo.getName();
        type = freightModelPo.getType();
        isDefault = freightModelPo.getDefaultModel();
    }

    /**
     * 构造函数
     * @param freightModelDefineRequest Vo对象
     */
    public FreightModel(FreightModelDefineRequest freightModelDefineRequest){
        name = freightModelDefineRequest.getName();
        type = freightModelDefineRequest.getType();
        unit = freightModelDefineRequest.getUnit();
    }

    /**
     * 生成FreightModelPo对象
     *
     * @return FreightModelPo
     */
    public FreightModelPo createPo(){
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setId(id);
        freightModelPo.setShopId(shopId);
        freightModelPo.setDefaultModel(isDefault);
        freightModelPo.setGmtCreate(gmtCreate);
        freightModelPo.setGmtModified(gmtModified);
        freightModelPo.setName(name);
        freightModelPo.setType(type);
        freightModelPo.setUnit(unit);
        return freightModelPo;
    }
}

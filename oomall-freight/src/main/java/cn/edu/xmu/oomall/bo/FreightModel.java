package cn.edu.xmu.oomall.bo;


import cn.edu.xmu.oomall.entity.FreightModelPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author zhibin lan
 * @date 2020-11-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModel {

    private Long id;

    private String name;

    private Integer type;

    private Boolean isDefault;

    private Integer unit;
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
}

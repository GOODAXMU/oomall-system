package cn.edu.xmu.oomall.bo;

import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.vo.WeightFreightModelDefineRequest;
import cn.edu.xmu.oomall.vo.WeightItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhibin lan
 * @date 2020-11-23
 * @modify
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeightFreightModel {

    private Long id;

    private Long freightModelId;

    private Long firstWeight;

    private Long firstWeightFreight;

    private Long tenPrice;

    private Long fiftyPrice;

    private Long hundredPrice;

    private Long trihunPrice;

    private Long abovePrice;

    private Long rid;

    /**
     * 构造函数
     * @param weightFreightModelPo Po对象
     */
    public WeightFreightModel(WeightFreightModelPo weightFreightModelPo){
        id = weightFreightModelPo.getId();
        freightModelId = weightFreightModelPo.getFreightModelId();
        firstWeight = weightFreightModelPo.getFirstWeight();
        firstWeightFreight = weightFreightModelPo.getFirstWeightFreight();
        tenPrice = weightFreightModelPo.getTenPrice();
        fiftyPrice = weightFreightModelPo.getFiftyPrice();
        hundredPrice = weightFreightModelPo.getHundredPrice();
        trihunPrice = weightFreightModelPo.getTrihunPrice();
        abovePrice = weightFreightModelPo.getAbovePrice();
        rid  = weightFreightModelPo.getRegionId();
    }

    /**
     * 构造函数
     * @param weightFreightModelDefineRequest Po对象, id Long
     */
    public WeightFreightModel(WeightFreightModelDefineRequest weightFreightModelDefineRequest,Long id){
        freightModelId = id;
        firstWeight= Long.valueOf(weightFreightModelDefineRequest.getFirstWeight());
        firstWeightFreight= Long.valueOf(weightFreightModelDefineRequest.getFirstWeightFreight());
        tenPrice= Long.valueOf(weightFreightModelDefineRequest.getTenPrice());
        fiftyPrice= Long.valueOf(weightFreightModelDefineRequest.getFiftyPrice());
        hundredPrice= Long.valueOf(weightFreightModelDefineRequest.getHundredPrice());
        trihunPrice= Long.valueOf(weightFreightModelDefineRequest.getTrihunPrice());
        abovePrice= Long.valueOf(weightFreightModelDefineRequest.getAbovePrice());
        rid= Long.valueOf(weightFreightModelDefineRequest.getRegionId());
    }

    /**
     * 构造函数
     * @param weightFreightModelDefineRequest Po对象
     */
    public WeightFreightModel(WeightItemRequest weightFreightModelDefineRequest,Long id){
        this.id=id;
        firstWeight= weightFreightModelDefineRequest.getFirstWeight();
        firstWeightFreight= weightFreightModelDefineRequest.getFirstWeightPrice();
        tenPrice= weightFreightModelDefineRequest.getTenPrice();
        fiftyPrice= weightFreightModelDefineRequest.getFiftyPrice();
        hundredPrice= weightFreightModelDefineRequest.getHundredPrice();
        trihunPrice= weightFreightModelDefineRequest.getTrihunPrice();
        abovePrice= weightFreightModelDefineRequest.getAbovePrice();
        rid= weightFreightModelDefineRequest.getRegionId();
    }

    /**
     *将po列表转化成bo列表
     * @param weightFreightModelPos Po对象列表
     */
    public List<WeightFreightModel> weightFreightModelPoListToBoList(List<WeightFreightModelPo> weightFreightModelPos){
        List<WeightFreightModel> weightFreightModels = new ArrayList<>();
        for(WeightFreightModelPo w: weightFreightModelPos){
            weightFreightModels.add(new WeightFreightModel(w));
        }
        return weightFreightModels;
    }

    /**
     *根据po修改bo
     * @param weightFreightModelPo Po对象
     */
    public Boolean modifyWeightFreightModelByPo(WeightItemRequest weightFreightModelPo){
        firstWeight=weightFreightModelPo.getFirstWeightPrice();
        tenPrice=weightFreightModelPo.getTenPrice();
        fiftyPrice=weightFreightModelPo.getFiftyPrice();
        hundredPrice=weightFreightModelPo.getHundredPrice();
        trihunPrice=weightFreightModelPo.getTrihunPrice();
        abovePrice=weightFreightModelPo.getAbovePrice();
        rid=weightFreightModelPo.getRegionId();
        return Boolean.TRUE;

    }


}

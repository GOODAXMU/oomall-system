package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.constant.OrderModuleStatus;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.repository.FreightModelRepository;
import cn.edu.xmu.oomall.repository.PieceModelRepository;
import cn.edu.xmu.oomall.repository.WeightModelRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */

@Repository
public class FreightDao {

    @Autowired
    private FreightModelRepository freightModelRepository;

    @Autowired
    private WeightModelRepository weightModelRepository;

    @Autowired
    private PieceModelRepository pieceModelRepository;

    /**
     * 通过po查询运费模板
     *
     * @param freightModelPo po对象
     * @return FreightModel
     * createdBy: zhibin lan
     */
    public FreightModel getFreightModelByPo(FreightModelPo freightModelPo) {
        FreightModel freightModel = new FreightModel(freightModelRepository.findOne(SpecificationFactory.get(freightModelPo)).get());
        return freightModel;
    }

    /**
     * 通过id查询运费模板
     *
     * @param id
     * @return FreightModel
     * createdBy: zhibin lan
     */
    public FreightModel getFreightModelById(Long id) throws OrderModuleException {
        FreightModel freightModel = new FreightModel(freightModelRepository.findById(id).get());
        if (null == freightModel) {
            throw new OrderModuleException(OrderModuleStatus.RESOURCE_ID_NOT_EXIST);
        }
        return freightModel;
    }

    /**
     * 通过po查询重量运费模板
     *
     * @param weightFreightModelPo po对象
     * @return WeightFreightModel
     * createdBy: zhibin lan
     */
    public WeightFreightModel getWeightFreightModelByPo(WeightFreightModelPo weightFreightModelPo) {
        WeightFreightModel weightFreightModel = new WeightFreightModel(
                weightModelRepository.findOne(SpecificationFactory.get(weightFreightModelPo)).get());
        return weightFreightModel;
    }

    /**
     * 通过po查询计件运费模板
     *
     * @param pieceFreightModelPo po对象
     * @return PieceFreightModel
     * createdBy: zhibin lan
     */
    public PieceFreightModel getPieceFreightModelByPo(PieceFreightModelPo pieceFreightModelPo) {
        PieceFreightModel pieceFreightModel = new PieceFreightModel(
                pieceModelRepository.findOne(SpecificationFactory.get(pieceFreightModelPo)).get());
        return pieceFreightModel;
    }

    /**
     * 创建店铺的运费模板
     *
     * @param freightModelPo po对象
     * @return FreightModel
     */
    public FreightModel createFreightModel(FreightModelPo freightModelPo) throws OrderModuleException{
        //模板名重复
        if (isFreightModelNameExit(freightModelPo.getName())) {
            throw new OrderModuleException(OrderModuleStatus.FREIGHT_NAME_EXIST);
        }
        return null;
    }

    /**
     * 检测运费模板名称是否存在
     *
     * @param name 运费模板名称
     * @return boolean
     */
    public boolean isFreightModelNameExit(String name) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setName(name);
        return null != freightModelRepository.findOne(SpecificationFactory.get(freightModelPo)).get();
    }
}

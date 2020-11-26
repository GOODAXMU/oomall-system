package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.exception.OrderModuleException;
import cn.edu.xmu.oomall.repository.FreightModelRepository;
import cn.edu.xmu.oomall.repository.PieceModelRepository;
import cn.edu.xmu.oomall.repository.WeightModelRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author zhibin lan
 * @date 2020-11-20
 */

@Repository
@Slf4j
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
    public Reply<FreightModel> getFreightModelById(Long id) {
        FreightModel freightModel = new FreightModel(freightModelRepository.findById(id).get());
        if (null == freightModel) {
            log.debug("freight model no exist, id: " + id);
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(freightModel);
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
    public Reply<FreightModelPo> createFreightModel(FreightModelPo freightModelPo) {

        Reply reply = new Reply<FreightModelPo>();
        //判断模板名是否重复
        if (isFreightModelNameExit(freightModelPo.getName())) {
            log.debug("模板名重复: "+ freightModelPo.getName());
            return new Reply<>(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        try{
            reply.setData(freightModelRepository.saveAndFlush(freightModelPo));
        }
        catch (Exception e){
            log.debug("数据库错误: "+ e.getMessage());
            return new Reply<>(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return reply;
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
        return !freightModelRepository.findOne(SpecificationFactory.get(freightModelPo)).isEmpty();
    }
}

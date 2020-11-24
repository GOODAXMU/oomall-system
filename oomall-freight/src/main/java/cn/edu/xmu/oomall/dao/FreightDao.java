package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
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

    public FreightModel getFreightModelByPo(FreightModelPo freightModelPo){
        FreightModel freightModel = new FreightModel(freightModelRepository.findOne(SpecificationFactory.get(freightModelPo)).get());
        return freightModel;
    }

    public FreightModel getFreightModelById(Long id){
        return new FreightModel(freightModelRepository.findById(id).get());
    }

    public WeightFreightModel getWeightFreightModelByPo(WeightFreightModelPo weightFreightModelPo){
        WeightFreightModel weightFreightModel = new WeightFreightModel(
                weightModelRepository.findOne(SpecificationFactory.get(weightFreightModelPo)).get());
        return weightFreightModel;
    }

    public PieceFreightModel getPieceFreightModelByPo(PieceFreightModelPo pieceFreightModelPo){
        PieceFreightModel pieceFreightModel = new PieceFreightModel(
                pieceModelRepository.findOne(SpecificationFactory.get(pieceFreightModelPo)).get());
        return pieceFreightModel;
    }
}

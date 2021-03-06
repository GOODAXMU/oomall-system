package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.dao.FreightDao;
import cn.edu.xmu.oomall.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yan song
 * @date 2020-11-25
 */
@Service
public class FreightModelService {
    @Autowired
    private FreightDao freightDao;


    /**
     * 管理员定义重量模板明细
     *
     * @param weightFreightModel,id;
     * @return Reply<WeightFreightModel>;
     */
    public Reply<WeightFreightModel> defineWeightFreightModel(WeightFreightModel weightFreightModel, Long shopId) {
        Reply<WeightFreightModel> reply = freightDao.defineWeightFreightModel(weightFreightModel, shopId);
        return reply;
    }

    /**
     * 店家或管理员查询某个运费模板的重量模板明细
     *
     * @param id Long;
     * @return Reply<List < WeightFreightModel>>;
     */
    public Reply<List<WeightFreightModelQueryResponse>> getWeightFreightModel(Long id, Long shopId, Long uShopId) {
        return freightDao.getWeightFreightModelByFreightModelId(id, shopId, uShopId);
    }

    /**
     * 管理员定义件数模板明细
     *
     * @param pieceFreightModel,id;
     * @return Reply<PieceFreightModel>;
     */
    public Reply<PieceFreightModel> definePieceFreightModel(PieceFreightModel pieceFreightModel, Long shopId) {
        return freightDao.definePieceFreightModel(pieceFreightModel, shopId);
    }

    /**
     * 店家或管理员查询某个运费模板的计件模板明细
     *
     * @param id;
     * @return Reply<List < PieceFreightModel>>;
     */
    public Reply<List<PieceFreightQueryResponse>> queryPieceFreightModel(Long id, Long shopId, Long uShopId) {
        return freightDao.getPieceFreightModelByFreightModelId(id, shopId, uShopId);
    }

    /**
     * 店家或管理员修改重量运费模板明细
     *
     * @param weightFreightModel,id;
     * @return Reply<>;
     */
    public Reply modifyWeightFreightModel(WeightFreightModel weightFreightModel, Long id, Long shopId) {
        return freightDao.modifyWeightFreightModel(weightFreightModel, id, shopId);
    }

    /**
     * 店家或管理员删除重量运费模板明细
     *
     * @param id;
     * @return Reply<>;
     */
    public Reply deleteWeightFreightModel(Long id, Long shopId) {
        return freightDao.deleteWeightFreightModel(id, shopId);
    }

    /**
     * 店家或管理员修改计件运费模板明细
     *
     * @param pieceFreightModel,id;
     * @return Reply<>;
     */
    public Reply modifyPieceFreightModel(PieceFreightModel pieceFreightModel, Long id, Long shopId) {
        return freightDao.modifyPieceFreightModel(pieceFreightModel, id, shopId);
    }

    /**
     * 店家或管理员删除计件运费模板明细
     *
     * @param id;
     * @return Reply<>;
     */
    public Reply deletePieceFreightModel(Long id, Long shopId) {
        return freightDao.deletePieceFreightModel(id, shopId);
    }

}

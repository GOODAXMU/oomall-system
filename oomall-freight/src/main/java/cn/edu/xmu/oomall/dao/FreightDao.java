package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.FreightModel;
import cn.edu.xmu.oomall.bo.PieceFreightModel;
import cn.edu.xmu.oomall.bo.WeightFreightModel;
import cn.edu.xmu.oomall.constant.ResponseStatus;
import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
import cn.edu.xmu.oomall.repository.FreightModelRepository;
import cn.edu.xmu.oomall.repository.PieceModelRepository;
import cn.edu.xmu.oomall.repository.WeightModelRepository;
import cn.edu.xmu.oomall.repository.util.SpecificationFactory;
import cn.edu.xmu.oomall.util.PageInfo;
import cn.edu.xmu.oomall.vo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
     * 通过shopId查询默认运费模板
     *
     * @param shopId
     * @return FreightModel
     * createdBy: zhibin lan
     */
    public FreightModel getDefaultFreightModel(Long shopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setShopId(shopId);
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
        FreightModel freightModel = new FreightModel();
        try {
            freightModel = new FreightModel(freightModelRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            log.debug("freight model no exist, id: " + id);
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(freightModel);
    }

    /**
     * 通过运费模板获取重量运费模板
     *
     * @param freightModel
     * @return
     */
    public WeightFreightModel getWeightFreightModel(FreightModel freightModel) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setFreightModelId(freightModel.getId());
        weightFreightModelPo.setRegionId(freightModel.getRid());
        WeightFreightModel weightFreightModel = new WeightFreightModel(
                weightModelRepository.findOne(SpecificationFactory.get(weightFreightModelPo)).get());
        return weightFreightModel;
    }

    /**
     * 通过运费模板获取计件运费模板
     *
     * @param freightModel
     * @return
     */
    public PieceFreightModel getPieceFreightModel(FreightModel freightModel) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setFreightModelId(freightModel.getId());
        pieceFreightModelPo.setRegionId(freightModel.getRid());
        PieceFreightModel pieceFreightModel = new PieceFreightModel(
                pieceModelRepository.findOne(SpecificationFactory.get(pieceFreightModelPo)).get());
        return pieceFreightModel;
    }

    /**
     * 创建店铺的运费模板
     *
     * @param freightModel Bo对象
     * @return FreightModel
     */
    public Reply<FreightModel> createFreightModel(FreightModel freightModel) {

        FreightModelPo freightModelPo = freightModel.createPo();
        freightModelPo.setGmtModified(LocalDateTime.now());
        freightModelPo.setGmtCreate(LocalDateTime.now());
        Reply reply = new Reply<FreightModel>();
        //判断模板名是否重复
        if (isFreightModelNameExit(freightModelPo.getName())) {
            log.debug("模板名重复: " + freightModelPo.getName());
            return new Reply<>(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        try {
            reply.setData(new FreightModel(freightModelRepository.saveAndFlush(freightModelPo)));
            log.debug("insert data: " + reply.getData());
        } catch (Exception e) {
            log.debug("数据库错误: " + e.getMessage());
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

    /**
     * 获取所有运费模板
     *
     * @param pageInfo
     * @param name     运费模板名称
     * @param shopId   店铺id
     * @return
     */
    public Reply<List<FreightModel>> findAllFreightModels(PageInfo pageInfo, String name, Long shopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setName(name);
        freightModelPo.setShopId(shopId);
        Page<FreightModelPo> freightModelPoPage = freightModelRepository.findAll(
                SpecificationFactory.get(freightModelPo),
                PageRequest.of(pageInfo.getPage() - 1, pageInfo.getPageSize())
        );
        pageInfo.calAndSetPagesAndTotal(freightModelPoPage.getTotalElements(), freightModelPoPage.getTotalPages());

        List<FreightModel> freightModels = new ArrayList<>();
        for (FreightModelPo po : freightModelPoPage.getContent()) {
            freightModels.add(new FreightModel(po));
        }

        return new Reply<List<FreightModel>>(freightModels);
    }

    /**
     * 更新运费模板
     *
     * @param freightModel
     * @return
     */
    public Reply updateFreightModel(FreightModel freightModel) {
        if (isFreightModelNameExit(freightModel.getName())) {
            return new Reply(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        FreightModelPo freightModelPo = freightModel.createPo();
        int ret = freightModelRepository.update(freightModelPo);
        if (ret <= 0) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 克隆运费模板
     *
     * @param id     模板id
     * @param shopId 商铺id
     * @return
     */
    public Reply<FreightModel> cloneFreightModel(Long id, Long shopId) {
        Reply<FreightModel> freightModelReply = getFreightModelById(id);
        if (!freightModelReply.isOk()) {
            return freightModelReply;
        }
        FreightModel freightModel = freightModelReply.getData();
        freightModel.setId(null);
        freightModel.setShopId(shopId);
        freightModel.setName(freightModel.getName() + UUID.randomUUID());
        return createFreightModel(freightModel);
    }

    /**
     * 删除运费模板
     *
     * @param id 模板id
     * @return
     */
    public Reply deleteFreightModel(Long id) {

        try {
            freightModelRepository.deleteById(id);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除指定运费模板下的重量运费模板
     *
     * @param id 模板id
     * @return
     */
    public Reply deleteWeightFreightModel(Long id) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setFreightModelId(id);
        try {
            weightModelRepository.delete(weightFreightModelPo);
        } catch (Exception e) {
            log.debug("删除失败: " + e.getMessage());
            return new Reply(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除指定运费模板下的计件运费模板
     *
     * @param id 模板id
     * @return
     */
    public Reply deletePieceFreightModel(Long id) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setFreightModelId(id);
        try {
            pieceModelRepository.delete(pieceFreightModelPo);
        } catch (Exception e) {
            log.debug("删除失败: " + e.getMessage());
            return new Reply(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 设置默认运费模板
     *
     * @param id     模板id
     * @param shopId 商铺id
     * @return
     */
    public Reply defineDefaultFreightModel(Long id, Long shopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setShopId(shopId);
        freightModelPo.setId(id);
        freightModelPo.setDefaultModel(1);
        int ret = freightModelRepository.update(freightModelPo);
        if (ret <= 0) {
            log.debug("设置默认运费模板失败: " + ret);
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply(ResponseStatus.OK);
    }
}

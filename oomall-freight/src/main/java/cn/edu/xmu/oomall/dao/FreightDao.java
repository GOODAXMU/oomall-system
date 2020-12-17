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
import cn.edu.xmu.oomall.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

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
     * @author zhibin lan
     */
    public FreightModel getDefaultFreightModel(Long shopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setShopId(shopId);
        freightModelPo.setDefaultModel(1);
        Optional<FreightModelPo> option = freightModelRepository.findOne(SpecificationFactory.get(freightModelPo));
        if (option.isEmpty()) {
            return null;
        }
        FreightModel freightModel = new FreightModel(option.get());
        return freightModel;
    }

    /**
     * 通过id查询运费模板
     *
     * @param id
     * @param shopId
     * @param uShopId
     * @return FreightModel
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id, Long shopId, Long uShopId) {
        FreightModel freightModel = new FreightModel();
        try {
            freightModel = new FreightModel(freightModelRepository.findById(id).get());
            if (!uShopId.equals(Long.valueOf(0)) && !freightModel.getShopId().equals(shopId)) {
                return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
            }
        } catch (NoSuchElementException e) {
            log.debug("freight model no exist, id: " + id);
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(freightModel);
    }

    /**
     * 通过id查询运费模板
     *
     * @param id
     * @param shopId
     * @return FreightModel
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id, Long shopId) {
        FreightModel freightModel = new FreightModel();
        try {
            freightModel = new FreightModel(freightModelRepository.findById(id).get());
            if (!shopId.equals(0L) && !freightModel.getShopId().equals(shopId)) {
                return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
            }
        } catch (NoSuchElementException e) {
            log.debug("freight model no exist, id: " + id);
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(freightModel);
    }

    /**
     * 通过id查询运费模板
     *
     * @param id
     * @return FreightModel
     * @author zhibin lan
     */
    public Reply<FreightModel> getFreightModelById(Long id) {
        FreightModel freightModel;
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
     * @author zhibin lan
     */
    public Reply<WeightFreightModel> getWeightFreightModel(FreightModel freightModel) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setFreightModelId(freightModel.getId());
        weightFreightModelPo.setRegionId(freightModel.getRid());
        WeightFreightModel weightFreightModel;
        try {
            weightFreightModel = new WeightFreightModel(weightModelRepository.findOne(SpecificationFactory.get(weightFreightModelPo)).get());
        } catch (NoSuchElementException e) {
            return new Reply<>(ResponseStatus.REGION_NOT_REACH);
        }
        return new Reply<>(weightFreightModel);
    }

    /**
     * 通过运费模板获取计件运费模板
     *
     * @param freightModel
     * @return
     * @author zhibin lan
     */
    public Reply<PieceFreightModel> getPieceFreightModel(FreightModel freightModel) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setFreightModelId(freightModel.getId());
        pieceFreightModelPo.setRegionId(freightModel.getRid());
        PieceFreightModel pieceFreightModel;
        try {
            pieceFreightModel = new PieceFreightModel(
                    pieceModelRepository.findOne(SpecificationFactory.get(pieceFreightModelPo)).get());
        } catch (NoSuchElementException e) {
            return new Reply<>(ResponseStatus.REGION_NOT_REACH);
        }
        return new Reply<>(pieceFreightModel);
    }

    /**
     * 创建店铺的运费模板
     *
     * @param freightModel Bo对象
     * @return FreightModel
     * @author zhibin lan
     */
    public Reply<FreightModel> createFreightModel(FreightModel freightModel) {

        FreightModelPo freightModelPo = freightModel.createPo();
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime gmt = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(),
                localDateTime.getSecond());
        freightModelPo.setGmtModified(gmt);
        freightModelPo.setGmtCreate(gmt);
        Reply reply = new Reply<FreightModel>();
        log.debug("into createFreightModel");
        if (isFreightModelNameExist(freightModel.getName())) {
            return new Reply<>(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        try {
            reply.setData(new FreightModel(freightModelRepository.saveAndFlush(freightModelPo)));
            log.debug("insert data: " + reply.getData());
        } catch (Exception e) {
            log.debug("数据库错误: " + e.getMessage());
            return new Reply<>(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        return reply;
    }

    /**
     * 检测运费模板名称是否存在
     *
     * @param name 运费模板名称
     * @return boolean
     * @author zhibin lan
     */
    public boolean isFreightModelNameExist(String name) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setName(name);
        return !freightModelRepository.findOne(SpecificationFactory.get(freightModelPo)).isEmpty();
    }

    /**
     * 检测该运费模板名称是否是该商店的
     *
     * @param id
     * @param shopId
     * @param uShopId
     * @return Reply
     * @author zhibin lan
     */
    public Reply isFreightModelBelongShop(Long id, Long shopId, Long uShopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        try {
            freightModelPo = freightModelRepository.findById(id).get();
        } catch (Exception e) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!uShopId.equals(Long.valueOf(0)) && !freightModelPo.getShopId().equals(shopId)) {
            return new Reply(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 检测运费模板名称是否存在并且该模板是否属于该商店
     *
     * @param freightModel
     * @param uShopId
     * @return boolean
     * @author zhibin lan
     */
    public Reply checkFreightModel(FreightModel freightModel, Long uShopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        try {
            freightModelPo = freightModelRepository.findById(freightModel.getId()).get();
        } catch (Exception e) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!uShopId.equals(Long.valueOf(0)) && !freightModel.getShopId().equals(freightModelPo.getShopId())) {
            return new Reply(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        return new Reply(ResponseStatus.OK);

    }

    /**
     * 获取所有运费模板
     *
     * @param pageInfo
     * @param name     运费模板名称
     * @param shopId   店铺id
     * @return
     * @author zhibin lan
     */
    public Reply<List<FreightModel>> findAllFreightModels(PageInfo pageInfo, String name, Long shopId) {
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setName(name);
        freightModelPo.setShopId(shopId);
        Page<FreightModelPo> freightModelPoPage = freightModelRepository.findAll(
                SpecificationFactory.get(freightModelPo),
                PageRequest.of(pageInfo.getJpaPage(), pageInfo.getPageSize())
        );
        pageInfo.setPagesAndTotal(freightModelPoPage.getTotalElements(), freightModelPoPage.getTotalPages());
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
     * @param uShopId
     * @return
     * @author zhibin lan
     */
    public Reply updateFreightModel(FreightModel freightModel, Long uShopId) {

        Reply reply = checkFreightModel(freightModel, uShopId);
        if (!reply.isOk()) {
            return reply;
        }

        if (isFreightModelNameExist(freightModel.getName())) {
            return new Reply(ResponseStatus.FREIGHT_NAME_EXIST);
        }
        System.out.println("update freight model check success: ");
        FreightModelPo freightModelPo = freightModel.createPo();
        int ret = freightModelRepository.update(freightModelPo);
        if (ret <= 0) {
            return new Reply(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 克隆运费模板
     *
     * @param id      模板id
     * @param shopId  商铺id
     * @param uShopId 用户所属商铺id
     * @return
     * @author zhibin lan
     */
    public Reply<FreightModel> cloneFreightModel(Long id, Long shopId, Long uShopId) {
        Reply<FreightModel> freightModelReply = getFreightModelById(id, shopId, uShopId);
        if (!freightModelReply.isOk()) {
            return freightModelReply;
        }
        FreightModel freightModel = freightModelReply.getData();
        freightModel.setId(null);
        freightModel.setShopId(shopId);
        freightModel.setIsDefault(0);
        freightModel.setName(freightModel.getName() + UUID.randomUUID());
        return createFreightModel(freightModel);
    }

    /**
     * 删除运费模板
     *
     * @param id      模板id
     * @param shopId
     * @param uShopId
     * @return
     * @author zhibin lan
     */
    public Reply deleteFreightModel(Long id, Long shopId, Long uShopId) {

        Reply reply = isFreightModelBelongShop(id, shopId, uShopId);
        if (!reply.isOk()) {
            return reply;
        }
        try {
            freightModelRepository.deleteById(id);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new Reply(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除重量运费模板
     *
     * @param id 模板id
     * @return
     * @author yan song
     */
    public Reply deleteWeightFreightModel(Long id, Long shopId) {
        WeightFreightModel weightFreightModel = getWeightFreightModelById(id).getData();
        if (null == weightFreightModel) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        FreightModel freightModel = getFreightModelById(weightFreightModel.getFreightModelId()).getData();
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        weightModelRepository.deleteById(id);
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除指定运费模板下的重量运费模板
     *
     * @param id 模板id
     * @return
     * @author zhibin lan
     */
    public Reply deleteWeightFreightModel(Long id) {
        int res = weightModelRepository.delete(id);
        if (res == 0) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除指定运费模板下的计件运费模板
     *
     * @param id 模板id
     * @return
     * @author zhibin lan
     */
    public Reply deletePieceFreightModel(Long id) {
        int res = pieceModelRepository.delete(id);
        if (res == 0) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 删除计件运费模板
     *
     * @param id 计件模板id
     * @return
     * @author yan song
     */
    public Reply deletePieceFreightModel(Long id, Long shopId) {
        PieceFreightModel pieceFreightModel = getPieceFreightModelById(id).getData();
        if (null == pieceFreightModel) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        FreightModel freightModel = getFreightModelById(pieceFreightModel.getFreightModelId()).getData();
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        pieceModelRepository.deleteById(id);
        return new Reply(ResponseStatus.OK);
    }

    /**
     * 设置默认运费模板，并取消原来的默认运费模板
     *
     * @param id      模板id
     * @param shopId  商铺id
     * @param uShopId 用户所属商铺id
     * @return
     * @author zhibin lan
     */
    public Reply defineDefaultFreightModel(Long id, Long shopId, Long uShopId) {
        Reply reply = isFreightModelBelongShop(id, shopId, uShopId);
        if (!reply.isOk()) {
            return reply;
        }
        FreightModelPo cancelFreightModelPo = new FreightModelPo();
        cancelFreightModelPo.setShopId(shopId);
        cancelFreightModelPo.setDefaultModel(1);
        //原来是否存在默认运费模板
        Boolean isExist = true;
        try {
            cancelFreightModelPo = freightModelRepository.findOne(SpecificationFactory.get(cancelFreightModelPo)).get();
        } catch (NoSuchElementException e) {
            isExist = false;
        }

        if (cancelFreightModelPo.getId().equals(id)) {
            return new Reply(ResponseStatus.OK);
        }
        FreightModelPo freightModelPo = new FreightModelPo();
        freightModelPo.setShopId(shopId);
        freightModelPo.setId(id);
        freightModelPo.setDefaultModel(1);
        int ret = freightModelRepository.update(freightModelPo);
        if (ret <= 0) {
            log.debug("设置默认运费模板失败: " + ret);
            return new Reply(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        if (isExist) {
            cancelFreightModelPo.setDefaultModel(0);
            freightModelRepository.update(cancelFreightModelPo);
        }
        return new Reply(ResponseStatus.OK);
    }


    /**
     * 定义重量运费模板
     *
     * @param weightFreightModel
     * @return WeightFreightModel
     */
    public Reply<WeightFreightModel> defineWeightFreightModel(WeightFreightModel weightFreightModel, Long shopId) {
        WeightFreightModelPo WeightFreightModelPo = new WeightFreightModelPo(weightFreightModel);
        WeightFreightModelPo.setGmtModified(LocalDateTime.now().toString());
        WeightFreightModelPo.setGmtCreate(LocalDateTime.now().toString());
        FreightModel freightModel = getFreightModelById(weightFreightModel.getFreightModelId()).getData();
        if (null == freightModel) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        if (null != getWeightFreightModelByRid(weightFreightModel.getRid())) {
            return new Reply<>(ResponseStatus.REGION_EXIST);
        }
        Reply<WeightFreightModel> reply = new Reply<>();
        try {
            reply.setData(new WeightFreightModel(weightModelRepository.saveAndFlush(WeightFreightModelPo)));
            log.debug("insert data: " + reply.getData());
        } catch (Exception e) {
            log.debug("数据库错误: " + e.getMessage());
            return new Reply<>(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return reply;
    }

    /**
     * 通过运费模板id查询重量运费模板
     *
     * @param id 运费模板id
     * @return WeightFreightModel
     */
    public Reply<List<WeightFreightModelQueryResponse>> getWeightFreightModelByFreightModelId(Long id, Long shopId, Long uShopId) {
        Reply<FreightModel> freightModelReply = getFreightModelById(id, shopId, uShopId);
        if (!freightModelReply.isOk()) {
            return new Reply<>(freightModelReply.getResponseStatus());
        }
        if (!freightModelReply.getData().getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setFreightModelId(id);
        List<WeightFreightModelPo> weightFreightModelPos = weightModelRepository.findAll(SpecificationFactory.get(weightFreightModelPo));
        if (weightFreightModelPos.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Reply<List<WeightFreightModelQueryResponse>> reply = new Reply<>(new WeightFreightModelQueryResponse().WeightFreightModelQueryResponseListTransfer(weightFreightModelPos));
        return new Reply<>(new WeightFreightModelQueryResponse().WeightFreightModelQueryResponseListTransfer(weightFreightModelPos));
    }

    /**
     * 通过运费模板id查询计件运费模板
     *
     * @param id 运费模板id
     * @return PieceFreightModel
     */
    public Reply<List<PieceFreightQueryResponse>> getPieceFreightModelByFreightModelId(Long id, Long shopId, Long uShopId) {
        Reply<FreightModel> freightModelReply = getFreightModelById(id, shopId, uShopId);
        if (!freightModelReply.isOk()) {
            return new Reply<>(freightModelReply.getResponseStatus());
        }
        if (!freightModelReply.getData().getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setFreightModelId(id);
        List<PieceFreightModelPo> pieceFreightModelPos = pieceModelRepository.findAll(SpecificationFactory.get(pieceFreightModelPo));
        if (pieceFreightModelPos.isEmpty()) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(new PieceFreightQueryResponse().PieceFreightQueryResponseListTransfer(pieceFreightModelPos));
    }


    /**
     * 修改重量运费模板
     *
     * @param weightFreightModel
     */

    public Reply modifyWeightFreightModel(WeightFreightModel weightFreightModel, Long id, Long shopId) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo(weightFreightModel);
        WeightFreightModel weightFreightModel1 = getWeightFreightModelById(id).getData();
        if (null == weightFreightModel1) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        FreightModel freightModel = getFreightModelById(weightFreightModel1.getFreightModelId()).getData();
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        WeightFreightModelPo weightFreightModelPo1 = new WeightFreightModelPo();
        weightFreightModelPo1.setRegionId(weightFreightModel.getRid());
        List<WeightFreightModelPo> weightFreightModelPos = weightModelRepository.findAll(SpecificationFactory.get(weightFreightModelPo1));
        if (!weightFreightModelPos.isEmpty()&&weightFreightModel.getRid()!=null) {
            int isExist = 0;
            for (WeightFreightModelPo po : weightFreightModelPos) {
                if (po.getId().equals(weightFreightModelPo.getId())) {
                    isExist = 1;
                }
            }
            if (isExist != 1 || weightFreightModelPos.size() > 1) {
                return new Reply(ResponseStatus.REGION_EXIST);
            }
        }
        int res = weightModelRepository.update(weightFreightModelPo);
        if (res == 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(ResponseStatus.OK);
    }

    /**
     * 修改计件运费模板
     *
     * @param pieceFreightModel
     */
    public Reply modifyPieceFreightModel(PieceFreightModel pieceFreightModel, Long id, Long shopId) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo(pieceFreightModel);
        PieceFreightModel pieceFreightModel1 = getPieceFreightModelById(id).getData();
        if (null == pieceFreightModel1) {
            return new Reply(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        FreightModel freightModel = getFreightModelById(pieceFreightModel1.getFreightModelId()).getData();
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        PieceFreightModelPo pieceFreightModelPo1 = new PieceFreightModelPo();
        pieceFreightModelPo1.setRegionId(pieceFreightModel.getRid());
        List<PieceFreightModelPo> pieceFreightModelPos = pieceModelRepository.findAll(SpecificationFactory.get(pieceFreightModelPo1));
        if (!pieceFreightModelPos.isEmpty()&&pieceFreightModel.getRid()!=null) {
            int isExist = 0;
            for (PieceFreightModelPo po : pieceFreightModelPos) {
                if (po.getId().equals(pieceFreightModelPo)) {
                    isExist = 1;
                }
            }
            if (isExist != 1 || pieceFreightModelPos.size() > 1) {
                return new Reply(ResponseStatus.REGION_EXIST);
            }
        }
        int res = pieceModelRepository.update(pieceFreightModelPo);
        if (res <= 0) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        return new Reply<>(ResponseStatus.OK);
    }

    /**
     * 通过重量运费模板id查询重量运费模板
     *
     * @param id 重量运费模板id
     * @return WeightFreightModel
     */
    public Reply<WeightFreightModel> getWeightFreightModelById(Long id) {
        WeightFreightModelPo weightFreightModelPo;
        try {
            weightFreightModelPo = weightModelRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Reply<WeightFreightModel> reply = new Reply<>(new WeightFreightModel(weightFreightModelPo));
        return reply;
    }

    /**
     * 定义计件运费模板
     *
     * @param pieceFreightModel
     * @return PieceFreightModel
     */
    public Reply<PieceFreightModel> definePieceFreightModel(PieceFreightModel pieceFreightModel, Long shopId) {
        FreightModel freightModel = getFreightModelById(pieceFreightModel.getFreightModelId()).getData();
        if (null == freightModel) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        if (!freightModel.getShopId().equals(shopId)) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_OUT_OF_SCOPE);
        }
        if (null != getPieceFreightModelByRid(pieceFreightModel.getRid()).getData()) {
            return new Reply<>(ResponseStatus.REGION_EXIST);
        }
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo(pieceFreightModel);
        pieceFreightModelPo.setGmtModified(LocalDateTime.now());
        pieceFreightModelPo.setGmtCreate(LocalDateTime.now());
        Reply reply = new Reply<PieceFreightModel>();
        try {
            reply.setData(new PieceFreightModel(pieceModelRepository.saveAndFlush(pieceFreightModelPo)));
            log.debug("insert data: " + reply.getData());
        } catch (Exception e) {
            log.debug("数据库错误: " + e.getMessage());
            return new Reply<>(ResponseStatus.INTERNAL_SERVER_ERR);
        }
        return reply;
    }

    /**
     * 通过计件运费模板id查询计件运费模板
     *
     * @param id 计件运费模板id
     * @return WeightFreightModel
     */
    public Reply<PieceFreightModel> getPieceFreightModelById(Long id) {
        PieceFreightModelPo pieceFreightModelPo;
        try {
            pieceFreightModelPo = pieceModelRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new Reply<>(ResponseStatus.RESOURCE_ID_NOT_EXIST);
        }
        Reply<PieceFreightModel> reply = new Reply<>(new PieceFreightModel(pieceFreightModelPo));
        return reply;
    }

    /**
     * 通过计件运费模板rid查询计件运费模板
     *
     * @param rid 计件运费模板rid
     * @return PieceFreightModel
     */
    public Reply<PieceFreightModel> getPieceFreightModelByRid(Long rid) {
        PieceFreightModelPo pieceFreightModelPo = new PieceFreightModelPo();
        pieceFreightModelPo.setRegionId(rid);
        List<PieceFreightModelPo> pieceFreightModelPos = pieceModelRepository.findAll(SpecificationFactory.get(pieceFreightModelPo));
        if (pieceFreightModelPos.isEmpty()) {
            return new Reply<>(null);
        }
        return new Reply<>(new PieceFreightModel(pieceFreightModelPos.get(0)));
    }

    /**
     * 通过重量运费模板rid查询重量运费模板
     *
     * @param rid 重量运费模板rid
     * @return WeightFreightModel
     */
    public Reply<WeightFreightModel> getWeightFreightModelByRid(Long rid) {
        WeightFreightModelPo weightFreightModelPo = new WeightFreightModelPo();
        weightFreightModelPo.setRegionId(rid);
        List<WeightFreightModelPo> weightFreightModelPos = weightModelRepository.findAll(SpecificationFactory.get(weightFreightModelPo));
        if (weightFreightModelPos.isEmpty()) {
            return new Reply<>(null);
        }
        return new Reply<>(new WeightFreightModel((weightFreightModelPos.get(0))));
    }

}

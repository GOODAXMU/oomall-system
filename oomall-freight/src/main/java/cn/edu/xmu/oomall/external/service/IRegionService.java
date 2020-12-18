package cn.edu.xmu.oomall.external.service;

/**
 * @author zhibin lan
 * @date 2020-12-18
 */
public interface IRegionService {
    /**
     * 获取该region的上级地区
     *
     * @param regionId
     * @return
     */
    Long getSuperiorRegionId(Long regionId);
}

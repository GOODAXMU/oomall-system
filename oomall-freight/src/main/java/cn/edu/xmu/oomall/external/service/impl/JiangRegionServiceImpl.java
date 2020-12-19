package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IRegionService;
import cn.edu.xmu.oomall.other.impl.IDubboRegionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class JiangRegionServiceImpl implements IRegionService {

    @DubboReference(version = "${oomall.external.region-service.version}", cache = "false", timeout = 5000, check = false)
    private IDubboRegionService regionService;

    @Override
    public Long getSuperiorRegionId(Long regionId) {
        Long sRegionId = regionService.getSuperiorRegionId(regionId);
        return sRegionId == null ? -1 : sRegionId;
    }
}

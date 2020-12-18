package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IRegionService;
import org.springframework.stereotype.Component;

@Component
public class SimulatedRegionServiceImpl implements IRegionService {
    @Override
    public Long getSuperiorRegionId(Long regionId){
        return 14L;
    }
}

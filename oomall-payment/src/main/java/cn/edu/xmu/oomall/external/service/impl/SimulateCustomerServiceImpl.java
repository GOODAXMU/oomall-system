package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.other.impl.IRebateService;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 * modified 2020/12/16
 */
@Component
public class SimulateCustomerServiceImpl implements ICustomerService {

    @DubboReference(version = "${oomall.external.customer-service.version}", cache = "false", timeout = 5000, check = false)
    private IRebateService rebateService;

    @Override
    public Integer useRebate(Long customerId, Integer rebate) {
        return rebateService.useRebate(customerId, rebate);
    }

    @Override
    public Integer refundRebate(Long customerId, Integer rebate) {
        return rebateService.useRebate(customerId, -rebate);
    }
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.ICustomerService;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 */
@Component
public class SimulateCustomerServiceImpl implements ICustomerService {

    @Override
    public Boolean useRebate(Long customerId, Long rebate) {
        return true;
    }

    @Override
    public Boolean refundRebate(Long customerId, Long rebate) {
        return true;
    }
}

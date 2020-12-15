package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 */
@Component
public class SimulateAfterSaleServiceImpl implements IAfterSaleService {

    @Override
    public Boolean isCustomerOwnAfterSale(Long customerId, Long afterSaleId) {
        return true;
    }

    @Override
    public Boolean isShopOwnAfterSale(Long shopId, Long afterSaleId) {
        return true;
    }

    @Override
    public Boolean afterSaleCanBePaid(Long id) {
        return true;
    }

    @Override
    public void checkAfterSalePaid(Long id, Long amount) {
        /* do nothing */
    }

    @Override
    public Long getCustomerIdByAfterSaleId(Long id) {
        return 0L;
    }
}

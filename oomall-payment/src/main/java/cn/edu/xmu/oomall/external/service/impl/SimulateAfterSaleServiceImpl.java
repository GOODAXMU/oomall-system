package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IAfterSaleService;
import cn.edu.xmu.oomall.other.impl.IAftersaleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author Wang Zhizhou
 * create 2020/12/14
 */
@Component
public class SimulateAfterSaleServiceImpl implements IAfterSaleService {

    @DubboReference(version = "${oomall.external.afterSale-service.version}", cache = "false", timeout = 5000, check = false)
    private IAftersaleService aftersaleService;

    @Override
    public Boolean isCustomerOwnAfterSale(Long customerId, Long afterSaleId) {
        return aftersaleService.isCustomerOwnAftersale(customerId, afterSaleId);
    }

    @Override
    public Boolean isShopOwnAfterSale(Long shopId, Long afterSaleId) {
        return aftersaleService.isShopOwnAftersale(shopId, afterSaleId);
    }

    @Override
    public Boolean afterSaleCanBePaid(Long id) {
        return aftersaleService.aftersaleCanBePaid(id);
    }

    @Override
    public Long getCustomerIdByAfterSaleId(Long id) {
        return aftersaleService.getCustomerIdByAftersaleId(id);
    }
}

package cn.edu.xmu.oomall.external.service;

/**
 * @author Wang Zhizhou
 * create 2020/12/10
 */
public interface IAfterSaleServer {

    /**
     * 检查用户是否拥有该售后
     * @param afterSaleId 售后id
     * @param customerId  用户id
     * @return
     */
    Boolean isCustomerOwnAfterSale(Long customerId, Long afterSaleId);

    /**
     * 检查商店是否拥有该售后
     * @param shopId        商店id
     * @param afterSaleId   售后id
     * @return
     */
    Boolean isShopOwnAfterSale(Long shopId, Long afterSaleId);
}

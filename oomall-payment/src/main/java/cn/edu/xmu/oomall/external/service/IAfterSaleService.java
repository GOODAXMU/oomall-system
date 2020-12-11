package cn.edu.xmu.oomall.external.service;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 */
public interface IAfterSaleService {

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

    /**
     * 查看售后是否处于可支付状态
     * @param id    售后id
     * @return
     */
    Boolean afterSaleCanBePaid(Long id);

    /**
     * 完成支付通知售后检查状态
     * @param id        售后id
     * @param amount    售后完成的支付
     */
    void checkAfterSalePaid(Long id, Long amount);

    /**
     * 获取售后的顾客id
     * @param id 售后id
     * @return
     */
    Long getCustomerIdByAfterSaleId(Long id);

}

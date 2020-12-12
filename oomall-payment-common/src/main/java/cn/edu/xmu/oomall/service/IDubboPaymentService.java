package cn.edu.xmu.oomall.service;

/**
 * @author Wang Zhizhou
 * create 2020/12/06
 * modified 2020/12/12
 */
public interface IDubboPaymentService {

    /**
     * 根据售后创建退款
     * @param afterSaleId   售后id
     * @param orderItemId   需要返款的orderItem id
     * @param quantity      返款件数
     */
    Boolean createRefund(Long afterSaleId, Long orderItemId, Integer quantity);
}

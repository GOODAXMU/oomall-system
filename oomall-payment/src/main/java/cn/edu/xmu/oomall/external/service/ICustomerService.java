package cn.edu.xmu.oomall.external.service;

/**
 * @author Wang Zhizhou
 * create 2020/12/11
 */
public interface ICustomerService {

    /**
     * 支付使用顾客的返点, 若返点充足则扣除返点, 支付成功
     * @param customerId    顾客id
     * @param rebate        使用的返点数额
     * @return  true or false
     */
    Boolean useRebate(Long customerId, Long rebate);

    /**
     * 返款返还顾客的返点, 返还应必然成功
     * @param customerId    顾客id
     * @param rebate        返还返点
     * @return
     */
    Boolean refundRebate(Long customerId, Long rebate);
}

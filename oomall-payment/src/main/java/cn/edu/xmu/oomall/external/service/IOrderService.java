package cn.edu.xmu.oomall.external.service;

/**
 * @author Wang Zhizhou
 * create 2020/11/28
 * modified 2020/12/08
 */
public interface IOrderService {

    /**
     * 查看订单是否可以进行支付
     * @param id 订单id
     * @return
     */
    Boolean orderCanBePaid(Long id);

    /**
     * 根据已完成的支付数额修改订单状态
     * @param id 订单id
     * @param amount 已支付金额
     */
    void checkOrderPaid(Long id, Long amount);

    /**
     * 获取订单的价格(可以被返款的部分)
     * @param id
     * @return
     */
    Long getOrderCanBeRefundPrice(Long id);
}

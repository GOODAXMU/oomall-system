package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.Payment;

import javax.validation.constraints.NotNull;

/**
 * @author Wang Zhizhou
 * create 2020/11/26
 * modified 2020/12/15
 */
public interface IExternalPayment {
    /**
     * 提供支付的接口, 根据 payment 信息完成支付
     * @param payment 支付信息
     * @return
     */
    Boolean pay(@NotNull Payment payment);

    /**
     * 提供返款的接口, 返款本质也认为是一种反向的支付
     * @param payment 反向支付信息
     * @return
     */
    Boolean refund(@NotNull Payment payment);

    /**
     * 返回该支付的渠道
     * @return
     */
    String getPattern();

    /**
     * 返回支付的渠道名称
     * @return
     */
    String getPatternName();
}

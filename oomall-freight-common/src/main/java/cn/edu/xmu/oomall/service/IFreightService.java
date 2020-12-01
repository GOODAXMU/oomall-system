package cn.edu.xmu.oomall.service;


import cn.edu.xmu.oomall.dto.CalculateFreightRequest;
import cn.edu.xmu.oomall.vo.Reply;

import java.util.List;


/**
 * @author zhibin lan
 * @date 2020-11-30
 */
public interface IFreightService {

    /**
     * 计算普通订单运费
     *
     * @param calculateFreightRequestList 由skuId和counts数量组成的请求对象
     * @param rid                         地区码
     * @return Long 运费
     */
    Reply<Long> calFreight(List<CalculateFreightRequest> calculateFreightRequestList, Long rid);

    /**
     * 计算秒杀活动订单运费
     *
     * @param calculateFreightRequest 由skuId和counts数量组成的请求对象
     * @param rid                     地区码
     * @return Long 运费
     */
    public Reply<Long> calActivityFreight(CalculateFreightRequest calculateFreightRequest, Long rid);
}

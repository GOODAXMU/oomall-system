package cn.edu.xmu.oomall.service;


import cn.edu.xmu.oomall.dto.CalculateFreightRequest;
import cn.edu.xmu.oomall.vo.Reply;

import java.util.List;

public interface IFreightService {

    /**
     * @param calculateFreightRequestList 由skuId和counts数量组成的请求对象
     * @param rid                         地区码
     * @return Long 运费
     */
    Reply<Long> calculateFreight(List<CalculateFreightRequest> calculateFreightRequestList, Long rid);

}

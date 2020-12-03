package cn.edu.xmu.ooamll.service;

import cn.edu.xmu.ooamll.dto.AfterSaleDto;

/**
 * @author xincong yao
 * @date 2020-11-30
 */
public interface IDubboOrderService {

	AfterSaleDto getAfterSaleByOrderItemId(Long orderItemId);
}

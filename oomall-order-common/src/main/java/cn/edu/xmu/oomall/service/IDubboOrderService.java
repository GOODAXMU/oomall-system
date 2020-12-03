package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.AfterSaleDto;

/**
 * @author xincong yao
 * @date 2020-11-30
 */
public interface IDubboOrderService {

	AfterSaleDto getAfterSaleByOrderItemId(Long orderItemId);
}

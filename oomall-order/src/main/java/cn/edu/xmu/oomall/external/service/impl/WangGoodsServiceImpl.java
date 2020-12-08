package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IGoodsService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-12-8
 */
@Component
public class WangGoodsServiceImpl implements IGoodsService {

	// todo 外部服务未配置
	// @DubboReference(version = "${oomall.goods.version}", cache = "false", async = true, timeout = 5000)
	private cn.xmu.edu.goods.client.IGoodsService goodsService;

	@Override
	public Long getPrice(Long skuId) {
		return goodsService.getPrice(skuId);
	}
}

package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Component
public class SimulatedCustomerServiceImpl implements ICustomerService {

	// 由外部模块实现并提供接口
	// @DubboReference(version = "${}", cache = "false", async = true, timeout = 11000)
	// private ExternalCustomerServiceInterface customerService;

	@Override
	public Customer getCustomer(Long customerId) {
		// ExCustomer ec = customerService.getExCustomer(customerId);
		// Customer lc = toLocalCustomer(ec);
		// return lc;
		Customer c = new Customer();
		c.setId(customerId);
		c.setRealName("wilson");
		c.setUserName("abee");
		return c;
	}
}

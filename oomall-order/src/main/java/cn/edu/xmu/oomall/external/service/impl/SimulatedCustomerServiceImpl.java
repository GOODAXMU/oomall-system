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

	@Override
	public Customer getCustomer(Long customerId) {
		Customer c = new Customer();
		c.setId(customerId);
		c.setRealName("wilson");
		c.setUserName("abee");
		return c;
	}
}

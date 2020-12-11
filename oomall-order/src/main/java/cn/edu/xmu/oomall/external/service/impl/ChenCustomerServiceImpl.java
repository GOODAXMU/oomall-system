package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.bo.Customer;
import cn.edu.xmu.oomall.external.service.ICustomerService;
import cn.edu.xmu.oomall.other.dto.CustomerDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-12-10
 */
@Component
public class ChenCustomerServiceImpl implements ICustomerService {

	@DubboReference(version = "${oomall.external.customer-service.version}", check = false)
	private cn.edu.xmu.oomall.other.impl.ICustomerService customerService;

	@Override
	public Customer getCustomer(Long customerId) {
		CustomerDTO dto = customerService.getCustomer(customerId);
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setUserName(dto.getUserName());
		customer.setRealName(dto.getRealName());
		return customer;
	}
}

package cn.edu.xmu.oomall.external.service;

import cn.edu.xmu.oomall.bo.Customer;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
public interface ICustomerService {

	/**
	 * 获取customer业务对象
	 * @param customerId
	 * @return
	 */
	Customer getCustomer(Long customerId);
}

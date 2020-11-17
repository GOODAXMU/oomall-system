package cn.edu.xmu.oomall.dao;

import cn.edu.xmu.oomall.bo.Order;
import org.springframework.stereotype.Component;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class OrderDao {

	public Order saveOrder(Order order) {
		return order;
	}
}

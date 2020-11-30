package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.bo.Order;
import cn.edu.xmu.oomall.vo.Reply;

import java.util.concurrent.ExecutionException;

/**
 * @author xincong yao
 * @date 2020-11-28
 */
public interface IOrderService {

	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	Reply<Order> createOrder(Order order) throws ExecutionException, InterruptedException;
}

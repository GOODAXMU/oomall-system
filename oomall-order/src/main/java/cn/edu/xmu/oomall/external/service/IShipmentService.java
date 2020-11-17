package cn.edu.xmu.oomall.external.service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public interface IShipmentService {

	/**
	 * 提交快递申请
	 * @param orderSn
	 * @return
	 */
	Map<String, Object> createShipment(String orderSn);

	/**
	 * 获取快递单号
	 * @param map
	 * @return
	 */
	String getShipmentSn(Map<String, Object> map);

	/**
	 * 获取发货时间
	 * @param map
	 * @return
	 */
	LocalDateTime getConfirmTime(Map<String, Object> map);

}

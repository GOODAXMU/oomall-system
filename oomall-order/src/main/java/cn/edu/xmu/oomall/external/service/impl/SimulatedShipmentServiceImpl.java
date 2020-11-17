package cn.edu.xmu.oomall.external.service.impl;

import cn.edu.xmu.oomall.external.service.IShipmentService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
@Component
public class SimulatedShipmentServiceImpl implements IShipmentService {

	private static final String SHIPMENT_SN = "sn";
	private static final String CONFIRM_TIME = "confirmTime";

	@Override
	public Map<String, Object> createShipment(String orderSn) {
		Map<String, Object> map = new HashMap<>(2);
		map.put(SHIPMENT_SN, "1100011912");
		map.put(CONFIRM_TIME, LocalDateTime.now());
		return map;
	}

	@Override
	public String getShipmentSn(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		return (String) map.get(SHIPMENT_SN);
	}

	@Override
	public LocalDateTime getConfirmTime(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		return (LocalDateTime) map.get(CONFIRM_TIME);
	}
}

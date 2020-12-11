package cn.edu.xmu.oomall.util;

import java.util.UUID;

/**
 * @author xincong yao
 * @date 2020-12-11
 */
public class OrderSnGenerator {

	public static String createAndGetOrderSn() {
		return UUID.randomUUID().toString();
	}
}

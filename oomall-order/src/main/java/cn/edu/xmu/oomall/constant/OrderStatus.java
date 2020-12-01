package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public enum OrderStatus {

	CANCELED(0, "已取消"),
	DEPOSIT_TO_BE_PAID(1, "定金待支付"),
	TO_BE_PAID(2, "待支付"),
	GROUPON_TO_BE_JOIN(3, "待参团"),
	DEPOSIT_PAID(4, "已支付定金"),
	BALANCE_TO_BE_PAID(5, "待支付尾款"),
	NEW(6, "创建订单"),
	PRESALE_TERMINATED(7, "预售中止"),
	GROUPON_JOIN(8, "已参团"),
	GROUPON_THRESHOLD_NOT_REACH(9, "未达到团购门槛"),
	GROUPON_THRESHOLD_REACH(10, "已成团"),
	PAID(11, "已支付"),
	BALANCE_PAID(12, "已支付尾款"),
	REFUNDED(13, "已退款"),
	TERMINATED(14, "订单中止"),
	AFTER_SALE_TO_BE_DELIVERED(15, "售后单待发货"),
	DELIVERED(16, "发货中"),
	ARRIVED(17, "到货"),
	RECEIVED(18, "已签收"),
	;


	private Integer value;
	private String desc;

	OrderStatus(Integer v, String d) {
		value = v;
		desc = d;
	}

	public int value() {
		return this.value;
	}

	public String getDesc() {
		return this.desc;
	}

	public OrderStatus valueOf(int statusCode) {
		OrderStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static OrderStatus resolve(int statusCode) {
		OrderStatus[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			OrderStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

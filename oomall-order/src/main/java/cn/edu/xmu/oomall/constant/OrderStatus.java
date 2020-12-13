package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public enum OrderStatus {


	TO_BE_PAID(1, "待付款"),
	TO_BE_RECEIVED(2, "待收货"),

	COMPLETED(3, "已完成"),
	CANCELED(4, "已取消"),

	NEW(11, "新订单"),
	BALANCE_TO_BE_PAID(12, "待支付尾款"),

	PAID(21, "付款完成"),
	GROUPON_THRESHOLD_TO_BE_REACH(22, "待成团"),
	GROUPON_THRESHOLD_NOT_REACH(23, "未成团"),
	DELIVERED(24, "已发货"),
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

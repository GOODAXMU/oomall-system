package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public enum OrderStatus {

	NEW(0, "新建态"),
	DELIVERED(10, "已发货"),
	RECEIVED(11, "已收货"),
	COMPLETE(12, "已完成"),
	FORBID(-1, "禁止"),
	CANCELED(-2, "已取消"),
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

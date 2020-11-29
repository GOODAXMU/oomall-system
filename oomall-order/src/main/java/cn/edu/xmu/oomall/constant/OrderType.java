package cn.edu.xmu.oomall.constant;

/**
 * @author yaoxi
 */
public enum OrderType {

	NORMAL(0, "普通订单"),
	GROUPON(1, "团购订单"),
	PRESALE(2, "预售订单"),
	;


	private Integer value;
	private String desc;

	OrderType(Integer v, String d) {
		value = v;
		desc = d;
	}

	public int value() {
		return this.value;
	}

	public String getDesc() {
		return this.desc;
	}

	public OrderType valueOf(int type) {
		OrderType t = resolve(type);
		if (t == null) {
			throw new IllegalArgumentException("No matching constant for [" + type + "]");
		} else {
			return t;
		}
	}

	public static OrderType resolve(int type) {
		OrderType[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			OrderType t = var1[var3];
			if (t.value == type) {
				return t;
			}
		}

		return null;
	}
}

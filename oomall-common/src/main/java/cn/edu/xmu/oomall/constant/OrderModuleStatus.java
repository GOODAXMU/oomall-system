package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-10-28
 */
public enum OrderModuleStatus {

	/**
	 * 成功
	 */
	OK(0, "成功"),
	/**
	 * 商品库存不足
	 */
	STOCK_SHORTAGE(900, "商品库存不足"),
	/**
	 * 订单状态禁止
	 */
	ORDER_STATUS_FORBIDDEN(800, "订单状态禁止")
	;

	private final int value;
	private final String reasonPhrase;

	OrderModuleStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int value() {
		return this.value;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public OrderModuleStatus valueOf(int statusCode) {
		OrderModuleStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static OrderModuleStatus resolve(int statusCode) {
		OrderModuleStatus[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			OrderModuleStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

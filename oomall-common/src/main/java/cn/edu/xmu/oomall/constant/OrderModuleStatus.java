package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-10-28
 */
public enum OrderModuleStatus {

	/******************************
	 * 通用状态码
	 ******************************/
	OK(0, "成功"),
	OUT_OF_STOCK(900, "商品库存不足"),


	/******************************
	 * 订单模块状态码
	 ******************************/
	ORDER_FORBID(801,"订单状态禁止"),
	FREIGHT_NAME_EXIST(802,"运费模板名重复"),
	REGION_EXIST(803,"运费模板中该地区已经定义"),
	REFUND_EXCESS(804,"退款金额超过支付金额"),


	/******************************
	 * 自定义状态码
	 ******************************/
	CUSTOMER_NOT_EXIST(599, "用户id不存在")
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

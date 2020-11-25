package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-10-28
 */
public enum ResponseStatus {

	/******************************
	 * 通用状态码
	 ******************************/
	OK(0, "成功"),


	/******************************
	 * 系统级错误
	 ******************************/
	INTERNAL_SERVER_ERR(500,"服务器内部错误"),
	FIELD_INVALID(503,"字段不合法"),
	RESOURCE_ID_NOT_EXIST(504,"操作的资源id不存在"),
	RESOURCE_ID_OUT_OF_SCOPE(505,"操作的资源id不是自己的对象"),
	RESOURCE_FALSIFY(507, "信息签名不正确"),


	/******************************
	 * 订单模块状态码
	 ******************************/
	ORDER_FORBID(801,"订单状态禁止"),
	FREIGHT_NAME_EXIST(802,"运费模板名重复"),
	REGION_EXIST(803,"运费模板中该地区已经定义"),
	REFUND_EXCESS(804,"退款金额超过支付金额"),


	/******************************
	 * 非订单模块状态码
	 ******************************/
	OUT_OF_STOCK(900, "库存不足")
	
	;


	private final int value;
	private final String reasonPhrase;

	ResponseStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int value() {
		return this.value;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public ResponseStatus valueOf(int statusCode) {
		ResponseStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static ResponseStatus resolve(int statusCode) {
		ResponseStatus[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			ResponseStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

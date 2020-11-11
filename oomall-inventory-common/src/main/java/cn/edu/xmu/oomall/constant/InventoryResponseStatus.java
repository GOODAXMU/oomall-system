package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
public enum InventoryResponseStatus {

	OK(10, "成功"),
	ERROR(11, "未知错误"),
	PARAM_ERROR(12, "参数错误"),
	OUT_OF_INVENTORY(13, "库存不足")
	;

	private final int value;
	private final String reasonPhrase;

	InventoryResponseStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int value() {
		return this.value;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public InventoryResponseStatus valueOf(int statusCode) {
		InventoryResponseStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static InventoryResponseStatus resolve(int statusCode) {
		InventoryResponseStatus[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			InventoryResponseStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

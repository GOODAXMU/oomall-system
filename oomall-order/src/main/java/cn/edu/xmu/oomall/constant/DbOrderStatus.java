package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-17
 */
public enum DbOrderStatus {

	NOT_BE_DELETED(0, "未删除"),
	BE_DELETED(1, "已删除"),
	;

	private Integer value;
	private String desc;

	DbOrderStatus(Integer v, String d) {
		value = v;
		desc = d;
	}

	public int value() {
		return this.value;
	}

	public String getDesc() {
		return this.desc;
	}

	public DbOrderStatus valueOf(int statusCode) {
		DbOrderStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static DbOrderStatus resolve(int statusCode) {
		DbOrderStatus[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			DbOrderStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

package cn.edu.xmu.oomall.constant;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
public enum SeckillResponseStatus {

	OK(0, "成功"),
	ERROR(1, "未知错误"),
	FAIL(2, "部分加载失败"),
	SECKILL_NOT_FOUND(3, "秒杀活动不存在"),
	SECKILL_CLOSED(4, "秒杀活动已关闭"),
	PARAM_ERROR(5, "参数错误"),
	OUT_OF_INVENTORY(6, "库存不足")
	;

	private final int value;
	private final String reasonPhrase;

	SeckillResponseStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int value() {
		return this.value;
	}

	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

	public SeckillResponseStatus valueOf(int statusCode) {
		SeckillResponseStatus status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		} else {
			return status;
		}
	}

	public static SeckillResponseStatus resolve(int statusCode) {
		SeckillResponseStatus[] var1 = values();
		int var2 = var1.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			SeckillResponseStatus status = var1[var3];
			if (status.value == statusCode) {
				return status;
			}
		}

		return null;
	}
}

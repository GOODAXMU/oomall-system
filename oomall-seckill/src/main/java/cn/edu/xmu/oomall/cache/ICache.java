package cn.edu.xmu.oomall.cache;

/**
 * @author xincong yao
 * @date 2020-10-31
 */
public interface ICache {

	Long SUCCESS = 0L;

	Long MODIFY_ERROR_CODE = -1L;

	Long FETCH_ERROR_CODE = -2L;

	/**
	 * 数据放入缓存
	 * @param h  一级标识
	 * @param hk 二级标识
	 * @param o  数据
	 */
	void set(Object h, Object hk, Object o);

	/**
	 * 从缓存中获取数据
	 * @param h  一级标识
	 * @param hk 二级标识
	 * @return	 数据
	 */
	Object get(Object h, Object hk);

	/**
	 * 修改数值
	 * @param h  一级标识
	 * @param hk 二级标识
	 * @param v  修改量, 正数表示增加, 负数表示减少
	 * @return   修改后的值, 不存在时返回{@link ICache#MODIFY_ERROR_CODE}
	 */
	Long modify(Object h, Object hk, Long v);

	/**
	 * 取得值, 并移除
	 * @param h  一级标识
	 * @param hk 二级标识
	 * @return   移除前的值
	 */
	Long fetch(Object h, Object hk);

	/**
	 * 删除缓存记录
	 * @param h  一级标识
	 * @param hk 二级标识
	 * @return   成功删除的记录数
	 */
	Long delete(Object h, Object... hk);
}

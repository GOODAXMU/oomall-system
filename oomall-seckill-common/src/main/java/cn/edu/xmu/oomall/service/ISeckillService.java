package cn.edu.xmu.oomall.service;

import cn.edu.xmu.oomall.dto.*;

import java.util.Map;

/**
 * <p>
 *       第一版考虑使用dubbo rpc
 *     商品模块加载或卸载秒杀活动
 *     订单模块下单后加减库存
 *
 *       第二版考虑使用rocketmq
 *     商品模块向消息队列发送加载或卸载秒杀活动的请求
 *     订单模块下单后向消息队列发送加键库存的请求
 * </p>
 *
 * @author xincong yao
 * @date 2020-10-31
 */
public interface ISeckillService {

	/**
	 * 加载一个秒杀活动
	 * 加载时间由调用方决定
	 * 加载时把所有秒杀商品加载到缓存
	 * 正式开始时间由秒杀活动开始时间决定
	 * @param request
	 * @return
	 */
	Map<String, Object> loadSeckill(SeckillLoadRequest request);

	/**
	 * 卸载一个秒杀活动
	 * 卸载时间由调用方决定
	 * 卸载时把缓存中所有的剩余商品库存写回秒杀商品项中
	 * @param request
	 * @return
	 */
	Map<String, Object> unloadSeckill(SeckillUnloadRequest request);

	/**
	 * 修改秒杀活动的状态
	 * @param request
	 * @return
	 */
	Map<String, Object> modifySeckillStatus(SeckillStatusModifyRequest request);

	/**
	 * 修改库存
	 * @param request
	 * @return
	 */
	Map<String, Object> modifyInventory(InventoryModifyRequest request);
}

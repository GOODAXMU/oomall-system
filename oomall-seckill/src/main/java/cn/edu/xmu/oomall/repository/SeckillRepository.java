package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.FlashSalePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
public interface SeckillRepository extends
		JpaRepository<FlashSalePo, Long>,
		JpaSpecificationExecutor<FlashSalePo> {
}

package cn.edu.xmu.oomall.repository;

import cn.edu.xmu.oomall.entity.TaskPo;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-21
 */
public interface TaskRepository extends
		JpaRepository<TaskPo, Long>,
		JpaSpecificationExecutor<TaskPo> {

	@Query(value = "SELECT t FROM TaskPo t WHERE id = :id")
	TaskPo findTaskById(Long id);

	@Query(value = "SELECT t FROM TaskPo t WHERE t.prepareTime <= :endTime AND t.prepareTime >= :beginTime")
	List<TaskPo> findTasksByPrepareTimeBetween(LocalDateTime beginTime, LocalDateTime endTime);
}

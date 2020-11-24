package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class TaskPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long methodId;

	private LocalDateTime startTime;

	private LocalDateTime prepareTime;

	private String topic;

	private String tag;
}

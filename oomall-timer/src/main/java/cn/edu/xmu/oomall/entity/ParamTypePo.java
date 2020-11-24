package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "param_type")
public class ParamTypePo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long methodId;

	private Integer sequence;

	private String className;
}

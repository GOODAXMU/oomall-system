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
@Table(name = "method")
public class MethodPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String className;

	private String methodName;

	private String returnTypeClassName;
}

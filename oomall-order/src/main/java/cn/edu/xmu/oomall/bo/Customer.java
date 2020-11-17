package cn.edu.xmu.oomall.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private Long id;
	private String userName;
	private String realName;
}

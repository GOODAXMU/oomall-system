package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-11-2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillStatusModifyRequest implements Serializable {

	private Long seckillId;
	private Boolean switchOn;
}

package cn.edu.xmu.oomall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xincong yao
 * @date 2020-10-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillUnloadRequest implements Serializable {

	private Long seckillId;
}

package cn.edu.xmu.oomall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xincong yao
 * @date 2020-10-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods_flash_sale")
public class FlashSalePo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime flashDate;

	private Long timeSegId;
}

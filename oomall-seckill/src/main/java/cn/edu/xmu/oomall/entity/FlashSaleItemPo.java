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
@Table(name = "flash_sale_item")
public class FlashSaleItemPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long saleId;

	private Long goodsSkuId;

	private Long price;

	private Integer quantity;

	private LocalDateTime gmtCreate;

	private LocalDateTime gmtModified;

	public FlashSaleItemPo(Long id, Integer quantity) {
		this.id = id;
		this.quantity = quantity;
	}
}

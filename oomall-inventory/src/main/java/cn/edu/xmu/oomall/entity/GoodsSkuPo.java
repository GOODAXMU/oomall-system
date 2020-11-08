package cn.edu.xmu.oomall.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author zhibin lan
 * @date 2020-11-07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods_goods_sku")
public class GoodsSkuPo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long goodsSpuId;

	private Long skuSn;

	private String name;

	private Long originalPrice;

	private String configuration;

	private Long weight;

	private String imageUrl;

	private Long inventory;

	private String detail;

	private Boolean disabled;
}

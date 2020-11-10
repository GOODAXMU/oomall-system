package cn.edu.xmu.oomall.repository.util;

import cn.edu.xmu.oomall.entity.GoodsSkuPo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-10
 * <p>
 * 不支持根据id查询
 * 构造复杂对象来执行id查询性能低
 * 鼓励使用hibernate接口或者手写query
 */
public class SpecificationFactory {

	public static Specification<GoodsSkuPo> get(GoodsSkuPo goodsSku) {
		return (Specification<GoodsSkuPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (goodsSku.getGoodsSpuId() != null) {
				predicates.add(builder.equal(root.get("goodsSpuId"), goodsSku.getGoodsSpuId()));
			}
			if (goodsSku.getSkuSn() != null) {
				predicates.add(builder.equal(root.get("skuSn"), goodsSku.getSkuSn()));
			}
			if (goodsSku.getName() != null) {
				predicates.add(builder.equal(root.get("name"), goodsSku.getName()));
			}
			if (goodsSku.getOriginalPrice() != null) {
				predicates.add(builder.equal(root.get("originalPrice"), goodsSku.getOriginalPrice()));
			}
			if (goodsSku.getConfiguration() != null) {
				predicates.add(builder.equal(root.get("configuration"), goodsSku.getConfiguration()));
			}
			if (goodsSku.getWeight() != null) {
				predicates.add(builder.equal(root.get("weight"), goodsSku.getWeight()));
			}
			if (goodsSku.getImageUrl() != null) {
				predicates.add(builder.equal(root.get("imageUrl"), goodsSku.getImageUrl()));
			}
			if (goodsSku.getInventory() != null) {
				predicates.add(builder.equal(root.get("inventory"), goodsSku.getInventory()));
			}
			if (goodsSku.getDetail() != null) {
				predicates.add(builder.equal(root.get("detail"), goodsSku.getDetail()));
			}
			if (goodsSku.getDisabled() != null) {
				predicates.add(builder.equal(root.get("disabled"), goodsSku.getDisabled()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
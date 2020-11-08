package cn.edu.xmu.oomall.repository.util;

import cn.edu.xmu.oomall.entity.FlashSaleItemPo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-1
 */
public class SpecificationFactory {

	public static Specification<FlashSaleItemPo> get(FlashSaleItemPo seckillItem) {
		return (Specification<FlashSaleItemPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (seckillItem.getSaleId() != null) {
				predicates.add(builder.equal(root.get("saleId"), seckillItem.getSaleId()));
			}
			if (seckillItem.getPrice() != null) {
				predicates.add(builder.equal(root.get("price"), seckillItem.getPrice()));
			}
			if (seckillItem.getQuantity() != null) {
				predicates.add(builder.equal(root.get("quantity"), seckillItem.getQuantity()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}


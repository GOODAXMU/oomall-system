package cn.edu.xmu.oomall.repository.util;

import cn.edu.xmu.oomall.entity.FreightModelPo;
import cn.edu.xmu.oomall.entity.PieceFreightModelPo;
import cn.edu.xmu.oomall.entity.WeightFreightModelPo;
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

    public static Specification<FreightModelPo> get(FreightModelPo freightModel) {
        return (Specification<FreightModelPo>) (root, criteriaQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (freightModel.getShopId() != null) {
                predicates.add(builder.equal(root.get("shopId"), freightModel.getShopId()));
            }
            if (freightModel.getName() != null) {
                predicates.add(builder.equal(root.get("name"), freightModel.getName()));
            }
            if (freightModel.getDefaultModel() != null) {
                predicates.add(builder.equal(root.get("defaultModel"), freightModel.getDefaultModel()));
            }
            if (freightModel.getType() != null) {
                predicates.add(builder.equal(root.get("type"), freightModel.getType()));
            }
			if (freightModel.getWeightUnit() != null) {
				predicates.add(builder.equal(root.get("weightUnit"), freightModel.getWeightUnit()));
			}
            if (freightModel.getGmtCreate() != null) {
                predicates.add(builder.equal(root.get("gmtCreated"), freightModel.getGmtCreate()));
            }
            if (freightModel.getGmtModified() != null) {
                predicates.add(builder.equal(root.get("gmtModified"), freightModel.getGmtModified()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<WeightFreightModelPo> get(WeightFreightModelPo weightFreightModel) {
        return (Specification<WeightFreightModelPo>) (root, criteriaQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (weightFreightModel.getFreightModelId() != null) {
                predicates.add(builder.equal(root.get("freightModelId"), weightFreightModel.getFreightModelId()));
            }
            if (weightFreightModel.getFirstWeight() != null) {
                predicates.add(builder.equal(root.get("firstWeight"), weightFreightModel.getFirstWeight()));
            }
            if (weightFreightModel.getFirstWeightFreight() != null) {
                predicates.add(builder.equal(root.get("firstWeightFreight"), weightFreightModel.getFirstWeightFreight()));
            }
            if (weightFreightModel.getTenPrice() != null) {
                predicates.add(builder.equal(root.get("tenPrice"), weightFreightModel.getTenPrice()));
            }
            if (weightFreightModel.getFiftyPrice() != null) {
                predicates.add(builder.equal(root.get("fiftyPrice"), weightFreightModel.getFiftyPrice()));
            }
            if (weightFreightModel.getHundredPrice() != null) {
                predicates.add(builder.equal(root.get("hundredPrice"), weightFreightModel.getHundredPrice()));
            }
            if (weightFreightModel.getTrihunPrice() != null) {
                predicates.add(builder.equal(root.get("trihunPrice"), weightFreightModel.getTrihunPrice()));
            }
            if (weightFreightModel.getAbovePrice() != null) {
                predicates.add(builder.equal(root.get("abovePrice"), weightFreightModel.getAbovePrice()));
            }
            if (weightFreightModel.getRegionId() != null) {
                predicates.add(builder.equal(root.get("regionId"), weightFreightModel.getRegionId()));
            }
            if (weightFreightModel.getGmtCreate() != null) {
                predicates.add(builder.equal(root.get("gmtCreated"), weightFreightModel.getGmtCreate()));
            }
            if (weightFreightModel.getGmtModified() != null) {
                predicates.add(builder.equal(root.get("gmtModified"), weightFreightModel.getGmtModified()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<PieceFreightModelPo> get(PieceFreightModelPo pieceFreightModel) {
        return (Specification<PieceFreightModelPo>) (root, criteriaQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pieceFreightModel.getFreightModelId() != null) {
                predicates.add(builder.equal(root.get("freightModelId"), pieceFreightModel.getFreightModelId()));
            }
            if (pieceFreightModel.getFirstItems() != null) {
                predicates.add(builder.equal(root.get("firstItems"), pieceFreightModel.getFirstItems()));
            }
            if (pieceFreightModel.getFirstItemsPrice() != null) {
                predicates.add(builder.equal(root.get("firstItemsPrice"), pieceFreightModel.getFirstItemsPrice()));
            }
            if (pieceFreightModel.getAdditionalItems() != null) {
                predicates.add(builder.equal(root.get("additionalItems"), pieceFreightModel.getAdditionalItems()));
            }
            if (pieceFreightModel.getAdditionalItemsPrice() != null) {
                predicates.add(builder.equal(root.get("additionalItemsPrice"), pieceFreightModel.getAdditionalItemsPrice()));
            }
            if (pieceFreightModel.getRegionId() != null) {
                predicates.add(builder.equal(root.get("regionId"), pieceFreightModel.getRegionId()));
            }
            if (pieceFreightModel.getGmtCreate() != null) {
                predicates.add(builder.equal(root.get("gmtCreated"), pieceFreightModel.getGmtCreate()));
            }
            if (pieceFreightModel.getGmtModified() != null) {
                predicates.add(builder.equal(root.get("gmtModified"), pieceFreightModel.getGmtModified()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

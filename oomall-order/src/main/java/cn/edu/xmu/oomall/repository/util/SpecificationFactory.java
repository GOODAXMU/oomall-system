package cn.edu.xmu.oomall.repository.util;

import cn.edu.xmu.oomall.entity.OrderItemPo;
import cn.edu.xmu.oomall.entity.OrderPo;
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

	public static Specification<OrderPo> get(OrderPo order) {
		return (Specification<OrderPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (order.getCustomerId() != null) {
				predicates.add(builder.equal(root.get("customerId"), order.getCustomerId()));
			}
			if (order.getShopId() != null) {
				predicates.add(builder.equal(root.get("shopId"), order.getShopId()));
			}
			if (order.getOrderSn() != null) {
				predicates.add(builder.equal(root.get("orderSn"), order.getOrderSn()));
			}
			if (order.getPid() != null) {
				predicates.add(builder.equal(root.get("pid"), order.getPid()));
			}
			if (order.getConsignee() != null) {
				predicates.add(builder.equal(root.get("consignee"), order.getConsignee()));
			}
			if (order.getRegionId() != null) {
				predicates.add(builder.equal(root.get("regionId"), order.getRegionId()));
			}
			if (order.getAddress() != null) {
				predicates.add(builder.equal(root.get("address"), order.getAddress()));
			}
			if (order.getMobile() != null) {
				predicates.add(builder.equal(root.get("mobile"), order.getMobile()));
			}
			if (order.getMessage() != null) {
				predicates.add(builder.equal(root.get("message"), order.getMessage()));
			}
			if (order.getOrderType() != null) {
				predicates.add(builder.equal(root.get("orderType"), order.getOrderType()));
			}
			if (order.getFreightPrice() != null) {
				predicates.add(builder.equal(root.get("freightPrice"), order.getFreightPrice()));
			}
			if (order.getCouponId() != null) {
				predicates.add(builder.equal(root.get("couponId"), order.getCouponId()));
			}
			if (order.getCouponActivityId() != null) {
				predicates.add(builder.equal(root.get("couponActivityId"), order.getCouponActivityId()));
			}
			if (order.getDiscountPrice() != null) {
				predicates.add(builder.equal(root.get("discountPrice"), order.getDiscountPrice()));
			}
			if (order.getOriginPrice() != null) {
				predicates.add(builder.equal(root.get("originPrice"), order.getOriginPrice()));
			}
			if (order.getPresaleId() != null) {
				predicates.add(builder.equal(root.get("presaleId"), order.getPresaleId()));
			}
			if (order.getGrouponDiscount() != null) {
				predicates.add(builder.equal(root.get("grouponDiscount"), order.getGrouponDiscount()));
			}
			if (order.getRebateNum() != null) {
				predicates.add(builder.equal(root.get("rebateNum"), order.getRebateNum()));
			}
			if (order.getConfirmTime() != null) {
				predicates.add(builder.equal(root.get("confirmTime"), order.getConfirmTime()));
			}
			if (order.getShipmentSn() != null) {
				predicates.add(builder.equal(root.get("shipmentSn"), order.getShipmentSn()));
			}
			if (order.getState() != null) {
				predicates.add(builder.equal(root.get("state"), order.getState()));
			}
			if (order.getSubState() != null) {
				predicates.add(builder.equal(root.get("subState"), order.getSubState()));
			}
			if (order.getBeDeleted() != null) {
				predicates.add(builder.equal(root.get("beDeleted"), order.getBeDeleted()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

	public static Specification<OrderItemPo> get(OrderItemPo orderItem) {
		return (Specification<OrderItemPo>) (root, criteriaQuery, builder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (orderItem.getOrderId() != null) {
				predicates.add(builder.equal(root.get("orderId"), orderItem.getOrderId()));
			}
			if (orderItem.getGoodsSkuId() != null) {
				predicates.add(builder.equal(root.get("goodsSkuId"), orderItem.getGoodsSkuId()));
			}
			if (orderItem.getQuantity() != null) {
				predicates.add(builder.equal(root.get("quantity"), orderItem.getQuantity()));
			}
			if (orderItem.getPrice() != null) {
				predicates.add(builder.equal(root.get("price"), orderItem.getPrice()));
			}
			if (orderItem.getDiscount() != null) {
				predicates.add(builder.equal(root.get("discount"), orderItem.getDiscount()));
			}
			if (orderItem.getName() != null) {
				predicates.add(builder.equal(root.get("name"), orderItem.getName()));
			}
			if (orderItem.getCouponId() != null) {
				predicates.add(builder.equal(root.get("couponId"), orderItem.getCouponId()));
			}
			if (orderItem.getCouponActivityId() != null) {
				predicates.add(builder.equal(root.get("couponActivityId"), orderItem.getCouponActivityId()));
			}
			if (orderItem.getBeSharedId() != null) {
				predicates.add(builder.equal(root.get("beSharedId"), orderItem.getBeSharedId()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}

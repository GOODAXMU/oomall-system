CREATE TABLE `goods_brand` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_comment` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`goods_sku_id` bigint(20) NULL DEFAULT NULL,

`orderitem_id` bigint(20) NULL DEFAULT NULL,

`type` tinyint(4) NULL DEFAULT NULL,

`content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_coupon` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`coupon_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`customer_id` bigint(20) NULL DEFAULT NULL,

`activity_id` bigint(20) NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_coupon_activity` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`coupon_time` datetime NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`valid_term` tinyint(4) NULL DEFAULT NULL,

`image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`strategy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`created_by` bigint(20) NULL DEFAULT NULL,

`modi_by` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_coupon_spu` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`activity_id` bigint(20) NULL DEFAULT NULL,

`spu_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_flash_sale` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`flash_date` datetime NULL DEFAULT NULL,

`time_seg_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_flash_sale_item` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`sale_id` bigint(20) NULL DEFAULT NULL,

`goods_sku_id` bigint(20) NULL DEFAULT NULL,

`price` decimal(10,2) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_float_price` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`goods_sku_id` bigint(20) NULL DEFAULT NULL,

`activity_price` decimal(10,2) NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`created_by` bigint(20) NULL DEFAULT NULL,

`invalid_by` bigint(20) NULL DEFAULT NULL,

`valid` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_goods_category` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`pid` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_goods_sku` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`sku_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`original_price` decimal(10,2) NULL DEFAULT NULL,

`configuration` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`weight` decimal(10,2) NULL DEFAULT NULL,

`image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`inventory` int(11) NULL DEFAULT NULL,

`detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`disabled` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_goods_spu` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`brand_id` bigint(20) NULL DEFAULT NULL,

`category_id` bigint(20) NULL DEFAULT NULL,

`freight_id` bigint(20) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`goods_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`spec` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`disabled` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_groupon_activity` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`strategy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_presale_activity` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`pay_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`advance_pay_price` decimal(10,2) NULL DEFAULT NULL,

`rest_pay_price` decimal(10,2) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `goods_shop` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_freight_model` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`shop_id` bigint(20) NULL DEFAULT NULL,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`default` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`type` tinyint(4) NULL DEFAULT NULL,

`unit` int(11) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_order` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`order_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`pid` bigint(20) NULL DEFAULT NULL,

`consignee` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`region_id` bigint(20) NULL DEFAULT NULL,

`address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`mobile` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`order_type` tinyint(4) NULL DEFAULT NULL,

`freight_price` decimal(10,2) NULL DEFAULT NULL,

`coupon_id` bigint(20) NULL DEFAULT NULL,

`coupon_activity_id` bigint(20) NULL DEFAULT NULL,

`discount_price` decimal(10,2) NULL DEFAULT NULL,

`origin_price` decimal(10,2) NULL DEFAULT NULL,

`presale_id` bigint(20) NULL DEFAULT NULL,

`groupon_discount` decimal(10,2) NULL DEFAULT NULL,

`rebate_num` int(11) NULL DEFAULT NULL,

`confirm_time` datetime NULL DEFAULT NULL,

`shipment_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`substate` tinyint(4) NULL DEFAULT NULL,

`be_deleted` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_order_item` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`order_id` bigint(20) NULL DEFAULT NULL,

`goods_sku_id` bigint(20) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`price` decimal(10,2) NULL DEFAULT NULL,

`discount` decimal(10,2) NULL DEFAULT NULL,

`name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`coupon_id` bigint(20) NULL DEFAULT NULL,

`coupon_activity_id` bigint(20) NULL DEFAULT NULL,

`be_share_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_payment` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`amout` decimal(10,2) NULL DEFAULT NULL,

`actual_amount` decimal(10,2) NULL DEFAULT NULL,

`payment_pattern` tinyint(4) NULL DEFAULT NULL,

`pay_time` datetime NULL DEFAULT NULL,

`pay_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`order_id` bigint(20) NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_piece_freight_model` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`freight_model_id` bigint(20) NULL DEFAULT NULL,

`first_items` int(11) NULL DEFAULT NULL,

`first_items_price` decimal(10,2) NULL DEFAULT NULL,

`additional_items` int(11) NULL DEFAULT NULL,

`additional_items_price` decimal(10,2) NULL DEFAULT NULL,

`region_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_refund` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`payment_id` bigint(20) NULL DEFAULT NULL,

`amout` decimal(10,2) NULL DEFAULT NULL,

`pay_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`bill_id` bigint(20) NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `order_weight_freight_model` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`freight_model_id` bigint(20) NULL DEFAULT NULL,

`first_weight` decimal(10,2) NULL DEFAULT NULL,

`first_weight_freight` decimal(10,2) NULL DEFAULT NULL,

`ten_price` decimal(10,2) NULL DEFAULT NULL,

`fifty_price` decimal(10,2) NULL DEFAULT NULL,

`hundred_price` decimal(10,2) NULL DEFAULT NULL,

`trihun_price` decimal(10,2) NULL DEFAULT NULL,

`above_price` decimal(10,2) NULL DEFAULT NULL,

`region_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_address` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`region_id` bigint(20) NULL DEFAULT NULL,

`detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`consignee` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`mobile` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`default` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_advertisement` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`seg_id` bigint(20) NULL DEFAULT NULL,

`link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`weight` int(11) NULL DEFAULT NULL,

`begin_date` datetime NULL DEFAULT NULL,

`end_date` datetime NULL DEFAULT NULL,

`repeat` tinyint(4) NULL DEFAULT NULL,

`message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`default` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_aftersale_service` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`order_item_id` bigint(20) NULL DEFAULT NULL,

`customer_id` bigint(20) NULL DEFAULT NULL,

`shop_id` bigint(20) NULL DEFAULT NULL,

`service_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`type` tinyint(4) NULL DEFAULT NULL,

`reason` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`conclusion` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`refund` decimal(10,2) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`region_id` bigint(20) NULL DEFAULT NULL,

`detail` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`consignee` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`mobile` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`customer_log_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`shop_log_sn` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`be_deleted` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_be_share` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`sharer_id` bigint(20) NULL DEFAULT NULL,

`share_id` bigint(20) NULL DEFAULT NULL,

`customer_id` bigint(20) NULL DEFAULT NULL,

`order_item_id` bigint(20) NULL DEFAULT NULL,

`rebate` int(11) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_customer` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`gender` tinyint(4) NULL DEFAULT NULL,

`birthday` datetime NULL DEFAULT NULL,

`point` int(11) NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

`email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`mobile` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`be_deleted` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_favourite_goods` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_foot_print` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_region` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`pid` bigint(20) NULL DEFAULT NULL,

`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`postal_code` bigint(20) NULL DEFAULT NULL,

`state` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_share` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`sharer_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_share_activity` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`shop_id` bigint(20) NULL DEFAULT NULL,

`goods_spu_id` bigint(20) NULL DEFAULT NULL,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`strategy` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,

`be_deleted` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_shopping_cart` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`customer_id` bigint(20) NULL DEFAULT NULL,

`goods_sku_id` bigint(20) NULL DEFAULT NULL,

`quantity` int(11) NULL DEFAULT NULL,

`price` decimal(10,2) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;



CREATE TABLE `other_time_segment` (

`id` bigint(20) NOT NULL AUTO_INCREMENT,

`begin_time` datetime NULL DEFAULT NULL,

`end_time` datetime NULL DEFAULT NULL,

`type` tinyint(4) NULL DEFAULT NULL,

PRIMARY KEY (`id`) 

)

ENGINE=InnoDB

DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

AUTO_INCREMENT=1
;




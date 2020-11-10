-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: oomall-1
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--

--
-- Table structure for table `good_brand`
--

DROP TABLE IF EXISTS `good_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_brand`
--

LOCK TABLES `good_brand` WRITE;
/*!40000 ALTER TABLE `good_brand` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_comment`
--

DROP TABLE IF EXISTS `good_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `orderitem_id` bigint(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_comment`
--

LOCK TABLES `good_comment` WRITE;
/*!40000 ALTER TABLE `good_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_coupon`
--

DROP TABLE IF EXISTS `good_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_sn` varchar(128) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `activity_id` bigint(20) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_coupon`
--

LOCK TABLES `good_coupon` WRITE;
/*!40000 ALTER TABLE `good_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_coupon_activity`
--

DROP TABLE IF EXISTS `good_coupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_coupon_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `coupon_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `valid_term` tinyint(4) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `modi_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_coupon_activity`
--

LOCK TABLES `good_coupon_activity` WRITE;
/*!40000 ALTER TABLE `good_coupon_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_coupon_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_coupon_spu`
--

DROP TABLE IF EXISTS `good_coupon_spu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_coupon_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) DEFAULT NULL,
  `spu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_coupon_spu`
--

LOCK TABLES `good_coupon_spu` WRITE;
/*!40000 ALTER TABLE `good_coupon_spu` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_coupon_spu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_flash_sale`
--

DROP TABLE IF EXISTS `good_flash_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_flash_sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `flash_date` datetime DEFAULT NULL,
  `time_seg_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_flash_sale`
--

LOCK TABLES `good_flash_sale` WRITE;
/*!40000 ALTER TABLE `good_flash_sale` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_flash_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_flash_sale_item`
--

DROP TABLE IF EXISTS `good_flash_sale_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_flash_sale_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sale_id` bigint(20) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `price` bigint(10) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_flash_sale_item`
--

LOCK TABLES `good_flash_sale_item` WRITE;
/*!40000 ALTER TABLE `good_flash_sale_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_flash_sale_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_float_price`
--

DROP TABLE IF EXISTS `good_float_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_float_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `activity_price` bigint(10) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `invalid_by` bigint(20) DEFAULT NULL,
  `valid` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_float_price`
--

LOCK TABLES `good_float_price` WRITE;
/*!40000 ALTER TABLE `good_float_price` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_float_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_goods_category`
--

DROP TABLE IF EXISTS `good_goods_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_goods_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_goods_category`
--

LOCK TABLES `good_goods_category` WRITE;
/*!40000 ALTER TABLE `good_goods_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_goods_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_goods_sku`
--

DROP TABLE IF EXISTS `good_goods_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_goods_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `sku_sn` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `original_price` bigint(10) DEFAULT NULL,
  `configuration` varchar(500) DEFAULT NULL,
  `weight` bigint(10) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `inventory` int(11) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `disabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_goods_sku`
--

LOCK TABLES `good_goods_sku` WRITE;
/*!40000 ALTER TABLE `good_goods_sku` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_goods_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_goods_spu`
--

DROP TABLE IF EXISTS `good_goods_spu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_goods_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `brand_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `freight_id` bigint(20) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `goods_sn` varchar(128) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `spec` varchar(500) DEFAULT NULL,
  `disabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_goods_spu`
--

LOCK TABLES `good_goods_spu` WRITE;
/*!40000 ALTER TABLE `good_goods_spu` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_goods_spu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_groupon_activity`
--

DROP TABLE IF EXISTS `good_groupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_groupon_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_groupon_activity`
--

LOCK TABLES `good_groupon_activity` WRITE;
/*!40000 ALTER TABLE `good_groupon_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_groupon_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_presale_activity`
--

DROP TABLE IF EXISTS `good_presale_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_presale_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `advance_pay_price` bigint(10) DEFAULT NULL,
  `rest_pay_price` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_presale_activity`
--

LOCK TABLES `good_presale_activity` WRITE;
/*!40000 ALTER TABLE `good_presale_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_presale_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_shop`
--

DROP TABLE IF EXISTS `good_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `good_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_shop`
--

LOCK TABLES `good_shop` WRITE;
/*!40000 ALTER TABLE `good_shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_freight_model`
--

DROP TABLE IF EXISTS `order_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_freight_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `default_model` varchar(64) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_freight_model`
--

LOCK TABLES `order_freight_model` WRITE;
/*!40000 ALTER TABLE `order_freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_freight_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_order`
--

DROP TABLE IF EXISTS `order_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `order_sn` varchar(128) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `consignee` varchar(64) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `order_type` tinyint(4) DEFAULT NULL,
  `freight_price` bigint(10) DEFAULT NULL,
  `coupon_id` bigint(20) DEFAULT NULL,
  `coupon_activity_id` bigint(20) DEFAULT NULL,
  `discount_price` bigint(10) DEFAULT NULL,
  `origin_price` bigint(10) DEFAULT NULL,
  `presale_id` bigint(20) DEFAULT NULL,
  `groupon_discount` bigint(10) DEFAULT NULL,
  `rebate_num` int(11) DEFAULT NULL,
  `confirm_time` datetime DEFAULT NULL,
  `shipment_sn` varchar(128) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `substate` tinyint(4) DEFAULT NULL,
  `be_deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_order`
--

LOCK TABLES `order_order` WRITE;
/*!40000 ALTER TABLE `order_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_order_item`
--

DROP TABLE IF EXISTS `order_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` bigint(10) DEFAULT NULL,
  `discount` bigint(10) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `coupon_id` bigint(20) DEFAULT NULL,
  `coupon_activity_id` bigint(20) DEFAULT NULL,
  `be_share_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_order_item`
--

LOCK TABLES `order_order_item` WRITE;
/*!40000 ALTER TABLE `order_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_payment`
--

DROP TABLE IF EXISTS `order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amout` bigint(10) DEFAULT NULL,
  `actual_amount` bigint(10) DEFAULT NULL,
  `payment_pattern` tinyint(4) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `pay_sn` varchar(128) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_payment`
--

LOCK TABLES `order_payment` WRITE;
/*!40000 ALTER TABLE `order_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_piece_freight_model`
--

DROP TABLE IF EXISTS `order_piece_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_piece_freight_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint(20) DEFAULT NULL,
  `first_items` int(11) DEFAULT NULL,
  `first_items_price` bigint(10) DEFAULT NULL,
  `additional_items` int(11) DEFAULT NULL,
  `additional_items_price` bigint(10) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_piece_freight_model`
--

LOCK TABLES `order_piece_freight_model` WRITE;
/*!40000 ALTER TABLE `order_piece_freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_piece_freight_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_refund`
--

DROP TABLE IF EXISTS `order_refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_id` bigint(20) DEFAULT NULL,
  `amout` bigint(10) DEFAULT NULL,
  `pay_sn` varchar(128) DEFAULT NULL,
  `bill_id` bigint(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_refund`
--

LOCK TABLES `order_refund` WRITE;
/*!40000 ALTER TABLE `order_refund` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_weight_freight_model`
--

DROP TABLE IF EXISTS `order_weight_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_weight_freight_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint(20) DEFAULT NULL,
  `first_weight` bigint(10) DEFAULT NULL,
  `first_weight_freight` bigint(10) DEFAULT NULL,
  `ten_price` bigint(10) DEFAULT NULL,
  `fifty_price` bigint(10) DEFAULT NULL,
  `hundred_price` bigint(10) DEFAULT NULL,
  `trihun_price` bigint(10) DEFAULT NULL,
  `above_price` bigint(10) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_weight_freight_model`
--

LOCK TABLES `order_weight_freight_model` WRITE;
/*!40000 ALTER TABLE `order_weight_freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_weight_freight_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_address`
--

DROP TABLE IF EXISTS `other_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `consignee` varchar(64) DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `be_default` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_address`
--

LOCK TABLES `other_address` WRITE;
/*!40000 ALTER TABLE `other_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_advertisement`
--

DROP TABLE IF EXISTS `other_advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_advertisement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seg_id` bigint(20) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `begin_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `repeat` tinyint(4) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `be_default` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_advertisement`
--

LOCK TABLES `other_advertisement` WRITE;
/*!40000 ALTER TABLE `other_advertisement` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_aftersale_service`
--

DROP TABLE IF EXISTS `other_aftersale_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_aftersale_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_item_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `service_sn` varchar(128) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `reason` varchar(500) DEFAULT NULL,
  `conclusion` varchar(500) DEFAULT NULL,
  `refund` bigint(10) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `consignee` varchar(64) DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `customer_log_sn` varchar(128) DEFAULT NULL,
  `shop_log_sn` varchar(128) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `be_deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_aftersale_service`
--

LOCK TABLES `other_aftersale_service` WRITE;
/*!40000 ALTER TABLE `other_aftersale_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_aftersale_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_be_share`
--

DROP TABLE IF EXISTS `other_be_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_be_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `sharer_id` bigint(20) DEFAULT NULL,
  `share_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `order_item_id` bigint(20) DEFAULT NULL,
  `rebate` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_be_share`
--

LOCK TABLES `other_be_share` WRITE;
/*!40000 ALTER TABLE `other_be_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_be_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_customer`
--

DROP TABLE IF EXISTS `other_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `be_deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_customer`
--

LOCK TABLES `other_customer` WRITE;
/*!40000 ALTER TABLE `other_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_favourite_goods`
--

DROP TABLE IF EXISTS `other_favourite_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_favourite_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_favourite_goods`
--

LOCK TABLES `other_favourite_goods` WRITE;
/*!40000 ALTER TABLE `other_favourite_goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_favourite_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_foot_print`
--

DROP TABLE IF EXISTS `other_foot_print`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_foot_print` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_foot_print`
--

LOCK TABLES `other_foot_print` WRITE;
/*!40000 ALTER TABLE `other_foot_print` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_foot_print` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_region`
--

DROP TABLE IF EXISTS `other_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_region` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `postal_code` bigint(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_region`
--

LOCK TABLES `other_region` WRITE;
/*!40000 ALTER TABLE `other_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_share`
--

DROP TABLE IF EXISTS `other_share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_share` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sharer_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_share`
--

LOCK TABLES `other_share` WRITE;
/*!40000 ALTER TABLE `other_share` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_share_activity`
--

DROP TABLE IF EXISTS `other_share_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_share_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `goods_spu_id` bigint(20) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `be_deleted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_share_activity`
--

LOCK TABLES `other_share_activity` WRITE;
/*!40000 ALTER TABLE `other_share_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_share_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_shopping_cart`
--

DROP TABLE IF EXISTS `other_shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `goods_sku_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_shopping_cart`
--

LOCK TABLES `other_shopping_cart` WRITE;
/*!40000 ALTER TABLE `other_shopping_cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `other_time_segment`
--

DROP TABLE IF EXISTS `other_time_segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_time_segment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `other_time_segment`
--

LOCK TABLES `other_time_segment` WRITE;
/*!40000 ALTER TABLE `other_time_segment` DISABLE KEYS */;
/*!40000 ALTER TABLE `other_time_segment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-10  7:57:21

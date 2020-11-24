-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: oomall_order
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
-- Table structure for table `freight_model`
--

DROP TABLE IF EXISTS `freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `freight_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `default_model` varchar(64) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freight_model`
--

LOCK TABLES `freight_model` WRITE;
/*!40000 ALTER TABLE `freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `freight_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
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
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item` (
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
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
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
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piece_freight_model`
--

DROP TABLE IF EXISTS `piece_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piece_freight_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint(20) DEFAULT NULL,
  `first_items` int(11) DEFAULT NULL,
  `first_items_price` bigint(10) DEFAULT NULL,
  `additional_items` int(11) DEFAULT NULL,
  `additional_items_price` bigint(10) DEFAULT NULL,
  `region_id` bigint(20) DEFAULT NULL,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piece_freight_model`
--

LOCK TABLES `piece_freight_model` WRITE;
/*!40000 ALTER TABLE `piece_freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `piece_freight_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_id` bigint(20) DEFAULT NULL,
  `amout` bigint(10) DEFAULT NULL,
  `pay_sn` varchar(128) DEFAULT NULL,
  `bill_id` bigint(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weight_freight_model`
--

DROP TABLE IF EXISTS `weight_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weight_freight_model` (
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
  `gmt_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weight_freight_model`
--

LOCK TABLES `weight_freight_model` WRITE;
/*!40000 ALTER TABLE `weight_freight_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `weight_freight_model` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-18 18:03:24

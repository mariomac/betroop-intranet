-- MySQL dump 10.13  Distrib 5.5.49, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: wmdb
-- ------------------------------------------------------
-- Server version	5.5.49-0+deb8u1

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `postal_code` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `meeting_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_address_meeting1_idx` (`meeting_id`),
  CONSTRAINT `fk_address_meeting1` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_config`
--

DROP TABLE IF EXISTS `app_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text COMMENT '0 disabled, 1 enabled',
  `active` int(11) DEFAULT '1' COMMENT '0 disabled, 1 enabled',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_config`
--

LOCK TABLES `app_config` WRITE;
/*!40000 ALTER TABLE `app_config` DISABLE KEYS */;
INSERT INTO `app_config` VALUES (1,'meeting_home_active',1),(2,'meeting_bar_active',1),(3,'meeting_live_active',1);
/*!40000 ALTER TABLE `app_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '	',
  `send_time` timestamp NULL DEFAULT NULL,
  `receive_time` timestamp NULL DEFAULT NULL,
  `message` text,
  `user_id` bigint(20) NOT NULL,
  `from_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_chat_user1_idx` (`user_id`),
  KEY `fk_chat_user2_idx` (`from_user_id`),
  CONSTRAINT `fk_chat_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_chat_user2` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dades_facturacio`
--

DROP TABLE IF EXISTS `dades_facturacio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dades_facturacio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `CIF` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `rao_social` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dades_facturacio_user1_idx` (`user_id`),
  CONSTRAINT `fk_dades_facturacio_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dades_facturacio`
--

LOCK TABLES `dades_facturacio` WRITE;
/*!40000 ALTER TABLE `dades_facturacio` DISABLE KEYS */;
/*!40000 ALTER TABLE `dades_facturacio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `session_token` varchar(255) DEFAULT NULL,
  `push_token` varchar(255) DEFAULT NULL,
  `device_uuid` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `stoken_expiration_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_device_user1_idx` (`user_id`),
  CONSTRAINT `fk_device_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=465 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `highlighting` int(11) DEFAULT '0' COMMENT '0 NO, 1 SI',
  `photoUrl` varchar(1024) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT 'Futbol' COMMENT 'Descripción del tipo de evento',
  `event_time` timestamp NULL DEFAULT NULL,
  `event_type` int(11) DEFAULT NULL COMMENT '1 - Fútbol, 2 - Baloncesto, 3- Padel, 4 - Tenis, 5 - Motos, 6 - F1, 7 - Otros',
  PRIMARY KEY (`id`),
  KEY `event_type_id_fk1_idx` (`event_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3260 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
  /*!50003 CREATE*/ /*!50017 DEFINER=`betroop`@`%`*/ /*!50003 TRIGGER tags_after_insert_event AFTER INSERT ON event
FOR EACH ROW
  BEGIN
    CALL ADD_NEW_TAGS_FROM_STRING(NEW.`tag`, NEW.`id`);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
  /*!50003 CREATE*/ /*!50017 DEFINER=`betroop`@`%`*/ /*!50003 TRIGGER tags_after_update_event AFTER UPDATE ON event
FOR EACH ROW
  BEGIN
    CALL ADD_NEW_TAGS_FROM_STRING(NEW.`tag`, NEW.`id`);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `event_tag`
--

DROP TABLE IF EXISTS `event_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `event_id` bigint(20) DEFAULT NULL,
  `tag_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `event_id_event_tag_fk1_idx` (`event_id`),
  KEY `tag_id_event_tag_fk1_idx` (`tag_id`),
  CONSTRAINT `event_id_event_tag_fk1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tag_id_event_tag_fk1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4202 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_tag`
--

--
-- Table structure for table `group_header`
--

DROP TABLE IF EXISTS `group_header`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `title` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_user1_idx` (`user_id`),
  CONSTRAINT `fk_group_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_posts`
--

DROP TABLE IF EXISTS `group_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_posts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_posts_group1_idx` (`group_id`),
  KEY `fk_group_posts_user1_idx` (`user_id`),
  CONSTRAINT `fk_group_posts_group1` FOREIGN KEY (`group_id`) REFERENCES `group_header` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_posts_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_posts`
--

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (3185);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_auto_meeting_creation`
--

DROP TABLE IF EXISTS `history_auto_meeting_creation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_auto_meeting_creation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subscription_id` bigint(20) DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `subscription_fk1_idx` (`subscription_id`),
  KEY `event_fk1_idx` (`event_id`),
  CONSTRAINT `event_fk1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `subscription_fk1` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_auto_meeting_creation`
--

LOCK TABLES `history_auto_meeting_creation` WRITE;
/*!40000 ALTER TABLE `history_auto_meeting_creation` DISABLE KEYS */;
INSERT INTO `history_auto_meeting_creation` VALUES (1,1,3246,'2016-08-18 00:00:01'),(2,2,3246,'2016-08-18 00:00:02');
/*!40000 ALTER TABLE `history_auto_meeting_creation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_auto_meeting_task`
--

DROP TABLE IF EXISTS `history_auto_meeting_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_auto_meeting_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `subscription_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `history_subs_fk1_idx` (`subscription_id`),
  CONSTRAINT `history_subs_fk1` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_auto_meeting_task`
--

LOCK TABLES `history_auto_meeting_task` WRITE;
/*!40000 ALTER TABLE `history_auto_meeting_task` DISABLE KEYS */;
INSERT INTO `history_auto_meeting_task` VALUES (1,1,2,'2016-08-13 00:00:01'),(2,1,2,'2016-08-14 00:00:01'),(3,1,2,'2016-08-15 00:00:01'),(4,1,2,'2016-08-16 00:00:02'),(5,1,2,'2016-08-17 00:00:01'),(6,1,2,'2016-08-18 00:00:01'),(7,2,2,'2016-08-18 00:00:02'),(8,1,2,'2016-08-19 00:00:01'),(9,2,2,'2016-08-19 00:00:02'),(10,1,2,'2016-08-20 00:00:01'),(11,2,2,'2016-08-20 00:00:01'),(12,1,2,'2016-08-21 00:00:01'),(13,2,2,'2016-08-21 00:00:02');
/*!40000 ALTER TABLE `history_auto_meeting_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meeting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `event_id` bigint(20) NOT NULL,
  `num_asistentes` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0' COMMENT 'Home 0 - Bar 1 - Live 2',
  `smoke` int(11) DEFAULT '0' COMMENT '0 no se puede, 1 si se puede',
  `alcohol` int(11) DEFAULT '0' COMMENT '0 no se puede, 1 si se puede',
  `team` varchar(255) DEFAULT NULL,
  `description` text,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` timestamp NULL DEFAULT NULL,
  `finish_time` timestamp NULL DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT '0',
  `longitude` double DEFAULT '0',
  `active` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_meeting_evento1_idx` (`event_id`),
  CONSTRAINT `fk_meeting_evento1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting`
--

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '0' COMMENT 'tipus de notificació',
  `status` int(11) DEFAULT '0' COMMENT '0 NOT_SEND; 1 PUSH_SEND; 2 PUSH_RECEIVED; 3 CONSULTED',
  `send_time` timestamp NULL DEFAULT NULL,
  `receive_time` timestamp NULL DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `description` text,
  `photoUrl` varchar(1024) DEFAULT NULL,
  `meeting_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `action` int(11) DEFAULT '0' COMMENT '1 - Realitzar\n2 - NO Realitzar',
  PRIMARY KEY (`id`),
  KEY `fk_notification_user1_idx` (`user_id`),
  KEY `type_idx` (`type`),
  KEY `fk_notification_user2_idx` (`from_user_id`),
  KEY `fk_notification_meeting1_idx` (`meeting_id`),
  CONSTRAINT `fk_notification_meeting1` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user2` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=455 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pagaments`
--

DROP TABLE IF EXISTS `pagaments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pagaments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_user_has_product_product1_idx` (`product_id`),
  KEY `fk_user_has_product_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_product_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_product_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagaments`
--

LOCK TABLES `pagaments` WRITE;
/*!40000 ALTER TABLE `pagaments` DISABLE KEYS */;
/*!40000 ALTER TABLE `pagaments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `number` int(11) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rate_history`
--

DROP TABLE IF EXISTS `rate_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rate_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rate` int(11) DEFAULT '0',
  `user_id` bigint(20) NOT NULL,
  `from_user_id` bigint(20) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `meeting_id` bigint(20) DEFAULT NULL,
  `rate_type` int(11) DEFAULT NULL COMMENT '1 : HOST, 2: GUEST',
  `comment` text,
  PRIMARY KEY (`id`),
  KEY `fk_rate_history_user1_idx` (`user_id`),
  KEY `fk_rate_history_user2_idx` (`from_user_id`),
  KEY `fk_rate_history_meeting1_idx` (`meeting_id`),
  CONSTRAINT `fk_rate_history_meeting1` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rate_history_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rate_history_user2` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rate_history`
--

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tags` text,
  `name` varchar(255) DEFAULT NULL,
  `meeting_name` varchar(255) DEFAULT NULL,
  `meeting_desc` varchar(255) DEFAULT NULL,
  `meeting_num_assis` int(11) DEFAULT NULL,
  `meeting_team` varchar(255) DEFAULT NULL,
  `meeting_street` varchar(255) DEFAULT NULL,
  `meeting_city` varchar(255) DEFAULT NULL,
  `meeting_postal_code` varchar(255) DEFAULT NULL,
  `meeting_country` varchar(255) DEFAULT NULL,
  `meeting_alcohol` int(11) DEFAULT '0',
  `meeting_smoke` int(11) DEFAULT '0',
  `user_id` bigint(20) DEFAULT NULL,
  `event_type` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `auto_subscription` int(11) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `type_id_idx` (`event_type`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT 'Tabla que se utiliza para tener un listado de los tags utilizados en los eventos',
  `event_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=358 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(155) CHARACTER SET utf8 DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `active` int(11) DEFAULT '1' COMMENT '0 Desactivat, 1 Activat',
  `auth_token` text CHARACTER SET utf8,
  `login_type` int(11) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `rol` int(11) DEFAULT '0' COMMENT '0 = User, 1 = Bar',
  `num_events` int(11) DEFAULT '0' COMMENT 'Número d’events que ha comprat el bar.',
  `photoUrl` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  `rate` int(11) DEFAULT '0' COMMENT 'puntuació de les quedades',
  `description` text,
  `image_modified` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_block_chat`
--

DROP TABLE IF EXISTS `user_block_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_block_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0' COMMENT '0: No Bloquejat, 1: Bloquejat',
  `my_user_id` bigint(20) NOT NULL,
  `to_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_block_chat_user1_idx` (`my_user_id`),
  KEY `fk_user_block_chat_user2_idx` (`to_user_id`),
  CONSTRAINT `fk_user_block_chat_user1` FOREIGN KEY (`my_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_block_chat_user2` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `user_has_friend`
--

DROP TABLE IF EXISTS `user_has_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_friend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `friend_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_has_user_user1_idx` (`friend_id`),
  KEY `fk_user_has_user_user_idx` (`user_id`),
  CONSTRAINT `fk_user_has_user_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_user_user1` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `user_has_meeting`
--

DROP TABLE IF EXISTS `user_has_meeting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_meeting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `meeting_id` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0' COMMENT '0 No està a la quedada, 1 ha fet join, 2 ha sigut acceptat, 3 no s’ha acceptat, 4 es el creador de la quedada.',
  `accepted_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_has_meeting_meeting1_idx` (`meeting_id`),
  KEY `fk_user_has_meeting_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_meeting_meeting1` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_meeting_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_meeting`
--

--
-- Table structure for table `webadmin`
--

DROP TABLE IF EXISTS `webadmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webadmin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL COMMENT 'SHA1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webadmin`
--

--
-- Dumping routines for database 'wmdb'
--
/*!50003 DROP FUNCTION IF EXISTS `COUNT_TAGS_FROM_EVENT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`betroop`@`%` FUNCTION `COUNT_TAGS_FROM_EVENT`(event_id_var INT, string_tags VARCHAR (255)) RETURNS int(11)
  BEGIN
    #split string and add new tags
    DECLARE a INT Default 0;
    DECLARE count INT default 0;
    DECLARE count_tmp INT default 0;
    DECLARE str VARCHAR(255);
    simple_loop: LOOP
      SET a=a+1;
      SET str=TRIM(SPLIT_STR(string_tags,",",a));
      IF str='' THEN
        LEAVE simple_loop;
      END IF;
      SELECT count(*) INTO count_tmp FROM event as e, event_tag as et, tag as t WHERE e.id = event_id_var AND e.id = et.event_id AND et.tag_id = t.id AND t.name LIKE CONCAT('%', str, '%');
      SET count = count + count_tmp;
    END LOOP simple_loop;
    RETURN count;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SPLIT_STR` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`betroop`@`%` FUNCTION `SPLIT_STR`(x VARCHAR(255), delim VARCHAR(12), pos INT) RETURNS varchar(255) CHARSET utf8
  RETURN REPLACE(SUBSTRING(SUBSTRING_INDEX(x, delim, pos),
                           LENGTH(SUBSTRING_INDEX(x, delim, pos -1)) + 1),
                 delim, '') ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ADD_NEW_TAGS_FROM_STRING` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
CREATE DEFINER=`betroop`@`%` PROCEDURE `ADD_NEW_TAGS_FROM_STRING`(IN string_tags VARCHAR (255), IN event_id_var INT)
  BEGIN
    #split string and add new tags
    DECLARE a INT Default 0 ;
    DECLARE tag_id_var INT;
    DECLARE event_count_var INT Default 0;
    DECLARE str VARCHAR(255);
    simple_loop: LOOP
      SET a=a+1;
      SET str=TRIM(SPLIT_STR(string_tags,",",a));
      IF str='' THEN
        LEAVE simple_loop;
      END IF;
      #Si no existeix el tag l'afegim de nou
      IF NOT EXISTS (SELECT * FROM tag WHERE name = str) THEN
        INSERT INTO tag (name) VALUES (str);
      END IF;

      SELECT id INTO tag_id_var FROM tag WHERE name = str;
      #Comprovar si s'ha d'inserir a la taula event_tag
      IF NOT EXISTS (SELECT * FROM event_tag WHERE event_id = event_id_var AND tag_id = tag_id_var) THEN
        INSERT INTO event_tag (event_id, tag_id) VALUES (event_id_var, tag_id_var);
      END IF;

      #Actualitzar el comptador
      SELECT count(*) INTO event_count_var FROM event WHERE tag LIKE CONCAT('%', str, '%');
      UPDATE tag SET event_count = event_count_var WHERE id = tag_id_var;

    END LOOP simple_loop;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-21 19:55:47

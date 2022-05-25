-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: realestateagent
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `rentarea`
--

DROP TABLE IF EXISTS `rentarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rentarea` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `buildingid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqhqoxlvm1iblaew5s0v8n3ut4` (`buildingid`),
  CONSTRAINT `FKqhqoxlvm1iblaew5s0v8n3ut4` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentarea`
--

LOCK TABLES `rentarea` WRITE;
/*!40000 ALTER TABLE `rentarea` DISABLE KEYS */;
INSERT INTO `rentarea` VALUES (28,'nhanvien4','2021-07-08 20:54:20','nhanvien4','2021-07-08 20:54:20',111,3),(29,'nhanvien4','2021-07-08 20:54:20','nhanvien4','2021-07-08 20:54:20',222,3),(30,'nhanvien4','2021-07-08 20:54:20','nhanvien4','2021-07-08 20:54:20',333,3),(34,'nhanvien4','2021-07-08 20:54:38','nhanvien4','2021-07-08 20:54:38',1000,2),(35,'nhanvien4','2021-07-08 20:54:38','nhanvien4','2021-07-08 20:54:38',2000,2),(36,'nhanvien4','2021-07-08 20:54:38','nhanvien4','2021-07-08 20:54:38',3000,2),(40,'admin','2021-07-17 19:57:43','admin','2021-07-17 19:57:43',100,1),(41,'admin','2021-07-17 19:57:43','admin','2021-07-17 19:57:43',200,1),(42,'admin','2021-07-17 19:57:43','admin','2021-07-17 19:57:43',300,1);
/*!40000 ALTER TABLE `rentarea` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-18 15:39:18

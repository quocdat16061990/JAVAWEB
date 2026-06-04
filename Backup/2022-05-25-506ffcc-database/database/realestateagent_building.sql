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
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `building` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `brokeragefee` varchar(255) DEFAULT NULL,
  `carfee` varchar(255) DEFAULT NULL,
  `decorationtime` varchar(255) DEFAULT NULL,
  `deposit` varchar(255) DEFAULT NULL,
  `direcion` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `electricityfee` varchar(255) DEFAULT NULL,
  `floorarea` int(11) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `linkofbuilding` varchar(255) DEFAULT NULL,
  `managername` varchar(255) DEFAULT NULL,
  `managerphone` varchar(255) DEFAULT NULL,
  `map` varchar(255) DEFAULT NULL,
  `motofee` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` longtext,
  `numberofbasement` int(11) DEFAULT NULL,
  `overtimefee` varchar(255) DEFAULT NULL,
  `payment` varchar(255) DEFAULT NULL,
  `rentprice` int(11) DEFAULT NULL,
  `rentpricedescription` longtext,
  `renttime` varchar(255) DEFAULT NULL,
  `servicefee` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `structure` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL,
  `waterfee` varchar(255) DEFAULT NULL,
  `image` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,NULL,NULL,'admin','2021-07-17 19:57:44',NULL,'1000','1000','10 ngày','1000','Đông Bắc','QUAN_GO_VAP','1000',100,'1','http://','Dat','0964366958','google map','1000','Love Building Area ','Nhớ đầy đủ',10,'1000','1000',999000,'Giá là 999000','1 tháng','1000','27/4','Sắt, Thép','NOI_THAT,TANG_TRET','Linh Trung','1000',NULL),(2,NULL,NULL,'nhanvien4','2021-07-08 20:54:38',NULL,'1000','1000','10 ngày','1000','Đông Bắc','QUAN_THU_DUC','1000',1000,'5','http://','Dat','0964366958','google map','1000','Làng Lá','Nhớ đầy đủ',2,'1000','1000',100000,'Giá là 100000','1 tháng','1000','27/4','Gỗ Lá','NGUYEN_CAN','Nhân Giống','1000',NULL),(3,NULL,NULL,'nhanvien4','2021-07-08 20:54:20',NULL,'10000','1000','1 years','10000','Đông Nam','QUAN_GO_VAP','10000',10000,'5','http','Vy','093123612','map','100','Car Building','Good Location',15,'1000','13000',1000000,'Giá là 1000000 vnd','10000 days','1000','Super Car','Leps','TANG_TRET','Car High Land','1',NULL);
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-18 15:39:16

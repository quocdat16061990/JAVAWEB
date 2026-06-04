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
-- Table structure for table `assignmentbuilding`
--

DROP TABLE IF EXISTS `assignmentbuilding`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assignmentbuilding` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdby` varchar(255) DEFAULT NULL,
  `createddate` datetime DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `modifieddate` datetime DEFAULT NULL,
  `buildingid` bigint(20) DEFAULT NULL,
  `staffid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkk3mdegrmfcdlsxqds1m6q238` (`buildingid`),
  KEY `FK5ml77mpq20c7cktnaayqyndi` (`staffid`),
  CONSTRAINT `FK5ml77mpq20c7cktnaayqyndi` FOREIGN KEY (`staffid`) REFERENCES `users` (`id`),
  CONSTRAINT `FKkk3mdegrmfcdlsxqds1m6q238` FOREIGN KEY (`buildingid`) REFERENCES `building` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignmentbuilding`
--

LOCK TABLES `assignmentbuilding` WRITE;
/*!40000 ALTER TABLE `assignmentbuilding` DISABLE KEYS */;
INSERT INTO `assignmentbuilding` VALUES (3,'admin','2021-07-17 20:27:58','admin','2021-07-17 20:27:58',3,3),(5,'admin','2021-07-17 20:27:58','admin','2021-07-17 20:27:58',3,5),(9,'admin','2021-07-17 20:28:29','admin','2021-07-17 20:28:29',2,3),(10,'admin','2021-07-17 20:28:29','admin','2021-07-17 20:28:29',2,4),(11,'admin','2021-07-17 20:28:29','admin','2021-07-17 20:28:29',2,5),(18,'admin','2021-07-17 20:29:15','admin','2021-07-17 20:29:15',3,2),(19,'admin','2021-07-17 20:29:15','admin','2021-07-17 20:29:15',3,3),(20,'admin','2021-07-17 20:29:15','admin','2021-07-17 20:29:15',3,5),(21,'admin','2021-07-18 09:28:13','admin','2021-07-18 09:28:13',1,2),(22,'admin','2021-07-18 09:28:13','admin','2021-07-18 09:28:13',1,5);
/*!40000 ALTER TABLE `assignmentbuilding` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-18 15:39:17

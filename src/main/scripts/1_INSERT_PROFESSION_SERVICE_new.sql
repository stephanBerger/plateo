-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: localhost    Database: plateo
-- ------------------------------------------------------
-- Server version	5.7.28-0ubuntu0.18.04.4

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
-- Dumping data for table `profession`
--

LOCK TABLES `profession` WRITE;
/*!40000 ALTER TABLE `profession` DISABLE KEYS */;
INSERT INTO `profession` VALUES (1,'Carreleur','CRR',_binary '\0',NULL),
(2,'Charpentier','CHR',_binary '\0',NULL),
(3,'Chauffagiste','CHF',_binary '\0',NULL),
(4,'Charpente-Couverture','CVR',_binary '\0',NULL),
(5,'Électricien','ELC',_binary '\0',NULL),
(6,'Maçon-Gros-oeuvre','MCN',_binary '\0',NULL),
(7,'Menuiseries-exterieures','MNE',_binary '\0',NULL),
(8,'Peintre','PNT',_binary '',NULL),
(9,'Platrier / Plaquiste','PLT',_binary '\0',NULL),
(10,'Plombier','PLM',_binary '',NULL),
(11,'Serrurier','SRR',_binary '\0',NULL),
(12,'Revetement-de-facades','RVT',_binary '\0',NULL),
(13,'Espaces-verts','ESP',_binary '\0',NULL),
(14,'Systeme-de-securite-incendie','TPR',_binary '\0',NULL),
(15,'Demolition-Desamiantage','DEM',_binary '\0',NULL),
(16,'Nettoyage','NET',_binary '\0',NULL),
(17,'Multi-Services','MUL',_binary '\0',NULL),
(18,'Mobilier','MOB',_binary '\0',NULL),
(19,'Etancheite','ETC',_binary '\0',NULL),
(20,'Revetement-de-sols','RVS',_binary '\0',NULL),
(21,'Photovoltaique','PHO',_binary '\0',NULL),
(22,'VRD-Terrassement','VRD',_binary '\0',NULL);
/*!40000 ALTER TABLE `profession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'MAT_NEW','Installation de matériel neuf',10,_binary ''),(2,'MAT_EXISTS','Dépannage ou intervention sur du matériel existant',10,_binary '\0'),(3,'MURS','Peinture murs',8,_binary ''),(4,'PLAFONDS','Peinture plafonds',8,_binary '');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-22 16:49:25

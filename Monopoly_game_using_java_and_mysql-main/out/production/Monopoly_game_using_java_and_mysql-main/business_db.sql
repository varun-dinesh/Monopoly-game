-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: business_db
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chance`
--

DROP TABLE IF EXISTS `chance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chance` (
  `id` int NOT NULL,
  `task` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chance`
--

LOCK TABLES `chance` WRITE;
/*!40000 ALTER TABLE `chance` DISABLE KEYS */;
INSERT INTO `chance` VALUES (1,'Go to jail'),(2,'Your building loan matures collect Rs.150'),(3,'Pay Rs.100 for car repair'),(4,'Bank pays you dividend of Rs.50'),(5,'Pay poor tax of Rs.30'),(6,'You won a lottery of Rs.500');
/*!40000 ALTER TABLE `chance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community_chest`
--

DROP TABLE IF EXISTS `community_chest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `community_chest` (
  `id` int NOT NULL,
  `task` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community_chest`
--

LOCK TABLES `community_chest` WRITE;
/*!40000 ALTER TABLE `community_chest` DISABLE KEYS */;
INSERT INTO `community_chest` VALUES (1,'You won 2nd prize in beauty contest collect Rs.500'),(2,'Pay Rs.800 for health insurance'),(3,'From sale of stock you get Rs.45'),(4,'You inherit Rs.1000'),(5,'Pay hospital Rs.100'),(6,'Income tax refund collect Rs.75');
/*!40000 ALTER TABLE `community_chest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `land`
--

DROP TABLE IF EXISTS `land`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `land` (
  `lid` int NOT NULL,
  `rent` int DEFAULT NULL,
  `pos_x` int DEFAULT NULL,
  `pos_y` int DEFAULT NULL,
  `buy` int DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `land`
--

LOCK TABLES `land` WRITE;
/*!40000 ALTER TABLE `land` DISABLE KEYS */;
INSERT INTO `land` VALUES (1,0,746,542,0,'start'),(2,600,677,546,1600,'mediterranean avenue'),(3,0,611,543,0,'community chest'),(4,600,541,546,1600,'baltic avenue'),(5,200,475,546,0,'income tax'),(6,200,406,546,1200,'reading railroad'),(7,100,340,546,800,'oriental avenue'),(8,0,268,546,0,'chance'),(9,150,202,546,900,'vermont avenue'),(10,100,136,542,800,'connecticut avenue'),(11,0,24,542,0,'free parking'),(12,240,24,495,1400,'st.charles place'),(13,500,24,447,1500,'electric company'),(14,200,24,398,1200,'states avenue'),(15,260,24,351,1600,'virginia avenue'),(16,900,24,302,2000,'pennsylvania railroad'),(17,280,24,253,1800,'st.james place'),(18,0,24,205,0,'community chest'),(19,280,24,157,1800,'tennessee avenue'),(20,600,24,111,1200,'new york avenue'),(21,0,24,31,0,'free parking'),(22,600,136,31,1600,'kentucky avenue'),(23,0,202,31,0,'chance'),(24,600,268,31,1600,'indiana avenue'),(25,200,340,31,1300,'illinois avenue'),(26,200,406,31,1200,'B&O railroad'),(27,100,472,31,800,'atlantic avenue'),(28,400,541,31,1500,'ventnor avenue'),(29,150,611,31,900,'water works'),(30,700,677,31,1800,'marvin gardens'),(31,100,746,31,0,'jail'),(32,240,746,111,1400,'pacific avenue'),(33,500,746,157,1500,'north carolina avenue'),(34,0,746,205,0,'community chest'),(35,260,746,253,1600,'pennsylvania avenue'),(36,200,746,302,1200,'short line'),(37,0,746,351,0,'chance'),(38,350,746,398,1350,'park palace'),(39,100,746,447,0,'luxury tax'),(40,600,746,495,1400,'boardwalk');
/*!40000 ALTER TABLE `land` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moves`
--

DROP TABLE IF EXISTS `moves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moves` (
  `move_no` int NOT NULL,
  `pid` int DEFAULT NULL,
  `dice` int DEFAULT NULL,
  `lid` int DEFAULT NULL,
  PRIMARY KEY (`move_no`),
  KEY `pid` (`pid`),
  KEY `lid` (`lid`),
  CONSTRAINT `moves_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `players` (`pid`) ON UPDATE CASCADE,
  CONSTRAINT `moves_ibfk_2` FOREIGN KEY (`lid`) REFERENCES `land` (`lid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moves`
--

LOCK TABLES `moves` WRITE;
/*!40000 ALTER TABLE `moves` DISABLE KEYS */;
/*!40000 ALTER TABLE `moves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owners`
--

DROP TABLE IF EXISTS `owners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owners` (
  `pid` int DEFAULT NULL,
  `lid` int DEFAULT NULL,
  KEY `pid` (`pid`),
  KEY `lid` (`lid`),
  CONSTRAINT `owners_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `players` (`pid`) ON UPDATE CASCADE,
  CONSTRAINT `owners_ibfk_2` FOREIGN KEY (`lid`) REFERENCES `land` (`lid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owners`
--

LOCK TABLES `owners` WRITE;
/*!40000 ALTER TABLE `owners` DISABLE KEYS */;
/*!40000 ALTER TABLE `owners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `pid` int NOT NULL,
  `pname` varchar(25) DEFAULT NULL,
  `color` varchar(10) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-23 17:16:53

CREATE DATABASE  IF NOT EXISTS `renting_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `renting_db`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: renting_db
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `amenity`
--

DROP TABLE IF EXISTS `amenity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amenity` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amenity`
--

LOCK TABLES `amenity` WRITE;
/*!40000 ALTER TABLE `amenity` DISABLE KEYS */;
INSERT INTO `amenity` VALUES (1,'pool facing','facing pool'),(2,'beach facing','near beach'),(3,'wifi','free');
/*!40000 ALTER TABLE `amenity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `state` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `details` text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Vietnam','Tp.HCM','Thanhxuan street',NULL),(2,'Vietnam','Hanoi','24 Thanhxuan street',NULL),(3,'Vietnam','Hanoi','103 Pham Van Dong street',NULL),(4,'Vietnam','Hanoi','Hoan Kiem','none'),(5,'Vietnam','Hanoi','Minh Phu',NULL),(6,'Vietnam','Da Lat','',NULL),(7,'Vietnam','Nha Trang','',NULL),(8,'Vietnam','Hai Phong','',NULL);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `avg_rating` decimal(2,1) DEFAULT '0.0',
  `rating_num` int DEFAULT '0',
  `price_by_night` int DEFAULT '0',
  `bedroom` tinyint DEFAULT '1',
  `bathroom` tinyint DEFAULT '1',
  `kitchen` tinyint DEFAULT '1',
  `user_id` int DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  `property_type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `location_id` (`location_id`),
  KEY `property_type_id` (`property_type_id`),
  CONSTRAINT `property_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `property_ibfk_2` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `property_ibfk_3` FOREIGN KEY (`property_type_id`) REFERENCES `property_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property`
--

LOCK TABLES `property` WRITE;
/*!40000 ALTER TABLE `property` DISABLE KEYS */;
INSERT INTO `property` VALUES (1,'Nha Ben Run(U Lesa) - Big Pine House','The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.',4.5,11,101,1,1,1,2,1,3),(2,'New Studio, lift, Hoan Kiem, near old quarter #B01','Welcome to Botanicahome! We are happy to invite you to enjoy our family’s home.\nWe wanted to create a space where people feel completely comfortable and at home. Each studio apartment is located in the mini building so near old quarter and downtown. This building was built and is operated by own family',4.9,116,938,1,1,1,2,2,2),(3,'Nha ben bien','The Big Pine House is located right on the hillside, the surrounding old pine forest brings a cool vibe all year round for Homestay. The house also owns an extremely spacious front view when the brick wall is replaced by transparent toughened glass wall, without visibility restrictions.',4.4,11,101,1,1,1,5,3,3),(4,'VILLA VENITY Sky','The Mirror Villa is luxurious all the way and features everything you can expect from a smart, upscale property of 21st century. It impresses with utilizing contemporary and distinctive materials, finishing with the utmost attention to details and quality, innovative technologies and high-end appliances.',5.0,3,100,7,7,7,5,4,2),(5,'Mountain house','The mountain house is luxurious all the way and features everything you can expect from a smart, upscale property of 21st century. It impresses with utilizing contemporary and distinctive materials, finishing with the utmost attention to details and quality, innovative technologies and high-end appliances.',5.0,3,100,4,4,4,6,5,2),(6,'Beach villa','The beach villa is luxurious all the way and features everything you can expect from a smart, upscale property of 21st century. It impresses with utilizing contemporary and distinctive materials, finishing with the utmost attention to details and quality, innovative technologies and high-end appliances.',4.8,20,100,4,4,4,6,6,2),(7,'Green villa','The green villa is luxurious all the way and features everything you can expect from a smart, upscale property of 21st century. It impresses with utilizing contemporary and distinctive materials, finishing with the utmost attention to details and quality, innovative technologies and high-end appliances.',4.8,20,100,4,4,4,7,7,2),(8,'ocean apartment','The ocean apartment is luxurious all the way and features everything you can expect from a smart, upscale property of 21st century. It impresses with utilizing contemporary and distinctive materials, finishing with the utmost attention to details and quality, innovative technologies and high-end appliances.',4.8,20,100,4,4,4,7,8,2);
/*!40000 ALTER TABLE `property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_amenity`
--

DROP TABLE IF EXISTS `property_amenity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_amenity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amenity_id` tinyint DEFAULT NULL,
  `property_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `amenity_id` (`amenity_id`),
  KEY `property_id` (`property_id`),
  CONSTRAINT `property_amenity_ibfk_1` FOREIGN KEY (`amenity_id`) REFERENCES `amenity` (`id`),
  CONSTRAINT `property_amenity_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_amenity`
--

LOCK TABLES `property_amenity` WRITE;
/*!40000 ALTER TABLE `property_amenity` DISABLE KEYS */;
INSERT INTO `property_amenity` VALUES (1,1,1),(2,2,1),(3,3,2),(4,2,3),(5,1,4),(6,3,5),(7,2,6),(8,1,7),(9,2,8),(10,3,8);
/*!40000 ALTER TABLE `property_amenity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_image`
--

DROP TABLE IF EXISTS `property_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `property_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `property_id` (`property_id`),
  CONSTRAINT `property_image_ibfk_1` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_image`
--

LOCK TABLES `property_image` WRITE;
/*!40000 ALTER TABLE `property_image` DISABLE KEYS */;
INSERT INTO `property_image` VALUES (1,'property1-1.jpg',1),(2,'property1-2.jpg',1),(3,'property1-3.jpg',1),(4,'property1-4.jpg',1),(6,'property2-1.jpg',2),(7,'property2-2.jpg',2),(8,'property2-3.jpg',2),(9,'property3-1.jpg',3),(10,'property3-2.jpg',3),(11,'property3-3.jpg',3),(12,'property4-1.jpg',4),(13,'property4-2.jpg',4),(14,'property4-3.jpg',4),(15,'property4-4.jpg',4),(16,'property5-1.jpg',5),(17,'property5-2.jpg',5),(18,'property5-3.jpg',5),(19,'property5-4.jpg',5),(20,'property6-1.jpg',6),(21,'property6-2.jpg',6),(22,'property6-3.jpg',6),(23,'property6-4.jpg',6),(24,'property7-1.jpg',7),(25,'property7-2.jpg',7),(26,'property7-3.jpg',7),(27,'property7-4.jpg',7),(28,'property8-1.jpg',8),(29,'property8-2.jpg',8),(30,'property8-3.jpg',8),(31,'property8-4.jpg',8);
/*!40000 ALTER TABLE `property_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `property_type`
--

DROP TABLE IF EXISTS `property_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `property_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `property_type`
--

LOCK TABLES `property_type` WRITE;
/*!40000 ALTER TABLE `property_type` DISABLE KEYS */;
INSERT INTO `property_type` VALUES (1,'flalt'),(2,'apartment'),(3,'villa');
/*!40000 ALTER TABLE `property_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `property_id` int DEFAULT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `total` double DEFAULT '0',
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `property_id` (`property_id`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,1,1,'2022-04-26','2022-12-12',404,0),(15,4,2,'2022-04-29','2022-05-05',5628,0);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` tinyint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'owner'),(2,'customer');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_rating`
--

DROP TABLE IF EXISTS `user_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_rating` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `property_id` int DEFAULT NULL,
  `rating_num` decimal(2,1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `property_id` (`property_id`),
  CONSTRAINT `user_rating_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_rating_ibfk_2` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_rating`
--

LOCK TABLES `user_rating` WRITE;
/*!40000 ALTER TABLE `user_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `role_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'033303332','Quan','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','quan@gmail.com','Lana','March',2),(2,'0334455555','owner','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','owner@gmail.com','Lin','Yang',1),(3,'0334566555','Thanh','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','user2@gmail.com','Hoa','Lan',2),(4,'0332333333','Minh','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','minh@gmail.com','Minh','Ha',2),(5,'0333033094','Viet Anh','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','vietanh@gmail.com','Anh','Viet',1),(6,'032323303','Anna','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','anna@gmail.com','Anna','Lenner',1),(7,'032323303','Kateryna','$2a$12$NBIaiXL1t0UvYRyBQ7zPyO9O.4m3GtAjc4eLAm3YZYKKADlz5aY.q','Kateryna@gmail.com','Kateryna','Lenner',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-07 23:23:10

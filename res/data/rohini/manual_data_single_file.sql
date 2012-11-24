CREATE DATABASE  IF NOT EXISTS `videolibrary` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `videolibrary`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: videolibrary
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `userId` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  `firstName` varchar(20) DEFAULT NULL,
  `lastName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin1','admin1','Dan','Smith'),('admin2','admin2','Riya','Shah');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `amountdetails`
--

DROP TABLE IF EXISTS `amountdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `amountdetails` (
  `membershipType` varchar(8) DEFAULT NULL,
  `feesUpdateDate` date DEFAULT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amountdetails`
--

LOCK TABLES `amountdetails` WRITE;
/*!40000 ALTER TABLE `amountdetails` DISABLE KEYS */;
INSERT INTO `amountdetails` VALUES ('simple','2012-11-14',1.5),('premium','2012-11-14',25),('simple','2012-11-14',1.75);
/*!40000 ALTER TABLE `amountdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `categoryName` varchar(50) DEFAULT NULL,
  `categoryId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('Drama',1),('Romance',2),('Comedy',3),('Thriller',4),('Documentary',5);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `movieId` int(11) NOT NULL AUTO_INCREMENT,
  `movieName` varchar(100) DEFAULT NULL,
  `movieBanner` varchar(100) DEFAULT NULL,
  `releaseDate` date DEFAULT NULL,
  `availableCopies` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  PRIMARY KEY (`movieId`),
  UNIQUE KEY `movieId` (`movieId`),
  UNIQUE KEY `movieName` (`movieName`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `movie_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'Ganesha','Ashtavinayak','2012-11-14',5,1),(2,'Lost','Paramount','2010-11-24',9,2),(3,'Titanic','Paramount','2012-11-14',5,2),(4,'Funny','ABC','2012-11-14',5,3),(5,'Space','NASA','2012-11-14',5,5);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moviecart`
--

DROP TABLE IF EXISTS `moviecart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moviecart` (
  `movieId` int(11) NOT NULL,
  `membershipId` int(11) NOT NULL,
  KEY `movieId` (`movieId`),
  KEY `membershipId` (`membershipId`),
  CONSTRAINT `moviecart_ibfk_1` FOREIGN KEY (`movieId`) REFERENCES `movie` (`movieId`),
  CONSTRAINT `moviecart_ibfk_2` FOREIGN KEY (`membershipId`) REFERENCES `user` (`membershipId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moviecart`
--

LOCK TABLES `moviecart` WRITE;
/*!40000 ALTER TABLE `moviecart` DISABLE KEYS */;
/*!40000 ALTER TABLE `moviecart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymenttransaction`
--

DROP TABLE IF EXISTS `paymenttransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymenttransaction` (
  `transactionId` int(11) NOT NULL AUTO_INCREMENT,
  `rentDate` date DEFAULT NULL,
  `totalDueAmount` double NOT NULL,
  `membershipId` int(11) NOT NULL,
  PRIMARY KEY (`transactionId`),
  UNIQUE KEY `transactionId` (`transactionId`),
  KEY `membershipId` (`membershipId`),
  CONSTRAINT `paymenttransaction_ibfk_1` FOREIGN KEY (`membershipId`) REFERENCES `user` (`membershipId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymenttransaction`
--

LOCK TABLES `paymenttransaction` WRITE;
/*!40000 ALTER TABLE `paymenttransaction` DISABLE KEYS */;
INSERT INTO `paymenttransaction` VALUES (1,'2012-11-14',4.5,1),(2,'2012-11-14',1.5,2),(3,'2012-11-15',25,2),(4,'2012-11-19',1.5,1);
/*!40000 ALTER TABLE `paymenttransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentmovietransaction`
--

DROP TABLE IF EXISTS `rentmovietransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rentmovietransaction` (
  `movieId` int(11) NOT NULL,
  `transactionId` int(11) NOT NULL,
  `returnDate` date DEFAULT NULL,
  PRIMARY KEY (`transactionId`,`movieId`),
  KEY `movieId` (`movieId`),
  CONSTRAINT `rentmovietransaction_ibfk_1` FOREIGN KEY (`movieId`) REFERENCES `movie` (`movieId`),
  CONSTRAINT `rentmovietransaction_ibfk_2` FOREIGN KEY (`transactionId`) REFERENCES `paymenttransaction` (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentmovietransaction`
--

LOCK TABLES `rentmovietransaction` WRITE;
/*!40000 ALTER TABLE `rentmovietransaction` DISABLE KEYS */;
INSERT INTO `rentmovietransaction` VALUES (1,1,NULL),(2,1,NULL),(3,1,NULL),(3,2,NULL);
/*!40000 ALTER TABLE `rentmovietransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statement`
--

DROP TABLE IF EXISTS `statement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statement` (
  `statementId` int(11) NOT NULL AUTO_INCREMENT,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `membershipId` int(11) NOT NULL,
  PRIMARY KEY (`statementId`),
  KEY `membershipId` (`membershipId`),
  CONSTRAINT `statement_ibfk_1` FOREIGN KEY (`membershipId`) REFERENCES `user` (`membershipId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statement`
--

LOCK TABLES `statement` WRITE;
/*!40000 ALTER TABLE `statement` DISABLE KEYS */;
INSERT INTO `statement` VALUES (12,11,2012,2),(13,11,2012,1),(14,10,2012,1);
/*!40000 ALTER TABLE `statement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statementtransactions`
--

DROP TABLE IF EXISTS `statementtransactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statementtransactions` (
  `statementId` int(11) NOT NULL,
  `transactionId` int(11) NOT NULL,
  KEY `statementId` (`statementId`),
  KEY `transactionId` (`transactionId`),
  CONSTRAINT `statementtransactions_ibfk_1` FOREIGN KEY (`statementId`) REFERENCES `statement` (`statementId`),
  CONSTRAINT `statementtransactions_ibfk_2` FOREIGN KEY (`transactionId`) REFERENCES `paymenttransaction` (`transactionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statementtransactions`
--

LOCK TABLES `statementtransactions` WRITE;
/*!40000 ALTER TABLE `statementtransactions` DISABLE KEYS */;
INSERT INTO `statementtransactions` VALUES (12,2),(12,3),(13,1);
/*!40000 ALTER TABLE `statementtransactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `membershipId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(50) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `membershipType` varchar(10) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` char(2) DEFAULT NULL,
  `zip` char(5) DEFAULT NULL,
  `creditCardNumber` char(16) DEFAULT NULL,
  `latestPaymentDate` date DEFAULT NULL,
  PRIMARY KEY (`membershipId`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'sr@yahoo.com','sr123','Simple','2012-11-14','Shri','Ram','Washington Ave,Apt 1234','San Jose','CA','93457','1234567890123456',NULL),(2,'ab@yahoo.com','ab123','Premium','2012-11-14','Abey','Brin','Newark Ave,674','Sacramento','CA','93347','1234512345123456',NULL),(3,'xy@yahoo.com','xy123','Simple','2012-11-14','Xiao','Yang','Rodeo Dr,123','Los Angelos','CA','91937','1234567890567890',NULL),(4,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2012-11-15');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'videolibrary'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-11-23 17:21:11

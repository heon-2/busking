-- MySQL dump 10.13  Distrib 8.1.0, for Linux (x86_64)
--
-- Host: localhost    Database: busking
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bus`
--

DROP TABLE IF EXISTS `bus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bus` (
  `company_id` bigint NOT NULL,
  `bus_no` bigint NOT NULL,
  PRIMARY KEY (`company_id`,`bus_no`),
  CONSTRAINT `FKt45aw9llo7vvpx8vkbvk6pwkf` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus`
--

LOCK TABLES `bus` WRITE;
/*!40000 ALTER TABLE `bus` DISABLE KEYS */;
INSERT INTO `bus` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `bus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bus_and_route`
--

DROP TABLE IF EXISTS `bus_and_route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bus_and_route` (
  `company_id` bigint NOT NULL,
  `bus_no` bigint NOT NULL,
  `route_id` bigint NOT NULL,
  UNIQUE KEY `UK_kg6ep0l7bd0ryjlj7tmhhufln` (`route_id`),
  KEY `FK6lkohmpbamqkovnxttyip9x8w` (`company_id`,`bus_no`),
  CONSTRAINT `FK6lkohmpbamqkovnxttyip9x8w` FOREIGN KEY (`company_id`, `bus_no`) REFERENCES `bus` (`company_id`, `bus_no`),
  CONSTRAINT `FKodeew5oum1hu32uowr1ndhlhs` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus_and_route`
--

LOCK TABLES `bus_and_route` WRITE;
/*!40000 ALTER TABLE `bus_and_route` DISABLE KEYS */;
INSERT INTO `bus_and_route` VALUES (1,1,302),(1,1,303),(1,2,204),(1,2,304),(1,3,205),(1,3,305),(1,4,206),(1,4,355);
/*!40000 ALTER TABLE `bus_and_route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'BUSKING');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fcm_token`
--

DROP TABLE IF EXISTS `fcm_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcm_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s83ktcdlp96ihtp6sukvefuvt` (`user_id`),
  CONSTRAINT `FK8u9xsmd3agc2nn80tb16ouph4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcm_token`
--

LOCK TABLES `fcm_token` WRITE;
/*!40000 ALTER TABLE `fcm_token` DISABLE KEYS */;
INSERT INTO `fcm_token` VALUES (1,'fVl6LsF1_-bOW4zeKu_dZ-:APA91bFwY9E7YkcMb8kJfbj8L2LCwbyfNkMVoy1RTl4W3fu9sp_hrilIEZQ-rn4BVDxMNlJmXCWfnfWVtcnp5HuIGJ1aFJ646kDml72sa_gvxyFF5VqriZZKbxjGtrfJ1e3R2rlhDsU3',_binary '\éE2±cN\ö—G¯\Â\ð2¯w'),(2,'cupwIlYTNrf8TMNB-WTiSq:APA91bHZS-OxVWXlNhIjU47s8GPKmFFqxitBVZwH0ma0yo-t_P3719wEqXSDRvZUKwW6aESm_t3joEpWGT5Zb2tw_XmcdqNiSabB4RhlF03mJMNv6iStddR25UgRLVobYr0SZ8F7vCB8',_binary 'Ÿ¡\à\ï\\•Ma½Ù„.\ä>\ç'),(3,'dexFohQIgXGShfpNLPnbrT:APA91bFZgJUx_apLBYjO6gWY4lkI0XT0kJkrzqjB9AhjkjMSgKPW68c4OACi6LTfJHScdr5Y-EU3j3VP9YMHGR3buCy6Bxtphky_tYnadytpKlFJvS0oLuUgeGQEoN5ZOcs7Nej3iGxg',_binary 'ß¯t¶‚A0€8u\öVƒ\É'),(4,'cxWyBb6McjasoOFtrMREm6:APA91bEzhV_GDWS0D7mE1n1ZYM9sZa7N0GayjD4W0XKgZ1FT-eBZLdZi5hL60BuHMRQ261l-0hbnqY9YINGrLY3JrlaxTXWkVTGCfzRXI3EXFRhtv2yMdc2VwUdRYenj4aIZoFdKyp-o',_binary 'nP-\É@š­\ÃÇ~.N'),(5,'feHvcbzcFK9ThNqWfUadc0:APA91bHOdI8rJFRkoCKXiIUx2ap00kJXKdMfdtGSvZ8JvLZOfYy7zLkaHKnQ4qVZ1uuDp6XTJtFZmstrbPeo6t1dlopusvl3f--Jn7QiXZehJjEgLig8Ndp-Z16sCVnW79dmo9jWFGS8',_binary '’ Ê¦\ïK·ŽKož&®Wz'),(6,'d55JOJlMxS0a0K2aBiCeQn:APA91bHvm7d6NuesI89GY4QfVfnjRgPAaobkYXp5VIlDISMJXB0B6xGuB3YYWt9qLm1XdVrMRsvpqHBoTojtKa4tfLT--wEdBUSQAjwpDxzsuxzFl6Z7J_Xaj9ud9WC2PDsMg15AOCb7',_binary '*­\æ\Í\ÄÿN.ª|š—4–\ìp'),(7,'egQmrGDuK1HKiQXvMw6eVq:APA91bHMbE9gm4wGRfQaO2tLj5MGtSoJ_y_PC4tXXQo1JL_-APmWA98KiS5tw6JxF2VJfaECn6ZCTWqSubiwe3ZHe-ugDmsfzzMnlPotSjxyC2e6VlFz-ZD5Zogi0qD71MvswDcufq98',_binary '.½¼Yø@ü˜¤aX»&\ô†'),(8,'feHvcbzcFK9ThNqWfUadc0:APA91bHOdI8rJFRkoCKXiIUx2ap00kJXKdMfdtGSvZ8JvLZOfYy7zLkaHKnQ4qVZ1uuDp6XTJtFZmstrbPeo6t1dlopusvl3f--Jn7QiXZehJjEgLig8Ndp-Z16sCVnW79dmo9jWFGS8',_binary '\ålý–eF¶­´È³vˆž\×'),(9,'fVl6LsF1_-bOW4zeKu_dZ-:APA91bFwY9E7YkcMb8kJfbj8L2LCwbyfNkMVoy1RTl4W3fu9sp_hrilIEZQ-rn4BVDxMNlJmXCWfnfWVtcnp5HuIGJ1aFJ646kDml72sa_gvxyFF5VqriZZKbxjGtrfJ1e3R2rlhDsU3',_binary '“9(}E¯„¾â­·\'p'),(10,'d4-jYrwfovWwobpIgYqBug:APA91bEnKrZ4yUMyW4tFg6HekFsJPzwgT79EvesfN0O46cb_StCFHTXrWKHA48yOmpZ--6xte3W71gu8-tTOdPO-l9FwIiJaOurc92h82whrd9DKoJuCS_VbfCi8ukFGSi8O2lTiwJat',_binary '†8\Û\îZþH¸¾Ÿlu\ZX\''),(11,'d4-jYrwfovWwobpIgYqBug:APA91bEnKrZ4yUMyW4tFg6HekFsJPzwgT79EvesfN0O46cb_StCFHTXrWKHA48yOmpZ--6xte3W71gu8-tTOdPO-l9FwIiJaOurc92h82whrd9DKoJuCS_VbfCi8ukFGSi8O2lTiwJat',_binary '9\ì\ÑN5AS[\Î=Vx\ô');
/*!40000 ALTER TABLE `fcm_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `bus_id` bigint DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKajjyc87cy1lkelx7v7394elpt` (`company_id`,`bus_id`),
  KEY `FKj62onw73yx1qnmd57tcaa9q3a` (`user_id`),
  CONSTRAINT `FKajjyc87cy1lkelx7v7394elpt` FOREIGN KEY (`company_id`, `bus_id`) REFERENCES `bus` (`company_id`, `bus_no`),
  CONSTRAINT `FKj62onw73yx1qnmd57tcaa9q3a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (202,'2023-08-17 07:55:59.455803','ì˜ˆì • ì‹œê°„ë³´ë‹¤ ë¹¨ë¦¬ ì¶œë°œ',35.2059392,126.81216,1,1,_binary 'Ÿ¡\à\ï\\•Ma½Ù„.\ä>\ç'),(203,'2023-08-17 11:59:40.372295','ì˜ˆì • ì‹œê°„ë³´ë‹¤ ë¹¨ë¦¬ ì¶œë°œ',35.168306,126.81425,1,1,_binary '\éE2±cN\ö—G¯\Â\ð2¯w'),(252,'2023-08-17 14:07:27.013092','ë¬´ì •ì°¨ ì¶œë°œ',35.1873416,126.81425,1,1,_binary '\ålý–eF¶­´È³vˆž\×');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report_seq`
--

DROP TABLE IF EXISTS `report_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report_seq`
--

LOCK TABLES `report_seq` WRITE;
/*!40000 ALTER TABLE `report_seq` DISABLE KEYS */;
INSERT INTO `report_seq` VALUES (351);
/*!40000 ALTER TABLE `report_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `route_id` bigint NOT NULL,
  `company_id` bigint DEFAULT NULL,
  `direction` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `geometry` varchar(8192) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`route_id`),
  KEY `FKk9jfktygs4c3yeeo8icifaqvo` (`company_id`),
  CONSTRAINT `FKk9jfktygs4c3yeeo8icifaqvo` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (204,1,'inbound','umzuEcx~dW?}BCmN?oD^AtHCbDALAVAXE@?DAVExAo@VIZITCNAPAxBAR?X?R?vB?LA`A@J?bAAfBAxAAb@@?l@?D@pBAt@Cf@Gp@GZI^KRKTCFMRORQPSV]^QNMHwA|ASVILABQZINK\\EPGTEVEZAXAv@?R@vCy@?iHCwDGoDA{AEyAUyA]c@Q','2í˜¸ì°¨'),(205,1,'inbound','kqzuEiy~dWiCwBsHkGwBeBGEkAcAeH}FoEkDm@g@aD}AeDiAe@OsHeC_Cq@cCkAOIWU]]k@q@a@o@k@iAs@_B}AmDUi@aDmHe@gAc@cA','3í˜¸ì°¨'),(206,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDr`@ExA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHzD`AjAXHB^FjBPt@?fAEbAEfBO`Cc@nEu@nEw@dJwAhDO?C','4í˜¸ì°¨'),(302,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@','1ì¶œê·¼'),(303,1,'outbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@','1í˜¸ì°¨ í‡´ê·¼'),(304,1,'outbound','umzuEcx~dW?}BCmN?oDf@AlHCbDALAVAXE@?DAVExAo@VIZITCNAPAxBAR?X?R?vB?LA`A@J?bAAfBAxAA`@@@??l@@vBAt@Cf@Gp@GZI^KRKTCFMRORQPSV]^QNMHwA|ASVILABQZINK\\EPGTEVEZAXAv@?R@vCcJCwDGu@?','2í˜¸ì°¨ í‡´ê·¼'),(305,1,'outbound','kqzuEiy~dWiCwBsHkGwBeBGEkAcAeH}FoEkDm@g@aD}AeDiAe@OsHeC_Cq@cCkAOIWU]]k@q@a@o@k@iAs@_B}AmDUi@aDmHe@gAc@cA','3í˜¸ì°¨ í‡´ê·¼'),(306,1,'outbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@','4í˜¸ì°¨ í‡´ê·¼'),(307,1,'outbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@','4í˜¸ì°¨ ì¶œê·¼'),(308,1,'outbound','umzuEcx~dW?}BCmN?oDtIEbDALAVAXE@?DAVExAo@VIZITCNAPAxBAR?X?R?vB?LA`A@J?bAAfBAxAA`@@@??l@@vBAt@Cf@Gp@GZI^KRKTCFMRORQPSV]^QNMHwA|ASVILABQZINK\\EPGTEVEZAXAv@?R@vCcJCwDGoDA{AEyAUyA]iAc@w@e@sAgAkDwCsHkGwBeBGEkAcAeH}FcDgCk@c@m@g@aD}AeDiAe@OsHeC_Cq@cCkAOIWU]]k@q@a@o@k@iAs@_B}AmDUi@aDmHe@gAc@cAkAiCOXN\\l@pAh@nAjEvJVj@h@lAp@~At@`Bd@bA`@r@h@r@`@`@DDv@f@`A`@t@Z~Br@|IvCbDjAhBv@`Ab@z@p@~DbDhHhG~@x@xBdBzN~LLJz@r@Ak@sAgAa@_@','4í˜¸ì°¨ë…¸ì„ '),(309,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@?a@mc@FcJCwDGoDA{AEyAUyA]iAc@w@e@sAgAkDwCsHkGwBeBGEkAcAeH}FcDgCk@c@m@g@?Xz@p@~DbDhHhG~@x@xBdBzN~LLJz@r@l@ZRFv@XxA\\xAVzABnDBtD@jJDr`@ExA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@dAV@]{Bk@uCy@IC{Aa@mA[_LmCyCq@e@Ei@?oNbBc@?_Ge@wGs@mc@FcJCwDGu@?','4í˜¸'),(310,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@?a@BkDLqBD{D@gJY?aFK}@Aa@Em@Si@[y@i@s@g@u@k@SOg@YWKWG]Gi@Ec@CA?a@AyA@gB@cA@K?aAAM@wB?S?Y?S?yB@Q@O@UB[HWHyAn@WDE@A?YDW@M@cD@uID?nDBlN?|B','44'),(311,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJD?]cJCwDGu@?yBA{AEyAUyA]iAc@w@e@sAgAkDwCsHkGwBeBGEkAcAeH}FcDgCk@c@m@g@?Xz@p@~DbDhHhG~@x@xBdBzN~LLJz@r@Ak@?aA?}BSnBCFADCBC@E?C?g@KkDwCsHkGwBeBGEkAcA@f@~@x@xBdBzN~LLJz@r@l@ZRFv@XxA\\xAVzABnDBtD@jJDb^C','44444'),(312,1,'outbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@dAV@]{Bk@uCy@IC{Aa@mA[_LmCyCq@e@Ei@?oNbBc@?_Ge@wGs@mc@FcJCwDGu@?yBA{AEyAUyA]iAc@w@e@sAgAa@_@','4í˜¸ì°¨ë²„ìŠ¤'),(352,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJDb^CnAAxA@lALhE`@`Gf@T?pD_@jI_Af@?^@|Cr@xKnCbDx@nAZZHtBh@dAV@]{Bk@uCy@IC{Aa@mA[_LmCyCq@e@Ei@?oNbBc@?_Ge@wGs@mc@FcJCwDGu@?yBA{AEyAUyA]iAc@w@e@sAgAa@_@','4í˜¸ì°¨ë…¸ì„ '),(353,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJD?]AwC?S@w@@YD[DWFUDQJ]HOP[@CHMRWvA}ALIPO\\_@RWPQNSLSBGJUJSH_@F[Fq@Bg@@u@AwB?m@A?a@AyA@gB@cA@K?aAAM@wB?S?Y?S?yB@Q@O@UB[HWHyAn@WDE@A?YDW@M@cD@uID?nDBlN?|B?`A@j@l@ZRFv@XxA\\xAVzABnDBtD@jJDb^C','4ë²„ìŠ¤'),(354,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJD?]AwC?S@w@@YD[DWFUDQJ]HOP[@CHMRWvA}ALIPO\\_@RWPQNSLSBGJUJSH_@F[Fq@Bg@@u@AwB?m@A?a@AyA@gB@cA@K?aAAM@wB?S?Y?S?yB@Q@O@UB[HWHyAn@WDE@A?YDW@M@cD@uID?nDBlN?|B?`A@j@l@ZRFv@XxA\\xAVzABnDBtD@jJDb^C','4í˜¸ì°¨ë²„ìŠ¤'),(355,1,'inbound','kmzuE_r~dWDSFMDENGTAv@XxA\\xAVzABnDBtD@jJD?]AwC?S@w@@YD[DWFUDQJ]HOP[@CHMRWvA}ALIPO\\_@RWPQNSLSBGJUJSH_@F[Fq@Bg@@u@AwB?m@A?a@AyA@gB@cA@K?aAAM@wB?S?Y?S?yB@Q@O@UB[HWHyAn@WDE@A?YDW@M@cD@uID?nDBlN?|B?`A@j@l@ZRFv@XxA\\xAVzABnDBtD@jJDb^C','4í˜¸ì°¨ë…¸ì„ ë²„ìŠ¤');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_and_station`
--

DROP TABLE IF EXISTS `route_and_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_and_station` (
  `route_id` bigint NOT NULL,
  `station_id` bigint NOT NULL,
  KEY `FK4y9wfpf2fxc3b75nb7phlhiea` (`station_id`),
  KEY `FKiq7oj1eulw2nc9411i9gwkpur` (`route_id`),
  CONSTRAINT `FK4y9wfpf2fxc3b75nb7phlhiea` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`),
  CONSTRAINT `FKiq7oj1eulw2nc9411i9gwkpur` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_and_station`
--

LOCK TABLES `route_and_station` WRITE;
/*!40000 ALTER TABLE `route_and_station` DISABLE KEYS */;
INSERT INTO `route_and_station` VALUES (204,257),(204,258),(204,259),(205,260),(205,261),(206,254),(302,254),(302,255),(302,256),(303,254),(303,255),(303,256),(304,257),(304,258),(304,302),(305,260),(305,303),(305,261),(306,254),(306,255),(306,256),(307,254),(307,255),(307,256),(308,257),(308,258),(308,303),(308,261),(308,260),(309,254),(309,255),(309,303),(309,256),(309,302),(310,254),(310,255),(310,258),(310,257),(311,254),(311,302),(311,303),(311,257),(311,255),(312,254),(312,255),(312,256),(312,302),(312,260),(352,254),(352,255),(352,256),(352,302),(352,260),(353,254),(353,258),(353,257),(353,255),(354,254),(354,258),(354,257),(354,255),(355,254),(355,258),(355,257),(355,255);
/*!40000 ALTER TABLE `route_and_station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_seq`
--

DROP TABLE IF EXISTS `route_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_seq`
--

LOCK TABLES `route_seq` WRITE;
/*!40000 ALTER TABLE `route_seq` DISABLE KEYS */;
INSERT INTO `route_seq` VALUES (451);
/*!40000 ALTER TABLE `route_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_station`
--

DROP TABLE IF EXISTS `route_station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_station` (
  `id` bigint NOT NULL,
  `route_id` bigint DEFAULT NULL,
  `station_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb7lhgapsmdgjls1s3lxhsllas` (`route_id`),
  KEY `FK2e184eo4cipupq3pc4x2iyahd` (`station_id`),
  CONSTRAINT `FK2e184eo4cipupq3pc4x2iyahd` FOREIGN KEY (`station_id`) REFERENCES `station` (`station_id`),
  CONSTRAINT `FKb7lhgapsmdgjls1s3lxhsllas` FOREIGN KEY (`route_id`) REFERENCES `route` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_station`
--

LOCK TABLES `route_station` WRITE;
/*!40000 ALTER TABLE `route_station` DISABLE KEYS */;
/*!40000 ALTER TABLE `route_station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route_station_seq`
--

DROP TABLE IF EXISTS `route_station_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route_station_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route_station_seq`
--

LOCK TABLES `route_station_seq` WRITE;
/*!40000 ALTER TABLE `route_station_seq` DISABLE KEYS */;
INSERT INTO `route_station_seq` VALUES (1);
/*!40000 ALTER TABLE `route_station_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station` (
  `station_id` bigint NOT NULL,
  `lat` double DEFAULT NULL,
  `lng` double DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`station_id`),
  KEY `FKilcxplfp9ux78uqas46pckb47` (`company_id`),
  CONSTRAINT `FKilcxplfp9ux78uqas46pckb47` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES (254,35.2023,126.81008,'SSAFY êµìœ¡ìž¥',1),(255,35.191778509664225,126.80987777782715,'ìž¥ë•ë™',1),(256,35.18043,126.80823,'í‘ì„ì‚¬ê±°ë¦¬',1),(257,35.20235,126.81106,'SSAFY',1),(258,35.1948859741854,126.81558033189918,'ì„±ë•ì¤‘í•™êµ',1),(259,35.20188,126.81045,'ë„ì°©ì§€',1),(260,35.20294,126.81125,'ì¶œë°œì§€2',1),(261,35.21658,126.82356,'ë„ì°©ì§€2',1),(302,35.19973,126.81006,'2í˜¸ì°¨ ì¢…ì°©',1),(303,35.20847696554337,126.81601556766086,'ì¤‘ê°„ì 1',1);
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station_seq`
--

DROP TABLE IF EXISTS `station_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `station_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station_seq`
--

LOCK TABLES `station_seq` WRITE;
/*!40000 ALTER TABLE `station_seq` DISABLE KEYS */;
INSERT INTO `station_seq` VALUES (401);
/*!40000 ALTER TABLE `station_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `company_id` bigint DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `i_realName` (`real_name`),
  KEY `FK2yuxsfrkkrnkn5emoobcnnc3r` (`company_id`),
  CONSTRAINT `FK2yuxsfrkkrnkn5emoobcnnc3r` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (_binary '“9(}E¯„¾â­·\'p',1,'busking@busking.org','driver4','010-1234-1234','ë²„ìŠ¤ê¸°ì‚¬ë‹˜ 4','DRIVER','driver4'),(_binary '*­\æ\Í\ÄÿN.ª|š—4–\ìp',1,'admin@a.b.kr','admin','010-1234-1232','ê´€ë¦¬ìž','COMPANY_ADMIN','admin'),(_binary '.½¼Yø@ü˜¤aX»&\ô†',1,'busking@busking.org','busking5','010-1234-1234','busking','EMPLOYEE','busking5'),(_binary '9\ì\ÑN5AS[\Î=Vx\ô',1,'busking@busking.org','driver2','010-1234-1234','ë²„ìŠ¤ê¸°ì‚¬ë‹˜ 2','DRIVER','driver2'),(_binary 'nP-\É@š­\ÃÇ~.N',1,'busking@busking.org','busking4','010-1234-1234','busking','EMPLOYEE','busking4'),(_binary '†8\Û\îZþH¸¾Ÿlu\ZX\'',1,'busking@busking.org','driver3','010-1234-1234','ë²„ìŠ¤ê¸°ì‚¬ë‹˜ 3','DRIVER','driver3'),(_binary '’ Ê¦\ïK·ŽKož&®Wz',1,'busking@busking.org','busking6','010-1234-1234','busking','EMPLOYEE','busking6'),(_binary 'Ÿ¡\à\ï\\•Ma½Ù„.\ä>\ç',1,'busking@busking.org','busking2','010-1234-1234','busking','EMPLOYEE','busking2'),(_binary 'ß¯t¶‚A0€8u\öVƒ\É',1,'busking@busking.org','busking3','010-1234-1234','busking','EMPLOYEE','busking3'),(_binary '\ålý–eF¶­´È³vˆž\×',1,'busking@busking.org','driver1','010-1234-1234','ë²„ìŠ¤ê¸°ì‚¬ë‹˜ 1','DRIVER','driver1'),(_binary '\éE2±cN\ö—G¯\Â\ð2¯w',1,'busking@busking.org','busking1','010-1234-1234','busking','EMPLOYEE','busking1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-18  9:23:24

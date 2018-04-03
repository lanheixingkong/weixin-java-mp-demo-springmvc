/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-04-03 14:11:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jieyou_counseling
-- ----------------------------
DROP TABLE IF EXISTS `jieyou_counseling`;
CREATE TABLE `jieyou_counseling` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user_id` varchar(100) NOT NULL,
  `from_user_name` varchar(100) NOT NULL,
  `to_user_id` varchar(100) DEFAULT NULL,
  `to_user_name` varchar(100) DEFAULT NULL,
  `question` varchar(1000) NOT NULL,
  `answer` varchar(1000) DEFAULT NULL,
  `state` tinyint(10) NOT NULL DEFAULT '1',
  `createdate` datetime NOT NULL,
  `answerdate` datetime DEFAULT NULL,
  `rel_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

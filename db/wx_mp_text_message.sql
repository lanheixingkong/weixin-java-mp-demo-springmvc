/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-04-03 14:11:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wx_mp_text_message
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_text_message`;
CREATE TABLE `wx_mp_text_message` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `to_user` varchar(100) NOT NULL,
  `from_user` varchar(100) NOT NULL,
  `create_time` bigint(10) NOT NULL,
  `msg_type` varchar(20) NOT NULL,
  `content` text NOT NULL,
  `msg_id` bigint(10) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

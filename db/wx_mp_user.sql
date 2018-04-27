/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-04-03 14:11:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wx_mp_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_mp_user`;
CREATE TABLE `wx_mp_user` (
  `open_id` varchar(100) NOT NULL,
  `subscribe` tinyint(1) NOT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `language` varchar(10) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `province` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `head_img_url` varchar(500) DEFAULT NULL,
  `subscribe_time` bigint(10) DEFAULT NULL,
  `union_id` varchar(100) DEFAULT NULL,
  `sex_id` int(10) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `group_id` int(10) DEFAULT NULL,
  `tag_ids` varchar(500) DEFAULT NULL,
  `createdate` datetime NOT NULL,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

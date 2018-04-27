/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-04-04 16:08:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jieyou_add_content
-- ----------------------------
DROP TABLE IF EXISTS `jieyou_add_content`;
CREATE TABLE `jieyou_add_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `counseling_id` bigint(20) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `createdate` datetime NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `user_type` tinyint(10) NOT NULL COMMENT '1 fromUserId 2 toUserId',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

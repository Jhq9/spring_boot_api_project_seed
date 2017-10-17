/*
 Navicat Premium Data Transfer

 Source Server         : May
 Source Server Type    : MySQL
 Source Server Version : 50635
 Source Host           : localhost
 Source Database       : jwt

 Target Server Type    : MySQL
 Target Server Version : 50635
 File Encoding         : utf-8

 Date: 10/17/2017 10:16:06 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', 'ROLE_AUTHOR'), ('2', 'ROLE_USER'), ('3', 'ROLE_VISITER');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL,
  `email` varchar(32) NOT NULL,
  `role` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `password` varchar(128) NOT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `phone` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  KEY `fk_role` (`role`) USING BTREE,
  KEY `email` (`email`) USING BTREE,
  CONSTRAINT `FKl5alypubd40lwejc45vl35wjb` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('12', 'jhq', '1044038054@qq.com', '2', '2017-10-16 23:37:58', '$2a$10$RpWEAz9vvnjKwbUoaNyuKuyzDHDlnUMnpQFWrlIZH0z1Zr7Zn5PBC', '2017-10-16 23:37:58', '15700084693'), ('13', 'jhq', '1044038054@qq.com', '2', '2017-10-17 07:38:40', '$2a$10$3blZnGvgjyk9QAMA3U/.kOaTMqwdNAygMwUxR/Tnnabj.MvIbcEO6', '2017-10-17 07:38:40', '15700084694');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

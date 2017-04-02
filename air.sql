/*
Navicat MySQL Data Transfer

Source Server         : 101.200.54.205
Source Server Version : 50716
Source Host           : 101.200.54.205:3306
Source Database       : air

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-04-02 11:22:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for trainnumber
-- ----------------------------
DROP TABLE IF EXISTS `trainnumber`;
CREATE TABLE "trainnumber" (
  "id" bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  "endSite" varchar(255) DEFAULT NULL COMMENT '终到站',
  "name" varchar(255) DEFAULT NULL COMMENT '车次名称',
  "seatsNumber" bigint(20) DEFAULT NULL COMMENT '余票数量(座位数)',
  "startSite" varchar(255) DEFAULT NULL COMMENT '起始站',
  "price" float NOT NULL COMMENT '价格',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for trainorder
-- ----------------------------
DROP TABLE IF EXISTS `trainorder`;
CREATE TABLE "trainorder" (
  "id" bigint(20) NOT NULL AUTO_INCREMENT,
  "createTime" bigint(20) DEFAULT NULL COMMENT '订单创建时间',
  "discount" float DEFAULT NULL COMMENT '折扣值',
  "trainId" bigint(20) DEFAULT NULL COMMENT '车次id',
  "userId" bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE "user" (
  "id" bigint(20) NOT NULL AUTO_INCREMENT,
  "idCardNumber" varchar(255) DEFAULT NULL COMMENT '身份证号码',
  "loginName" varchar(255) DEFAULT NULL COMMENT '登录名',
  "name" varchar(255) DEFAULT NULL COMMENT '姓名',
  "password" varchar(255) DEFAULT NULL COMMENT '密码',
  "phone" varchar(255) DEFAULT NULL COMMENT '手机号',
  "sex" int(11) NOT NULL DEFAULT '3' COMMENT '性别 1 男 2女 3是不知道',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for usercontact
-- ----------------------------
DROP TABLE IF EXISTS `usercontact`;
CREATE TABLE "usercontact" (
  "id" bigint(20) NOT NULL AUTO_INCREMENT,
  "contactUserId" bigint(20) DEFAULT NULL COMMENT '联系人id',
  "userId" bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY ("id")
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*
Navicat MySQL Data Transfer

Source Server         : yinhai-162
Source Server Version : 50536
Source Host           : 192.168.10.162:3306
Source Database       : apimanager

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2017-01-11 16:07:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for environments
-- ----------------------------
DROP TABLE IF EXISTS `environments`;
CREATE TABLE `environments` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `projectId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for environments_list
-- ----------------------------
DROP TABLE IF EXISTS `environments_list`;
CREATE TABLE `environments_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `envId` int(10) NOT NULL,
  `paramName` varchar(255) NOT NULL,
  `paramValue` varchar(510) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for find_password
-- ----------------------------
DROP TABLE IF EXISTS `find_password`;
CREATE TABLE `find_password` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userid` int(10) NOT NULL,
  `token` varchar(255) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0有效 1无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for interface
-- ----------------------------
DROP TABLE IF EXISTS `interface`;
CREATE TABLE `interface` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '接口名称',
  `description` varchar(3000) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '接口描述',
  `example` text COMMENT '示例数据',
  `folderId` int(10) NOT NULL COMMENT '分类id',
  `url` varchar(300) CHARACTER SET utf8mb4 NOT NULL COMMENT '接口地址',
  `requestMethod` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '请求方式',
  `contentType` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '请求数据类型',
  `requestHeaders` text CHARACTER SET utf8mb4 COMMENT '请求头 json格式',
  `requestArgs` text CHARACTER SET utf8mb4 COMMENT '请求参数 json格式',
  `requestRootElement` varchar(50) DEFAULT NULL COMMENT 'xml数据的rootElement',
  `responseArgs` text CHARACTER SET utf8mb4 COMMENT '响应数据 json格式',
  `responseRootElement` varchar(50) DEFAULT NULL COMMENT 'xml数据的rootElement',
  `moduleId` int(10) NOT NULL COMMENT '模板id',
  `projectId` int(10) NOT NULL COMMENT '项目id',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '最新更新时间',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  `dataType` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '响应类型',
  `protocol` varchar(30) CHARACTER SET utf8mb4 NOT NULL COMMENT '协议',
  `status` char(10) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='接口主表';

-- ----------------------------
-- Table structure for interface_folder
-- ----------------------------
DROP TABLE IF EXISTS `interface_folder`;
CREATE TABLE `interface_folder` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '接口分类名称',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `moduleId` int(10) DEFAULT NULL COMMENT '模块id',
  `projectId` int(10) DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='接口分类';

-- ----------------------------
-- Table structure for interface_ws
-- ----------------------------
DROP TABLE IF EXISTS `interface_ws`;
CREATE TABLE `interface_ws` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `apiId` int(10) NOT NULL,
  `targetNamespace` varchar(100) NOT NULL,
  `endpointAddress` varchar(100) NOT NULL,
  `methodName` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `apiId` (`apiId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='webservice接口扩展类';

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `lastUpdateTime` datetime NOT NULL,
  `projectId` int(10) NOT NULL COMMENT '项目id',
  `createTime` datetime NOT NULL,
  `requestHeaders` text COMMENT '模块全局请求头',
  `requestArgs` text COMMENT '模块全局请求参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='项目模块';

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '项目名称',
  `description` varchar(300) DEFAULT NULL COMMENT '项目描述',
  `teamId` int(10) DEFAULT NULL COMMENT '团队id',
  `createTime` datetime NOT NULL,
  `userId` int(10) NOT NULL COMMENT '项目拥有人',
  `status` varchar(20) NOT NULL DEFAULT 'ENABLE',
  `permission` varchar(20) NOT NULL DEFAULT 'PRIVATE' COMMENT '''PRIVATE'' 自己可见 ''PUBLIC'' 所有人可见',
  `envId` int(10) DEFAULT NULL COMMENT '环境ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- ----------------------------
-- Table structure for project_user
-- ----------------------------
DROP TABLE IF EXISTS `project_user`;
CREATE TABLE `project_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `projectId` int(10) NOT NULL,
  `userId` int(10) NOT NULL,
  `createTime` datetime NOT NULL,
  `status` char(10) NOT NULL DEFAULT 'NORMAL' COMMENT '''NORMAL'' ''OWNER'' ''TEAM''',
  `editable` char(3) NOT NULL DEFAULT 'YES' COMMENT '是否可编辑 项目拥有人可编辑 团队成员可编辑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='项目人员';

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '团队名称',
  `description` varchar(300) DEFAULT NULL COMMENT '团队描述',
  `userId` int(10) NOT NULL COMMENT '团队拥有人(创建人)',
  `createTime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT 'ENABLE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

-- ----------------------------
-- Table structure for team_user
-- ----------------------------
DROP TABLE IF EXISTS `team_user`;
CREATE TABLE `team_user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `teamId` int(10) NOT NULL,
  `userId` int(10) NOT NULL,
  `createTime` datetime NOT NULL,
  `teamUserType` char(10) NOT NULL DEFAULT 'MEMBER' COMMENT '团队成员类型 ''OWNER'' ''MEMBER''',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='团队成员表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `type` varchar(5) DEFAULT 'USER',
  `nickname` varchar(30) DEFAULT NULL COMMENT '别名',
  `status` char(10) DEFAULT 'ENABLE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

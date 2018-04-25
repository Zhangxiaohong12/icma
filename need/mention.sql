/*
 Navicat Premium Data Transfer

 Source Server         : mention
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : mention

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 04/25/2018 11:12:57 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `T_CLASS`
-- ----------------------------
DROP TABLE IF EXISTS `T_CLASS`;
CREATE TABLE `T_CLASS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CLASS_NAME` varchar(255) DEFAULT NULL,
  `XI_BIE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_CLASS`
-- ----------------------------
BEGIN;
INSERT INTO `T_CLASS` VALUES ('1', '15软件4班', '1'), ('2', '15软件3班', '1');
COMMIT;

-- ----------------------------
--  Table structure for `T_IMAGE`
-- ----------------------------
DROP TABLE IF EXISTS `T_IMAGE`;
CREATE TABLE `T_IMAGE` (
  `IMAGE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IMAGE_NAME` varchar(60) DEFAULT NULL,
  `IMAGE_CODE` varchar(200) DEFAULT NULL,
  `IMAGE_URL` varchar(200) DEFAULT NULL,
  `PERSON_ID` varchar(30) DEFAULT NULL,
  `FACE_ID` varchar(30) DEFAULT NULL,
  `IMAGE_PATH` varchar(255) DEFAULT NULL COMMENT '图片绝对路径',
  PRIMARY KEY (`IMAGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_IMAGE`
-- ----------------------------
BEGIN;
INSERT INTO `T_IMAGE` VALUES ('24', 'IMG_3223.JPG', null, '/Users/icma-upload-img/1513454/IMG_3223.JPG', '1513454', '2511358056665753420', '/Users/icma-upload-img/1513454/IMG_3223.JPG'), ('26', 'IMG_1722.JPG', null, '/Users/icma-upload-img/1515555/IMG_1722.JPG', '1515555', '2549171902917696840', '/Users/icma-upload-img/1515555/IMG_1722.JPG');
COMMIT;

-- ----------------------------
--  Table structure for `T_ITEM`
-- ----------------------------
DROP TABLE IF EXISTS `T_ITEM`;
CREATE TABLE `T_ITEM` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  `ORDER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_ITEM`
-- ----------------------------
BEGIN;
INSERT INTO `T_ITEM` VALUES ('1', 'STATUS', '状态', '状态', '1', '1'), ('2', 'USER_STATUS', '用户状态', '用户状态', '1', '2'), ('12', 'SEX', '性别', '性别', '1', '1'), ('13', 'ROLE_STATUS', '角色状态', '角色状态', '1', '4');
COMMIT;

-- ----------------------------
--  Table structure for `T_ITEM_DETAIL`
-- ----------------------------
DROP TABLE IF EXISTS `T_ITEM_DETAIL`;
CREATE TABLE `T_ITEM_DETAIL` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CATEGORY` varchar(255) DEFAULT NULL,
  `CODE` varchar(255) DEFAULT NULL,
  `ORDER_ID` int(11) DEFAULT NULL,
  `SUPPER_CODE` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `STATUS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_ITEM_DETAIL`
-- ----------------------------
BEGIN;
INSERT INTO `T_ITEM_DETAIL` VALUES ('1', 'STATUS', '0', '2', '0', '失效', '失效', '1'), ('2', 'STATUS', '1', '1', '0', '有效', '有效', '1'), ('7', 'SEX', '0', '2', '0', '女', '女', '1'), ('8', 'SEX', '1', '1', '0', '男', '男', '1'), ('9', 'USER_STATUS', '0', '2', '0', '失效', '失效', '1'), ('10', 'USER_STATUS', '1', '1', '0', '有效', '有效', '1'), ('13', 'ROLE_STATUS', '0', '2', '0', '失效', '失效', '1'), ('14', 'ROLE_STATUS', '1', '1', '0', '有效', '有效', '1');
COMMIT;

-- ----------------------------
--  Table structure for `T_MENU`
-- ----------------------------
DROP TABLE IF EXISTS `T_MENU`;
CREATE TABLE `T_MENU` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_ICON` varchar(50) DEFAULT NULL,
  `MENU_CODE` varchar(50) NOT NULL,
  `MENU_NAME` varchar(50) NOT NULL,
  `MENU_HREF` varchar(200) DEFAULT NULL,
  `SUPER_CODE` varchar(50) DEFAULT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `UPDATE_TIME` date DEFAULT NULL,
  `UPDATE_BY` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=627 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_MENU`
-- ----------------------------
BEGIN;
INSERT INTO `T_MENU` VALUES ('600', 'icon-home', 'home', '主页', '/home', null, '2017-12-18', '2017-12-18', '管理员'), ('601', 'icon-wrench', 'basedata', '基础数据设置', null, null, '2017-12-18', '2018-03-23', '管理员'), ('602', 'icon-glass', 'role_manage', '角色管理', '/role/search', 'basedata', '2017-12-18', '2018-03-30', '超级管理员'), ('603', 'icon-user', 'user_manage', '用户管理', '/user/search', 'basedata', '2017-12-18', '2018-03-30', '超级管理员'), ('604', 'icon-zoom-in', 'right_manage', '权限管理', '/right/search', 'basedata', '2017-12-18', '2017-12-18', '管理员'), ('605', 'icon-align-justify', 'menu_manage', '菜单管理', '/menu/search', 'systemdata', '2017-12-18', '2018-03-23', '超级管理员'), ('606', 'icon-user', 'person_manage', '个体管理', '/person/search', 'basedata', '2017-12-18', '2018-03-30', '超级管理员'), ('607', 'icon-flag', 'sxc_manage', '机构管理', null, 'basedata', '2017-12-21', '2018-03-30', '超级管理员'), ('608', 'icon-bookmark', 'item_manage', '数据字典', '/item/search', 'systemdata', '2018-03-14', '2018-03-30', '超级管理员'), ('609', 'icon-wrench', 'systemdata', '系统数据设置 ', null, null, '2018-03-15', '2018-03-23', '管理员'), ('612', 'icon-globe', 'school_manage', '学校管理', '/sxc/school/search', 'sxc_manage', '2018-03-19', null, null), ('613', 'icon-globe', 'xiBie_manage', '系别/院校管理', '/sxc/xiBie/search', 'sxc_manage', '2018-03-19', null, null), ('614', 'icon-globe', 'class_manage', '班级管理', '/sxc/class/search', 'sxc_manage', '2018-03-19', null, null), ('615', 'icon-repeat', 'update-pwd', '修改密码', '/to-update-pwd/update', 'basedata', '2018-03-23', '2018-04-21', '超级管理员'), ('626', 'icon-tags', 'valiFace', '人脸识别', '/img/to-test', 'basedata', '2018-04-24', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `T_PERSON`
-- ----------------------------
DROP TABLE IF EXISTS `T_PERSON`;
CREATE TABLE `T_PERSON` (
  `PERSON_ID` varchar(60) NOT NULL,
  `PERSON_NAME` varchar(50) DEFAULT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PERSON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_PERSON`
-- ----------------------------
BEGIN;
INSERT INTO `T_PERSON` VALUES ('1513454', '周宇恒', '101'), ('1515555', '陈景富', '103');
COMMIT;

-- ----------------------------
--  Table structure for `T_RIGHT`
-- ----------------------------
DROP TABLE IF EXISTS `T_RIGHT`;
CREATE TABLE `T_RIGHT` (
  `RIGHT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RIGHT_NAME` varchar(30) NOT NULL,
  `RIGHT_CODE` varchar(60) NOT NULL,
  `RIGHT_DESC` varchar(128) NOT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `UPDATE_TIME` date DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  `IS_MENU_RIGHT` varchar(11) NOT NULL,
  `PARENT_RIGHT_ID` varchar(60) NOT NULL,
  `PARENT_MENU_ID` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`RIGHT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=586 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_RIGHT`
-- ----------------------------
BEGIN;
INSERT INTO `T_RIGHT` VALUES ('500', '智能点名系统', 'mention_sys', '智能点名系统', '2017-12-18', '2017-12-18', null, '2', '0', '0'), ('501', '角色管理', 'basedata:role_manage', '角色管理', '2017-12-20', '2018-03-30', '602', '1', '502', '601'), ('502', '基础数据设置', 'basedata', '基础数据设置', '2017-12-20', '2018-03-23', '601', '1', '500', ''), ('503', '用户管理', 'basedata:user_manage', '用户管理', '2017-12-20', '2018-03-30', '603', '1', '502', '601'), ('504', '菜单管理', 'systemdata:menu_manage', '菜单管理', '2017-12-20', '2018-03-23', '605', '1', '534', '609'), ('505', '权限管理', 'basedata:right_manage', '权限管理', '2017-12-20', '2017-12-20', '604', '1', '502', '601'), ('506', '个体管理', 'basedata:person_manage', '个体管理', '2017-12-20', '2018-03-30', '606', '1', '502', '601'), ('507', '主页', 'home', '主页', '2017-12-20', '2017-12-20', '600', '1', '500', null), ('512', '用户增加', 'user:add', '用户增加', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('513', '用户修改', 'user:update', '用户修改', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('514', '用户删除', 'user:delete', '用户删除', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('515', '用户查询', 'user:search', '用户查询', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('516', '菜单查询', 'menu:search', '菜单查询', '2017-12-21', '2017-12-21', null, '0', '504', '605'), ('517', '角色查询', 'role:search', '角色查询', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('518', '角色修改', 'role:update', '角色查询', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('519', '角色新增', 'role:add', '角色新增', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('520', '角色删除', 'role:delete', '角色删除', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('521', '角色管理分配权限', 'role:giveRights', '角色管理分配权限', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('522', '角色管理查看权限', 'role:lookRights', '角色管理查看权限', '2017-12-21', '2017-12-21', null, '0', '501', '602'), ('523', '权限查询', 'right:search', '权限查询', '2017-12-21', '2017-12-21', null, '0', '505', '604'), ('524', '权限新增', 'right:add', '权限新增', '2017-12-21', '2017-12-21', null, '0', '505', '604'), ('525', '权限修改', 'right:update', '权限修改', '2017-12-21', '2017-12-21', null, '0', '505', '604'), ('526', '权限删除', 'right:delete', '权限删除', '2017-12-21', '2017-12-21', null, '0', '505', '604'), ('527', '用户管理查看角色', 'user:lookRole', '用户管理查看角色', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('528', '用户管理分配角色', 'user:giveRole', '用户管理分配角色', '2017-12-21', '2017-12-21', null, '0', '503', '603'), ('529', '个体查询', 'person:search', '个体查询', '2017-12-21', '2017-12-21', null, '0', '506', '606'), ('530', '个体新增', 'person:add', '个体新增', '2017-12-21', '2017-12-21', null, '0', '506', '606'), ('531', '个体修改', 'person:update', '个体修改', '2017-12-21', '2017-12-21', null, '0', '506', '606'), ('532', '个体删除', 'person:delete', '个体删除', '2017-12-21', '2017-12-21', null, '0', '506', '606'), ('533', '数据字典', 'systemdata:item_manage', '数据字典', '2018-03-16', '2018-03-30', '608', '1', '534', '609'), ('534', '系统数据设置 ', 'systemdata', '系统数据设置 ', '2018-03-16', '2018-03-23', '609', '1', '500', ''), ('536', '机构管理', 'basedata:sxc_manage', '机构管理', '2018-03-16', '2018-03-30', '607', '1', '502', '601'), ('538', '数据字典-添加', 'item:add', '数据字典-添加', '2018-03-17', null, null, '0', '533', '608'), ('539', '数据字典-修改', 'item:update', '数据字典-修改', '2018-03-17', null, null, '0', '533', '608'), ('540', '数据字典-删除', 'item:delete', '数据字典-删除', '2018-03-17', null, null, '0', '533', '608'), ('541', '数据字典详细-查看', 'itemDetail:search', '数据字典详细-查看', '2018-03-17', null, null, '0', '533', '608'), ('542', '数据字典详细-修改', 'itemDetail:update', '数据字典详细-修改', '2018-03-17', null, null, '0', '533', '608'), ('543', '数据字典详细-删除', 'itemDetail:delete', '数据字典详细-删除', '2018-03-17', null, null, '0', '533', '608'), ('544', '数据字典-查看', 'item:search', '数据字典-查看', '2018-03-17', null, null, '0', '533', '608'), ('545', '菜单管理-新增', 'menu:add', '菜单管理-新增', '2018-03-19', null, null, '0', '504', '605'), ('546', '菜单管理-修改', 'menu:update', '菜单管理-修改', '2018-03-19', null, null, '0', '504', '605'), ('547', '菜单管理-删除', 'menu:delete', '菜单管理-删除', '2018-03-19', null, null, '0', '504', '605'), ('549', '学校管理', 'sxc_manage:school_manage', '学校管理', '2018-03-19', '2018-03-19', '612', '1', '536', '607'), ('550', '系别/院校管理', 'sxc_manage:xiBie_manage', '系别/院校管理', '2018-03-19', '2018-03-19', '613', '1', '536', '607'), ('551', '班级管理', 'sxc_manage:class_manage', '班级管理', '2018-03-19', '2018-03-19', '614', '1', '536', '607'), ('552', '学校管理-查看', 'school:search', '学校管理-查看', '2018-03-19', null, null, '0', '549', '612'), ('553', '学校管理-更新', 'school:update', '学校管理-修改', '2018-03-19', null, null, '0', '549', '612'), ('554', '学校管理-删除', 'school:delete', '学校管理-删除', '2018-03-19', null, null, '0', '549', '612'), ('555', '系别管理-查看', 'xiBie:search', '系别管理-查看', '2018-03-19', null, null, '0', '550', '613'), ('556', '系别管理-修改', 'xiBie:update', '系别管理-修改', '2018-03-19', null, null, '0', '550', '613'), ('557', '学校管理-新增', 'school:add', '学校管理-新增', '2018-03-19', null, null, '0', '549', '612'), ('558', '系别管理-新增', 'xiBie:add', '系别管理-新增', '2018-03-19', null, null, '0', '550', '613'), ('559', '系别管理-删除', 'xiBie:delete', '系别管理-删除', '2018-03-19', null, null, '0', '550', '613'), ('560', '班级管理-查看', 'class:search', '班级管理-查看', '2018-03-19', null, null, '0', '551', '614'), ('561', '班级管理-新增', 'class:add', '班级管理-新增', '2018-03-19', null, null, '0', '551', '614'), ('562', '班级管理-修改', 'class:update', '班级管理-修改', '2018-03-19', null, null, '0', '551', '614'), ('563', '班级管理-删除', 'class:delete', '班级管理-删除', '2018-03-19', null, null, '0', '551', '614'), ('565', '重置用户密码', 'user:reSetPwd', '重置用户密码', '2018-03-23', null, null, '0', '503', '603'), ('566', '修改密码', 'basedata:update-pwd', '修改密码', '2018-03-23', '2018-04-21', '615', '1', '502', '601'), ('567', '用户修改密码', 'user:updatePwd', '用户修改密码', '2018-03-23', '2018-03-23', null, '0', '566', '615'), ('571', '人脸-新增', 'image:add', '人脸-新增', '2018-03-26', null, null, '0', '506', '606'), ('572', '人脸-删除', 'image:delete', '人脸-删除', '2018-03-26', null, null, '0', '506', '606'), ('573', '个体管理-详情', 'person:view', '个体管理-详情', '2018-03-27', null, null, '0', '506', '606'), ('585', '人脸识别', 'basedata:valiFace', '人脸识别', '2018-04-24', '2018-04-24', '626', '1', '502', '601');
COMMIT;

-- ----------------------------
--  Table structure for `T_ROLE`
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE`;
CREATE TABLE `T_ROLE` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(60) NOT NULL,
  `ROLE_DESC` varchar(128) NOT NULL,
  `ROLE_CODE` varchar(30) NOT NULL,
  `ROLE_STATUS` int(11) NOT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `UPDATE_TIME` date DEFAULT NULL,
  `ROLE_OPERATOR` varchar(20) DEFAULT NULL,
  `ADMIN` int(255) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=406 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_ROLE`
-- ----------------------------
BEGIN;
INSERT INTO `T_ROLE` VALUES ('400', '超级管理员', '超级管理员', 'Administrator', '1', '2017-12-18', '2018-03-23', '管理员', '1'), ('401', '教师', '教师', 'teacher', '1', '2017-12-20', '2018-03-17', '管理员', '0'), ('402', '学生', '学生', 'student', '1', '2017-12-20', '2017-12-20', '管理员', '0'), ('403', '官方新增角色', '官方给学生新增自己的角色', 'sys_add', '0', '2018-03-23', '2018-04-21', '管理员', '0'), ('405', '普通管理员', '普通管理员', 'admin', '1', '2018-03-23', null, '管理员', '0');
COMMIT;

-- ----------------------------
--  Table structure for `T_ROLE_RIGHT_RELATION`
-- ----------------------------
DROP TABLE IF EXISTS `T_ROLE_RIGHT_RELATION`;
CREATE TABLE `T_ROLE_RIGHT_RELATION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `RIGHT_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_ROLE_RIGHT_RELATION`
-- ----------------------------
BEGIN;
INSERT INTO `T_ROLE_RIGHT_RELATION` VALUES ('665', '400', '538'), ('666', '400', '539'), ('667', '400', '540'), ('668', '400', '541'), ('669', '400', '542'), ('670', '400', '543'), ('671', '400', '544'), ('672', '400', '533'), ('673', '400', '552'), ('674', '400', '553'), ('675', '400', '554'), ('676', '400', '557'), ('677', '400', '549'), ('678', '400', '555'), ('679', '400', '556'), ('680', '400', '558'), ('681', '400', '559'), ('682', '400', '550'), ('683', '400', '560'), ('684', '400', '561'), ('685', '400', '562'), ('686', '400', '563'), ('687', '400', '551'), ('688', '400', '536'), ('689', '400', '534'), ('690', '400', '517'), ('691', '400', '518'), ('692', '400', '519'), ('693', '400', '520'), ('694', '400', '521'), ('695', '400', '522'), ('696', '400', '501'), ('697', '400', '565'), ('698', '400', '512'), ('699', '400', '513'), ('700', '400', '514'), ('701', '400', '515'), ('702', '400', '527'), ('703', '400', '528'), ('704', '400', '503'), ('705', '400', '545'), ('706', '400', '546'), ('707', '400', '547'), ('708', '400', '516'), ('709', '400', '504'), ('710', '400', '523'), ('711', '400', '524'), ('712', '400', '525'), ('713', '400', '526'), ('714', '400', '505'), ('715', '400', '529'), ('716', '400', '530'), ('717', '400', '531'), ('718', '400', '532'), ('719', '400', '506'), ('720', '400', '507'), ('721', '400', '567'), ('722', '400', '566'), ('723', '400', '502'), ('724', '400', '500'), ('725', '400', '568'), ('726', '400', '569'), ('727', '400', '570'), ('728', '405', '500'), ('729', '405', '534'), ('730', '405', '502'), ('731', '405', '536'), ('732', '405', '552'), ('733', '405', '553'), ('734', '405', '554'), ('735', '405', '557'), ('736', '405', '555'), ('737', '405', '556'), ('738', '405', '558'), ('739', '405', '559'), ('740', '405', '560'), ('741', '405', '561'), ('742', '405', '562'), ('743', '405', '563'), ('744', '405', '549'), ('745', '405', '550'), ('746', '405', '551'), ('747', '405', '501'), ('748', '405', '517'), ('749', '405', '518'), ('750', '405', '519'), ('751', '405', '520'), ('752', '405', '521'), ('753', '405', '522'), ('754', '405', '566'), ('755', '405', '567'), ('756', '405', '503'), ('757', '405', '565'), ('758', '405', '512'), ('759', '405', '513'), ('760', '405', '514'), ('761', '405', '515'), ('762', '405', '527'), ('763', '405', '528'), ('764', '405', '506'), ('765', '405', '529'), ('766', '405', '530'), ('767', '405', '531'), ('768', '405', '532'), ('769', '405', '505'), ('770', '405', '523'), ('771', '405', '524'), ('772', '405', '525'), ('773', '405', '526'), ('774', '405', '507'), ('775', '400', '571'), ('776', '400', '572'), ('777', '400', '573'), ('967', '402', '500'), ('968', '402', '502'), ('969', '402', '536'), ('970', '402', '549'), ('971', '402', '550'), ('972', '402', '551'), ('973', '402', '503'), ('974', '402', '506'), ('975', '402', '567'), ('976', '402', '566'), ('977', '402', '552'), ('978', '402', '555'), ('979', '402', '560'), ('980', '402', '513'), ('981', '402', '515'), ('982', '402', '573'), ('983', '402', '571'), ('984', '402', '572'), ('985', '402', '529'), ('986', '402', '530'), ('987', '402', '531'), ('988', '402', '507'), ('989', '403', '500'), ('990', '403', '534'), ('991', '403', '533'), ('992', '403', '504'), ('993', '403', '545'), ('994', '403', '542'), ('995', '403', '538'), ('997', '400', '577'), ('1001', '400', '581'), ('1002', '400', '582'), ('1005', '400', '585');
COMMIT;

-- ----------------------------
--  Table structure for `T_SCHOOL`
-- ----------------------------
DROP TABLE IF EXISTS `T_SCHOOL`;
CREATE TABLE `T_SCHOOL` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCHOOL_NAME` varchar(255) DEFAULT NULL,
  `SCHOOL_CODE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_SCHOOL`
-- ----------------------------
BEGIN;
INSERT INTO `T_SCHOOL` VALUES ('1', '广东工贸职业技术学院', '12959'), ('3', '清华大学', '10003');
COMMIT;

-- ----------------------------
--  Table structure for `T_USER`
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ACCOUNT` varchar(30) NOT NULL,
  `USER_NAME` varchar(50) NOT NULL,
  `USER_IDCARD` varchar(200) DEFAULT NULL,
  `USER_PASSWORD` varchar(50) NOT NULL,
  `CLASS_ID` int(11) NOT NULL,
  `USER_SEX` varchar(11) NOT NULL,
  `USER_STATUS` varchar(11) NOT NULL,
  `CREATE_TIME` date DEFAULT NULL,
  `UPDATE_TIME` date DEFAULT NULL,
  `PERSON_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_USER`
-- ----------------------------
BEGIN;
INSERT INTO `T_USER` VALUES ('100', 'mention', '超级管理员', null, 'ccf3e494c8e5f27d0113214a226ad6a2', '0', '1', '1', '2017-12-18', '2017-12-18', null), ('101', '1513454', '周宇恒', '440421199606088032', '0c8f41d6a14d506a60fd37b845be1a01', '1', '1', '1', '2017-12-22', '2018-04-21', '1513454'), ('103', '1515555', '陈景富', '440421199404055067', '7c15d6aa3e3b1e3e5c912ca2692d8f8a', '1', '1', '1', '2018-04-24', null, '1515555');
COMMIT;

-- ----------------------------
--  Table structure for `T_USER_ROLE_RELATION`
-- ----------------------------
DROP TABLE IF EXISTS `T_USER_ROLE_RELATION`;
CREATE TABLE `T_USER_ROLE_RELATION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_USER_ROLE_RELATION`
-- ----------------------------
BEGIN;
INSERT INTO `T_USER_ROLE_RELATION` VALUES ('200', '100', '400'), ('204', '101', '402'), ('205', '102', '405');
COMMIT;

-- ----------------------------
--  Table structure for `T_XI_BIE`
-- ----------------------------
DROP TABLE IF EXISTS `T_XI_BIE`;
CREATE TABLE `T_XI_BIE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `XI_BIE_NAME` varchar(255) DEFAULT NULL,
  `SCHOOL_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `T_XI_BIE`
-- ----------------------------
BEGIN;
INSERT INTO `T_XI_BIE` VALUES ('1', '计算机工程系', '1'), ('2', '机械工程系', '1'), ('3', '应用外语系', '1'), ('4', '电气自动化系', '1'), ('6', '测绘遥感信息工程系', '1'), ('8', '工商管理系', '1'), ('9', '经济贸易系', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

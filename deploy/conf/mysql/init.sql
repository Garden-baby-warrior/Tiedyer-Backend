/*
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Schema         : tiedyer
 Date: 18/12/2023 20:03:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_badge
-- ----------------------------
DROP TABLE IF EXISTS `t_badge`;
CREATE TABLE `t_badge` (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '徽章名',
  `color_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '彩色头像地址-用户获得时显示',
  `grey_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '灰色头像地址-用户未获得时显示',
  `points` int NOT NULL COMMENT '徽章兑换需要的积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='徽章表';

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `priority` int NOT NULL COMMENT '优先级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1722531135570972673 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品分类表';

-- ----------------------------
-- Table structure for t_logistics
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics`;
CREATE TABLE `t_logistics` (
  `id` bigint NOT NULL COMMENT '主键',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `logistics_companies` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '物流公司',
  `logistics_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '物流编号',
  `recipient_name` varchar(10) NOT NULL COMMENT '收件人姓名',
  `recipient_phone` varchar(20) NOT NULL COMMENT '收件人电话',
  `recipient_area` varchar(255) NOT NULL COMMENT '收件人所处地区',
  `recipient_address` varchar(255) NOT NULL COMMENT '收件人具体地址',
  `sender_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寄件人姓名',
  `sender_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寄件人电话',
  `sender_area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '寄件人所处地区',
  `sender_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '具体地址',
  `status` int NOT NULL COMMENT '当前物流状态：-1 未生效，0 未发货， 1 已发货，2 已签收',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品物流表';

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `sku_id` bigint NOT NULL COMMENT '商品ID',
  `actual_price` decimal(10,2) NOT NULL COMMENT '购买商品的实际价格',
  `num` int NOT NULL COMMENT '购买的商品数量',
  `amount` decimal(10,2) NOT NULL COMMENT '订单总价',
  `payment_type` int DEFAULT NULL COMMENT '支付方式：1微信、2支付宝、3银行卡\n',
  `status` int NOT NULL COMMENT '状态：0取消，1未付款、2已付款、3已发货、4已签收',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for t_question_bank
-- ----------------------------
DROP TABLE IF EXISTS `t_question_bank`;
CREATE TABLE `t_question_bank` (
  `id` bigint NOT NULL COMMENT '题目id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题',
  `image` varchar(255) NOT NULL COMMENT '题目图片路径',
  `option` varchar(255) NOT NULL COMMENT '逗号分割选项',
  `answer` int NOT NULL COMMENT '正确答案',
  `analysis` varchar(255) NOT NULL COMMENT '解析',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='答题游戏-题库';

-- ----------------------------
-- Table structure for t_shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `t_shipping_address`;
CREATE TABLE `t_shipping_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收货地址id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `contact_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系电话',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '具体地址',
  `priority` int NOT NULL COMMENT '优先级',
  PRIMARY KEY (`id`),
  KEY `link_user` (`user_id`),
  CONSTRAINT `link_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1722235239041110019 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收货地址表';

-- ----------------------------
-- Table structure for t_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_sku`;
CREATE TABLE `t_sku` (
  `id` bigint NOT NULL,
  `spu_id` bigint NOT NULL COMMENT '产品id',
  `spec` json DEFAULT NULL COMMENT '商品规格',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `stock` int NOT NULL COMMENT '商品库存',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  KEY `link_spu` (`spu_id`),
  CONSTRAINT `link_spu` FOREIGN KEY (`spu_id`) REFERENCES `t_spu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表-sku';

-- ----------------------------
-- Table structure for t_spu
-- ----------------------------
DROP TABLE IF EXISTS `t_spu`;
CREATE TABLE `t_spu` (
  `id` bigint NOT NULL,
  `category_id` int NOT NULL COMMENT '分类id',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `image` varchar(255) NOT NULL COMMENT '图片',
  `base_price` decimal(10,2) NOT NULL COMMENT '产品起售价',
  `sales` int NOT NULL COMMENT '产品销量',
  `production_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产地',
  `use_spec` int NOT NULL COMMENT '是否有规格，0为单一规格，1为多规格',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft_index` (`title`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品表-spu';

-- ----------------------------
-- Table structure for t_spu_spec
-- ----------------------------
DROP TABLE IF EXISTS `t_spu_spec`;
CREATE TABLE `t_spu_spec` (
  `id` bigint NOT NULL COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT '产品id',
  `name` varchar(255) NOT NULL COMMENT '规格名',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规格值',
  PRIMARY KEY (`id`),
  KEY `link_spu_id` (`spu_id`),
  CONSTRAINT `link_spu_id` FOREIGN KEY (`spu_id`) REFERENCES `t_spu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='产品规格表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT '用户ID',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信小程序openId',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像路径',
  `points` int NOT NULL COMMENT '游戏积分',
  `role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `deleted` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Table structure for t_user_badge
-- ----------------------------
DROP TABLE IF EXISTS `t_user_badge`;
CREATE TABLE `t_user_badge` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `badge_id` bigint NOT NULL COMMENT '徽章id',
  `redeem_time` datetime NOT NULL COMMENT '兑换时间',
  PRIMARY KEY (`id`),
  KEY `link_badge` (`badge_id`),
  KEY `badge_link_user` (`user_id`),
  CONSTRAINT `badge_link_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `link_badge` FOREIGN KEY (`badge_id`) REFERENCES `t_badge` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户徽章表 - 记录用户兑换的徽章';

-- ----------------------------
-- Table structure for t_user_points_history
-- ----------------------------
DROP TABLE IF EXISTS `t_user_points_history`;
CREATE TABLE `t_user_points_history` (
  `id` bigint NOT NULL COMMENT '积分记录id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `points` int NOT NULL COMMENT '获取/消耗的积分',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户积分获取/消耗记录表';

-- ----------------------------
-- Table structure for t_user_question_history
-- ----------------------------
DROP TABLE IF EXISTS `t_user_question_history`;
CREATE TABLE `t_user_question_history` (
  `id` bigint NOT NULL COMMENT '历史记录Id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `question_id` bigint NOT NULL COMMENT '题目Id',
  `is_correct` int NOT NULL COMMENT '是否答题正确',
  `create_time` datetime NOT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户答题记录表';

SET FOREIGN_KEY_CHECKS = 1;

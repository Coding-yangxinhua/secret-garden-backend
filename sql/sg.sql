use sg_prod;

# 用户表
CREATE TABLE `sg_user` (
       `ID` bigint NOT NULL COMMENT '主键ID',
       `OPEN_ID` char(7) NOT NULL COMMENT '用户游戏ID',
       `USER_NAME` varchar(15) DEFAULT NULL COMMENT '用户微信名',
       `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
       `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
       `DELETED` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
       PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

# 用户地址表
CREATE TABLE `sg_user_address` (
       `ID` bigint NOT NULL COMMENT '主键ID',
       `USER_ID` bigint NOT NULL COMMENT '用户游戏ID',
       `IP` varchar(15) NOT NULL COMMENT '用户登录IP',
       `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
       `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
       `DELETED` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
       PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';

# 套餐订阅
CREATE TABLE `sg_subscribe` (
        `ID` bigint NOT NULL COMMENT '主键ID',
        `NAME` varchar(28) DEFAULT NULL COMMENT '名称',
        `PRICE` decimal(10, 2) NOT NULL COMMENT '价格',
        `RATIO` int NOT NULL DEFAULT 1 COMMENT '倍率',
        `VALID_TIME` int NOT NULL DEFAULT 0 COMMENT '有效期 -1为无限',
        `VALID_USES` int NOT NULL DEFAULT 0 COMMENT '有效次数 -1为无限',
        `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
        `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
        `DELETED` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
        PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='套餐表';

# 用户套餐
CREATE TABLE `sg_user_subscribe` (
         `ID` bigint NOT NULL COMMENT '主键ID',
         `USER_ID` bigint NOT NULL COMMENT '用户ID',
         `SUBSCRIBE_ID` bigint NOT NULL COMMENT '套餐id',
         `VALID_UTIL` datetime DEFAULT NULL COMMENT '有效期 null为无限',
         `REMAINING_USES` int NOT NULL DEFAULT 0 COMMENT '剩余次数 -1为无限',
         `ENABLE` tinyint NOT NULL DEFAULT 0 COMMENT '是否开启， 0 - 关闭，1 - 开启',
         `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
         `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
         `DELETED` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
         PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户套餐表';

CREATE TABLE `sg_user_statement` (
         `ID` bigint NOT NULL COMMENT '主键ID',
         `USER_ID` bigint NOT NULL COMMENT '用户ID',
         `SUBSCRIBE_ID` bigint NOT NULL COMMENT '套餐id',
         `PRICE` decimal(10, 1) NOT NULL COMMENT '价格',
         `NUM` int NOT NULL COMMENT '购买数量',
         `AMOUNT` decimal(10, 1) NOT NULL COMMENT '花费金额',
         `GMT_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
         `GMT_MODIFIED` datetime DEFAULT NULL COMMENT '修改时间',
         `DELETED` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
         PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户流水表';
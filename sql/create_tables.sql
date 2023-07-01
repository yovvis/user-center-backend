CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `username` varchar(256) DEFAULT NULL COMMENT '用户姓名',
                        `userAccount` varchar(256) NOT NULL COMMENT '账号',
                        `avatarUrl` varchar(1024) DEFAULT NULL COMMENT '头像',
                        `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
                        `userPassword` varchar(256) DEFAULT NULL COMMENT '用户密码',
                        `phone` varchar(256) DEFAULT NULL COMMENT '手机号',
                        `email` varchar(256) DEFAULT NULL COMMENT '邮箱',
                        `userStatus` int(11) NOT NULL DEFAULT '0' COMMENT '是否有效（0表示）',
                        `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `operateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0：未删除 1：删除',
                        `userRole` int(11) NOT NULL DEFAULT '0' COMMENT '用户角色 0：普通用户 1：管理员',
                        `planetCode` varchar(256) DEFAULT NULL COMMENT '编号',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'yovvis','yovvis','https://yovvis.oss-cn-shanghai.aliyuncs.com/logo/ig5.jpg',1,'cd1f07a43b18d9bcf2d8a76400f25682','138********','meet***@163.com',0,'2023-05-22 22:10:58','2023-05-24 23:20:48',0,0,NULL),(2,'yovv','yovv','https://yovvis.oss-cn-shanghai.aliyuncs.com/logo/ig1.jpg',0,'f6feaa40d4a6623989adf49768266565','150********','meet***@163.com',0,'2023-05-24 19:17:05','2023-05-24 23:20:15',0,0,NULL),(3,'admin','admin','https://yovvis.oss-cn-shanghai.aliyuncs.com/logo/ig4.jpg',0,'cd1f07a43b18d9bcf2d8a76400f25682','1381437****','meet***@163.com',0,'2023-05-24 23:18:29','2023-05-25 15:22:53',0,1,'22369'),(10,'yovvis','yovv','https://crulu.oss-cn-shanghai.aliyuncs.com/2023/01/31/adf5a0ba266f4de794962c738ea7ca66.jpg',0,'123123','1381437xxxx','meetxxx@163.com',0,'2023-05-26 11:29:13','2023-05-26 11:29:13',0,0,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

use stardew_valley_helper;
CREATE TABLE `tb_user` (
   `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
   `username` VARCHAR(50) NOT NULL COMMENT '用户名',
   `password` VARCHAR(255) NOT NULL COMMENT '密码',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
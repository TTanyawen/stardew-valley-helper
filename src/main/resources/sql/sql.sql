use stardew_valley_helper;
CREATE TABLE `tb_user` (
   `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
   `username` VARCHAR(50) NOT NULL COMMENT '用户名',
   `password` VARCHAR(255) NOT NULL COMMENT '密码',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 消息组表
use stardew_valley_helper;
CREATE TABLE tb_message_group (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  title VARCHAR(100) -- 默认为第一条message的前15个字，用户也可以设置
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息组表';

-- 消息详情表
use stardew_valley_helper;
CREATE TABLE tb_message_detail (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   message_group_id BIGINT NOT NULL,
   role VARCHAR(20) NOT NULL,   -- user(用户)/model(模型)
   content TEXT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息详情表';
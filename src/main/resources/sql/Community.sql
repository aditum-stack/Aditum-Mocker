-- auto Generated on 2018-11-21 12:52:02 
-- DROP TABLE IF EXISTS `community`; 
CREATE TABLE community(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `community_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '����ID Unique',
    `community_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '��������',
    `community_city` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '��������',
    `community_address` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '������ַ',
    `device_count` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '�����豸����',
    `device_online_count` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '���������豸��',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'ɾ�����',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`community_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'community';

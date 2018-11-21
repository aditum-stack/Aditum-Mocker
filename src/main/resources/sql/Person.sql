-- auto Generated on 2018-11-21 12:52:20 
-- DROP TABLE IF EXISTS `person`; 
CREATE TABLE person(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `personnel_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�û�ID Unique',
    `personnel_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�û�����',
    `community_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '����ID',
    `personnel_address` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�û�����סַ',
    `personnel_phone` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�û��ֻ���',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'ɾ�����',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`personnel_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'person';

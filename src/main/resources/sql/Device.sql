-- auto Generated on 2018-11-21 12:52:13 
-- DROP TABLE IF EXISTS `device`; 
CREATE TABLE device(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `imei` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸ID Unique',
    `alias` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸����',
    `community_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '����ID',
    `device_status` INTEGER(12) UNSIGNED NOT NULL DEFAULT 0 COMMENT '�豸״̬ 0δ���� 1�Ѽ���(��δʹ��) 2����(��ʹ��״̬) 3����',
    `activate_time` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸����ʱ��',
    `last_online_time` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸�������ʱ��',
    `last_offline_time` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸�������ʱ��',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'createTime',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'ɾ�����',
    PRIMARY KEY (`id`),
    UNIQUE KEY(`imei`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'device';

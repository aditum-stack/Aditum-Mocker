-- auto Generated on 2018-11-21 12:52:26 
-- DROP TABLE IF EXISTS `record`; 
CREATE TABLE record(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `imei` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�豸ID',
    `personnel_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '�ÿ�ID',
    `visite_time` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '����ʱ��',
    `visite_status` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '����״̬ 0�������� 1�뿪���� 2ʶ��ʧ�� 3��Ӧ��ʱ',
    `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'ɾ�����',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'record';

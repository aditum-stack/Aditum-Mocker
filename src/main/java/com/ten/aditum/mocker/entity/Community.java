package com.ten.aditum.mocker.entity;

import lombok.Data;

/**
 * 社区信息表
 */
@Data
public class Community {
    /**
     * 主键ID Auto
     */
    private Integer id;
    /**
     * 社区ID Unique
     */
    private String communityId;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 社区城市
     */
    private String communityCity;
    /**
     * 社区地址
     */
    private String communityAddress;
    /**
     * 社区设备总数
     */
    private Integer deviceCount;
    /**
     * 社区在线设备数
     */
    private Integer deviceOnlineCount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 删除标记
     */
    private Integer isDeleted;
}

package com.ten.aditum.mocker.entity;

/**
 * 社区信息表
 */
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

    public Community() {
    }

    @Override
    public String toString() {
        return "Community{" +
                "id=" + id +
                ", communityId=" + communityId +
                ", communityName='" + communityName + '\'' +
                ", communityCity='" + communityCity + '\'' +
                ", communityAddress='" + communityAddress + '\'' +
                ", deviceCount=" + deviceCount +
                ", deviceOnlineCount=" + deviceOnlineCount +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityCity() {
        return communityCity;
    }

    public void setCommunityCity(String communityCity) {
        this.communityCity = communityCity;
    }

    public String getCommunityAddress() {
        return communityAddress;
    }

    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public Integer getDeviceOnlineCount() {
        return deviceOnlineCount;
    }

    public void setDeviceOnlineCount(Integer deviceOnlineCount) {
        this.deviceOnlineCount = deviceOnlineCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}

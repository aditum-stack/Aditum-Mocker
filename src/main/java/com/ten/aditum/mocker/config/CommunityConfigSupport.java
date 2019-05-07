package com.ten.aditum.mocker.config;


import com.alibaba.fastjson.JSON;
import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.http.BackRemoteApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 从数据库获取初始数据
 */
@Slf4j
public class CommunityConfigSupport {

    /**
     * http远程获取communityMeta数据
     */
    public static void update() {
        // community
        List<Community> communityList = BackRemoteApi.getForCommunity();

        log.info("Community init {}", communityList);

        // 防止 "java.util.LinkedHashMap cannot be cast" 转换错误
        for (Object obj : communityList) {
            Community community = JSON.parseObject(JSON.toJSONString(obj), Community.class);

            String communityId = community.getCommunityId();

            // device
            List<Device> deviceList = BackRemoteApi.getForDevice(communityId);

            log.info("Device init {}", communityList);

            // person
            List<Person> personList = BackRemoteApi.getForPerson(communityId);

            log.info("Person init {}", communityList);

            // 更新容器
            CommunityConfigHolder.newCommunityMeta(community, deviceList, personList);
        }

        log.info("CommunityConfig update finished: {}", CommunityConfigHolder.getCommunityMetaString());
    }

}

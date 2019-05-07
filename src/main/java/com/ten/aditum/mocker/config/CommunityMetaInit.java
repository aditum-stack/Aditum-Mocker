package com.ten.aditum.mocker.config;


import com.alibaba.fastjson.JSON;
import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.http.BackRemoteApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 从数据库获取初始数据
 */
@Slf4j
@Component
public class CommunityMetaInit {

    private List<Community> communityList;
    private List<Person> personList;
    private List<Device> deviceList;

    /**
     * http远程获取communityMeta数据
     */
    public void init() {
        communityList = BackRemoteApi.getForCommunity();

        System.out.println(communityList);

        // 防止 "java.util.LinkedHashMap cannot be cast" 转换错误
        for (Object obj : communityList) {
            Community community = JSON.parseObject(JSON.toJSONString(obj), Community.class);

            String communityId = community.getCommunityId();

            personList = BackRemoteApi.getForPerson(communityId);
            deviceList = BackRemoteApi.getForDevice(communityId);

            // 生成新的communityMeta对象并加入到容器中
            CommunityMetaHolder.newCommunityMeta(community, deviceList, personList);
        }

        log.info("CommunityMeta init finished: {}", CommunityMetaHolder.getCommunityMetaString());
    }

}

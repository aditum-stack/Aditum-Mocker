package com.ten.aditum.mocker.config;


import com.alibaba.fastjson.JSON;
import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 从数据库获取初始数据
 */
@Slf4j
@Component
public class CommunityMetaInit {

    private static final String COMMUNITY_API = "http://localhost:9006/community";
    private static final String PERSON_API = "http://localhost:9006/person";
    private static final String DEVICE_API = "http://localhost:9006/device";

    private List<Community> communityList;
    private List<Person> personList;
    private List<Device> deviceList;

    /**
     * http远程获取communityMeta数据
     */
    public void init() {
        communityList = getForCommunity();

        System.out.println(communityList);

        // 防止 "java.util.LinkedHashMap cannot be cast" 转换错误
        for (Object obj : communityList) {
            Community community = JSON.parseObject(JSON.toJSONString(obj), Community.class);

            String communityId = community.getCommunityId();

            personList = getForPerson(communityId);
            deviceList = getForDevice(communityId);

            // 生成新的communityMeta对象并加入到容器中
            CommunityMetaHolder.newCommunityMeta(community, deviceList, personList);
        }

        log.info("CommunityMeta init finished: {}", CommunityMetaHolder.getCommunityMetaString());
    }

    /**
     * http远程获取community数据
     */
    public List<Community> getForCommunity() {
        ResultModel communityResult = new RestTemplate().getForEntity(COMMUNITY_API, ResultModel.class).getBody();
        if (communityResult == null) {
            throw new BackRemoteException("Get for community list error!");
        }

        if (communityResult.getCode() == 0) {
            List result = (List) communityResult.getData();
            return JSON.parseArray(JSON.toJSONString(result), Community.class);
        } else {
            throw new BackRemoteException("Get for community list error!");
        }
    }

    /**
     * http远程获取person数据
     */
    private List<Person> getForPerson(String communityId) {
        Person person = new Person();
        person.setCommunityId(communityId);

        ResultModel personResult = new RestTemplate().getForEntity(PERSON_API, ResultModel.class, person).getBody();
        if (personResult == null) {
            throw new BackRemoteException("Post for person list error!");
        }

        if (personResult.getCode() == 0) {
            List result = (List) personResult.getData();
            return JSON.parseArray(JSON.toJSONString(result), Person.class);
        } else {
            throw new BackRemoteException("Post for person list error!");
        }
    }

    /**
     * http远程获取device数据
     */
    private List<Device> getForDevice(String communityId) {
        Device device = new Device();
        device.setCommunityId(communityId);

        ResultModel deviceResult = new RestTemplate().getForEntity(DEVICE_API, ResultModel.class, device).getBody();
        if (deviceResult == null) {
            throw new BackRemoteException("Post for device list error!");
        }

        if (deviceResult.getCode() == 0) {
            List result = (List) deviceResult.getData();
            return JSON.parseArray(JSON.toJSONString(result), Device.class);
        } else {
            throw new BackRemoteException("Post for device list error!");
        }
    }

}

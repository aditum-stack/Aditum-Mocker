package com.ten.aditum.mocker.config;


import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.model.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
@Slf4j
public class CommunityMetaInit {

    private static final String COMMUNITY_API = "http://localhost:8080:community";
    private static final String PERSON_API = "http://localhost:8080/person";
    private static final String DEVICE_API = "http://localhost:8080/device";

    private List<Community> communityList;
    private List<Person> personList;
    private List<Device> deviceList;

    /**
     * http远程获取communityMeta数据
     */
    public void init() {
        communityList = getForCommunity();

        communityList.forEach(community -> {
            String communityId = community.getCommunityId();

            personList = postForPerson(communityId);
            deviceList = postForDevice(communityId);

            // 生成新的communityMeta对象并加入到容器中
            CommunityMetaHolder.newCommunityMeta(community, deviceList, personList);
        });

        log.info("CommunityMeta init finished: {}", CommunityMetaHolder.getCommunityMetaString());
    }

    /**
     * http远程获取community数据
     */
    private List<Community> getForCommunity() {
        ResultModel communityResult = new RestTemplate().getForEntity(COMMUNITY_API, ResultModel.class).getBody();
        if (communityResult.getCode() == 0) {
            return (List<Community>) communityResult.getData();
        } else {
            throw new BackRemoteException("Get for community list error!");
        }
    }

    /**
     * http远程获取person数据
     */
    private List<Person> postForPerson(String communityId) {
        Person person = new Person();
        person.setCommunityId(communityId);

        ResultModel personResult = new RestTemplate().postForEntity(PERSON_API, person, ResultModel.class).getBody();
        if (personResult.getCode() == 0) {
            return (List<Person>) personResult.getData();
        } else {
            throw new BackRemoteException("Post for person list error!");
        }
    }

    /**
     * http远程获取device数据
     */
    private List<Device> postForDevice(String communityId) {
        Device device = new Device();
        device.setCommunityId(communityId);

        ResultModel deviceResult = new RestTemplate().postForEntity(DEVICE_API, device, ResultModel.class).getBody();
        if (deviceResult.getCode() == 0) {
            return (List<Device>) deviceResult.getData();
        } else {
            throw new BackRemoteException("Post for device list error!");
        }
    }

}

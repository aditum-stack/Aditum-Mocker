package com.ten.aditum.mocker.http;

import com.alibaba.fastjson.JSON;
import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.model.ResultModel;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BackRemoteApi {

    private static final String COMMUNITY_API = "http://localhost:9006/community";
    private static final String PERSON_API = "http://localhost:9006/person";
    private static final String DEVICE_API = "http://localhost:9006/device";

    // ------------------------------------------------------------------------ GET

    /**
     * http远程获取community数据
     */
    public static List<Community> getForCommunity() {
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
     * http远程获取device数据
     */
    public static List<Device> getForDevice(String communityId) {
        Device device = new Device();
        device.setCommunityId(communityId);

        ResultModel deviceResult = new RestTemplate().getForEntity(DEVICE_API, ResultModel.class, device).getBody();
        if (deviceResult == null) {
            throw new BackRemoteException("Get for device list error!");
        }

        if (deviceResult.getCode() == 0) {
            List result = (List) deviceResult.getData();
            return JSON.parseArray(JSON.toJSONString(result), Device.class);
        } else {
            throw new BackRemoteException("Get for device list error!");
        }
    }

    /**
     * http远程获取person数据
     */
    public static List<Person> getForPerson(String communityId) {
        Person person = new Person();
        person.setCommunityId(communityId);

        ResultModel personResult = new RestTemplate().getForEntity(PERSON_API, ResultModel.class, person).getBody();
        if (personResult == null) {
            throw new BackRemoteException("Get for person list error!");
        }

        if (personResult.getCode() == 0) {
            List result = (List) personResult.getData();
            return JSON.parseArray(JSON.toJSONString(result), Person.class);
        } else {
            throw new BackRemoteException("Get for person list error!");
        }
    }

    // ------------------------------------------------------------------------ POST

    /**
     * http远程提交person数据
     */
    public static boolean postForPerson(Person person) {
        ResultModel personResult = new RestTemplate().postForObject(PERSON_API, person, ResultModel.class);
        if (personResult == null) {
            throw new BackRemoteException("Post for person error!");
        }

        if (personResult.getCode() == 0) {
            return true;
        } else {
            throw new BackRemoteException("Post for person error!");
        }
    }

}

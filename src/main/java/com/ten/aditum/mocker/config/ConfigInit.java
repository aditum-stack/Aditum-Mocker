package com.ten.aditum.mocker.config;


import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.model.ResultModel;
import com.ten.aditum.mocker.thread.DeviceThread;
import com.ten.aditum.mocker.thread.DeviceThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ConfigInit {
//
//    @Value("${spring.datasource.name}")
//    private String name;
//
//    @Value("${datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @RequestMapping(value = "/hi")
//    public String show() {
//        return this.url;
//    }

    public void run() {
        rest();
        init();
    }


    // FIXME 修改为从Config获取
    private String getPersonList = "http://localhost:8080/person";
    private String getDeviceList = "http://localhost:8080/device";

    private DeviceThreadPool deviceThreadPool = DeviceThreadPool.INSTANCE;

    private List<Person> personList;
    private List<Device> deviceList;
    private List<DeviceThread> deviceThreads;

    // TODO 调用Back-REST接口获取设备+用户集合信息
    public void rest() {
        ResultModel personResult = new RestTemplate().getForEntity(getPersonList, ResultModel.class).getBody();
        if (personResult.getCode() == 0) {
            personList = (List<Person>) personResult.getData();
        }

        ResultModel deviceResult = new RestTemplate().getForEntity(getDeviceList, ResultModel.class).getBody();
        if (deviceResult.getCode() == 0) {
            deviceList = (List<Device>) deviceResult.getData();
        }
    }


    public void init() {
        deviceThreadPool.setPersonList(personList);

        for (Device device : deviceList) {
            DeviceThread thread = new DeviceThread();
            thread.setDevice(device);
            deviceThreads.add(thread);
        }

        deviceThreadPool.setDeviceThreads(deviceThreads);
    }

}

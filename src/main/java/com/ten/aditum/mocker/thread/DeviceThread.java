package com.ten.aditum.mocker.thread;

import com.ten.aditum.mocker.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 设备访问线程
 */
@Service
public class DeviceThread extends Thread{

    private Logger logger = LoggerFactory.getLogger(DeviceThread.class);
    private Device device;

    @Async
    public void visite() {

    }
}

package com.ten.aditum.mocker.schedule;


import com.ten.aditum.mocker.config.CommunityConfigSupport;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.http.BackRemoteApi;
import com.ten.aditum.mocker.util.DeviceMocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class DeviceProducer {

    /*
     * 每1天产生一台模拟设备
     */
//    @Scheduled(cron = "0 0 0 1/1 * ? ")

    /**
     * 每1小时产生一台模拟设备
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void reWrite() {
        Device device = DeviceMocker.newDevice();

        log.info("产生新设备 : {}", device);

        BackRemoteApi.postForDevice(device);

        CommunityConfigSupport.update();
    }

}

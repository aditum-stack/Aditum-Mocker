package com.ten.aditum.mocker.schedule;


import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.http.BackRemoteApi;
import com.ten.aditum.mocker.util.RandomValue;
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

    /**
     * 每1天产生一台模拟设备
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void reWrite() {
        Person person = RandomValue.newPerson();

        log.info("产生新用户 : {}", person);

        BackRemoteApi.postForPerson(person);
    }

}

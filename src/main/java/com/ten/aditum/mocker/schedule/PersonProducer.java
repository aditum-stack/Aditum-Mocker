package com.ten.aditum.mocker.schedule;


import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.http.BackRemoteApi;
import com.ten.aditum.mocker.util.PersonMocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class PersonProducer {

    /**
     * 每1小时产生一位模拟用户
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void reWrite() {
        Person person = PersonMocker.newPerson();

        log.info("产生新用户 : {}", person);

        BackRemoteApi.postForPerson(person);
    }

}

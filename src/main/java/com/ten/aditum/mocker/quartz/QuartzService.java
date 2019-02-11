package com.ten.aditum.mocker.quartz;


import com.ten.aditum.mocker.execute.CommunityTablesHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class QuartzService {

    private static AtomicInteger atomicInteger;

    static {
        atomicInteger = new AtomicInteger(0);
    }

    private CommunityTablesHolder instance = CommunityTablesHolder.INSTANCE;

    /**
     * 每隔三分钟，进行模拟访问
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void reWrite() {
        log.info("进行模拟调度，当前次数：" + atomicInteger.addAndGet(1));
        instance.run();
    }
}

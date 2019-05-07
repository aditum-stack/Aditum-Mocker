package com.ten.aditum.mocker.schedule;

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
public class CommunityProducer {

    private static AtomicInteger atomicInteger;

    private static CommunityTablesHolder service;

    static {
        atomicInteger = new AtomicInteger(0);
        service = CommunityTablesHolder.INSTANCE;
    }

    /**
     * 每隔1分钟，进行模拟访问
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void reWrite() {
        log.info("进行模拟调度，当前次数：" + atomicInteger.addAndGet(1));

        for (int i = 0; i < 1; i++) {
            service.run(); // 执行异步任务
        }
    }
}

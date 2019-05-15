package com.ten.aditum.mocker.schedule;

import com.ten.aditum.mocker.execute.CommunityAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class RecordProducer {

    private static AtomicInteger atomicInteger;

    static {
        atomicInteger = new AtomicInteger(0);
    }

    /**
     * 拒绝访问时间 0-6
     */
    private static final String ILLEGAL_START = "00:00:00";
    private static final String ILLEGAL_STOP = "06:00:00";

    /**
     * 热门访问时间 7-9 早高峰
     */
    private static final String HOT_START_1 = "07:00:00";
    private static final String HOT_STOP_1 = "09:00:00";

    /**
     * 热门访问时间 5-7 晚高峰
     */
    private static final String HOT_START_2 = "17:00:00";
    private static final String HOT_STOP_2 = "19:00:00";
    /**
     * 热门访问时间 5-7 午休
     */
    private static final String HOT_START_3 = "11:00:00";
    private static final String HOT_STOP_3 = "14:00:00";

    /*
     * 每隔10分钟，进行模拟访问
     */
//    @Scheduled(cron = "0 0/10 * * * ? ")

    /**
     * 每隔1分钟，进行模拟访问
     */
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void reWrite() {
        log.info("进行模拟访问，当前次数：" + atomicInteger.addAndGet(1));

        CommunityAccess.run();

        // 根据时间修改失败概率

        // 早高峰
        boolean hot1 = between(HOT_START_1, HOT_STOP_1);
        if (hot1) {
            CommunityAccess.RANDOM_FAILURE_PROBABILITY = 0.1;
        }

        // 晚高峰
        boolean hot2 = between(HOT_START_2, HOT_STOP_2);
        if (hot2) {
            CommunityAccess.RANDOM_FAILURE_PROBABILITY = 0.3;
        }

        // 午休
        boolean hot3 = between(HOT_START_3, HOT_STOP_3);
        if (hot3) {
            CommunityAccess.RANDOM_FAILURE_PROBABILITY = 0.5;
        }

        log.info("当前时间 {}, 失败概率 {}", currentHour(), CommunityAccess.RANDOM_FAILURE_PROBABILITY);
    }

    private boolean between(String start, String stop) {
        String current = currentHour();
        return current.compareTo(start) > 0 && stop.compareTo(current) > 0;
    }

    private String currentHour() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

}

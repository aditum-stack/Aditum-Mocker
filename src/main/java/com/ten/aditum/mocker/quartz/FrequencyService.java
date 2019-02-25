package com.ten.aditum.mocker.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * TODO 修改为“出访问”“进访问”“随机访问”三种模式，对上学和周末进行区分，
 * 建立“周末调度器”“上学调度器”，进行三种访问模式的调用，对不同的门禁设施进行不同的调度
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class FrequencyService {

    /**
     * 每隔1分钟，修改访问间隔
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void reWrite() {
        // 获取当前时间
        String current;

        TimeSlot slot = TimeSlot.whichSlot(current);


    }

    private static final long ONE_HOUR = 1000 * 60 * 60;

    /**
     * 时段枚举
     */
    enum TimeSlot {
        /**
         * 描述，时间段，访问间隔
         */
        MORNING_FREE("早上_清闲时刻", "6:00-7:00", ONE_HOUR),
        MORNING_BASY("早上_忙碌时刻_上学", "7:00-8:00", 0.5 * ONE_HOUR),
        MORNING("早上_清闲时刻", "6:00-7:00", ONE_HOUR),

        NOON_BASY("早上_清闲时刻", "6:00-7:00", ONE_HOUR);

        private String desc;
        private String time;
        protected long frequency;

        TimeSlot(String desc, String time, long frequency) {
            this.desc = desc;
            this.time = time;
            this.frequency = frequency;
        }

        /**
         * 根据当前时间返回时段枚举
         */
        static TimeSlot whichSlot(String current) {
            return MORNING;
        }
    }
}

package com.ten.aditum.mocker.execute;

import com.ten.aditum.mocker.config.CommunityConfig;
import com.ten.aditum.mocker.config.CommunityConfigHolder;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.entity.Record;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.http.BackRemoteApi;
import com.ten.aditum.mocker.strategy.AccessStrategy;
import com.ten.aditum.mocker.strategy.AccessStrategyType;
import com.ten.aditum.mocker.strategy.AccessType;
import com.ten.aditum.mocker.strategy.GeneralAccessStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟访问器
 */
@Slf4j
public class CommunityAccess {
    /**
     * 默认采用General访问策略
     */
    private static AccessStrategy accessStrategy = new GeneralAccessStrategy();

    /**
     * 用户访问时间间隔 {@literal <PersonnelId, LastAccessTime>}
     */
    private static ConcurrentHashMap<String, Long> personAccessInterval = new ConcurrentHashMap<>();

    /**
     * 随机失败概率 不进行任何模拟访问操作
     */
    public static volatile double RANDOM_FAILURE_PROBABILITY = 0.8;

    /**
     * 用户访问时间最小间隔 8h = 1000ms * 60s * 60min * 8h
     */
    private static final long TIME_OUT_MS = 1000 * 60 * 60 * 8;

    /**
     * 快速测试访问间隔 1min = 1000ms * 60s
     */
    private static final long TEST_TIME_OUT_MS = 1000 * 60;

    /**
     * 拒绝访问时间
     */
    private static final String ILLEGAL_START = "00:00:00";
    private static final String ILLEGAL_STOP = "06:00:00";

    /**
     * 每个社区都执行一遍全设备随机访问
     */
    public static void run() {
        List<CommunityConfig> communityConfigs = CommunityConfigHolder.getAllCommunityMeta();

        if (communityConfigs.size() == 0) {
            throw new BackRemoteException("CommunityConfig is null.");
        }

        communityConfigs.forEach(communityConfig -> startAccess(communityConfig, accessStrategy));
    }

    /**
     * 启动一次所有设备的访问
     */
    private static void startAccess(CommunityConfig communityConfig, AccessStrategy accessStrategy) {
        int personSize = communityConfig.getPersonList().size();

        communityConfig.getDeviceList().forEach(device -> {
            int personRandom = (int) (Math.random() * personSize);
            Person choosePerson = communityConfig.getPersonList().get(personRandom);
            accessDevice(device, choosePerson, accessStrategy);
        });
    }

    /**
     * 访问设备并打印结果
     */
    private static void accessDevice(Device theDevice, Person thePerson, AccessStrategy accessStrategy) {
        log.info("开始启动模拟访问逻辑...");

        // 随机失败
        double flag = Math.random();
        if (flag < CommunityAccess.RANDOM_FAILURE_PROBABILITY) {
            log.info("模拟访问随机失败...imei=" + theDevice.getImei());
            return;
        }

        // 获取用户上次访问时间
        String personnelId = thePerson.getPersonnelId();
        long lastaccesstime = CommunityAccess.personAccessInterval.getOrDefault(personnelId, 0L);
        // 用户第一次访问
        if (lastaccesstime == 0L) {
            CommunityAccess.personAccessInterval.put(personnelId, System.currentTimeMillis());
        }
        // 判断是否满足最小访问时间间隔
        else {
            long currentTime = System.currentTimeMillis();
            long subtract = currentTime - lastaccesstime;

            // 随机延长访问间隔
            long randomTime = (long) (Math.random() * TIME_OUT_MS);

            // 访问间隔过小
            if (subtract < CommunityAccess.TIME_OUT_MS + randomTime) {
                log.info("模拟访问访问间隔过小...imei={}", theDevice.getImei());
                return;
            }
        }

        // 是否拒绝访问时间 (二十四小时制)
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String current = format.format(new Date());

        // 当前时间大于START且小于STOP，拒绝访问
        if (current.compareTo(ILLEGAL_START) > 0 && ILLEGAL_STOP.compareTo(current) > 0) {
            log.warn("当前为拒绝访问时间 {}", current);
            return;
        }

        /* 全部通过，执行访问 */

        // 获取访问的进出入顺序
        AccessType type = accessStrategy.select(personnelId);

        // 生成访问对象
        Record record = new Record();
        record.setImei(theDevice.getImei());
        record.setPersonnelId(personnelId);
        record.setVisiteStatus(type.num());

        // 生成日志
        log.info(record.toString());

        // 发送http到后台服务
        BackRemoteApi.postForRecord(record);
    }

}

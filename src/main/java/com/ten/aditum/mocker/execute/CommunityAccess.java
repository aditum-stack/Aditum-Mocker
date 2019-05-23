package com.ten.aditum.mocker.execute;

import com.ten.aditum.mocker.config.CommunityConfig;
import com.ten.aditum.mocker.config.CommunityConfigHolder;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import com.ten.aditum.mocker.entity.Record;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.http.BackRemoteApi;
import com.ten.aditum.mocker.strategy.AccessStrategy;
import com.ten.aditum.mocker.strategy.AccessType;
import com.ten.aditum.mocker.strategy.RandomAccessStrategy;
import com.ten.aditum.mocker.util.TimeGenerator;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟访问器
 */
@Slf4j
public class CommunityAccess {
    /**
     * 默认采用General访问策略
     */
    private static AccessStrategy accessStrategy = new RandomAccessStrategy();

    /**
     * 用户访问时间间隔记录 {@literal <PersonnelId, LastAccessTime>}
     */
    private static ConcurrentHashMap<String, Long> personAccessInterval = new ConcurrentHashMap<>();

    public static volatile double BASE_RANDOM_FAIL = 0.5;

    /**
     * 用户访问随机失败概率，对每个用户都不相同，该值在每次 #REJECT_FAILURE_PROBABILITY 后触发
     */
    private static ConcurrentHashMap<String, Double> personAccessRandomFail = new ConcurrentHashMap<>();

    /**
     * 用户访问随机最小间隔，对每个用户都不相同，该值初始化后固定
     */
    private static ConcurrentHashMap<String, Long> personTimeOutRandom = new ConcurrentHashMap<>();

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
     * 拒绝访问概率
     */
    private static final double REJECT_FAILURE_PROBABILITY = 0.999;

    /**
     * 每个社区都执行一遍全设备随机访问
     */
    public static void run() {
        // 获取最新的数据容器
        List<CommunityConfig> communityConfigs = CommunityConfigHolder.getAllCommunityMeta();

        if (communityConfigs.size() == 0) {
            throw new BackRemoteException("CommunityConfig is null.");
        }

        // 对新数据进行初始化
        communityConfigs.forEach(communityConfig -> {
            List<Person> personList = communityConfig.getPersonList();
            // 遍历每位用户，若该用户(新用户或初始化)没有对应的数值，赋值，并保持到应用结束
            personList.forEach(person -> {
                String personnelId = person.getPersonnelId();

                // 赋值 随机失败概率=BASE+random，BASE可以被外部修改
                personAccessRandomFail.putIfAbsent(personnelId, Math.random() / 2);

                // 赋值 随机最小访问间隔
                personTimeOutRandom.putIfAbsent(personnelId, TIME_OUT_MS + (long) (Math.random() * TIME_OUT_MS));
            });
        });

        communityConfigs.forEach(communityConfig -> startAccess(communityConfig, accessStrategy));
    }

    /**
     * 启动一次所有设备的访问
     */
    private static void startAccess(CommunityConfig communityConfig, AccessStrategy accessStrategy) {
        int personSize = communityConfig.getPersonList().size();

        communityConfig.getDeviceList().forEach(device -> {
            // 随机选择一位用户
            int personRandom = (int) (Math.random() * personSize);
            Person choosePerson = communityConfig.getPersonList().get(personRandom);
            // 对该设备该用户进行模拟访问
            accessDevice(device, choosePerson, accessStrategy);
        });
    }

    /**
     * 访问设备并打印结果
     */
    private static void accessDevice(Device theDevice, Person thePerson, AccessStrategy accessStrategy) {
        log.debug("开始启动模拟访问逻辑...");

        String alias = theDevice.getAlias();
        String personnelId = thePerson.getPersonnelId();
        String personnelName = thePerson.getPersonnelName();

        /* step 1 */

        // 随机失败
        double flag = Math.random();
        double fail = personAccessRandomFail.get(personnelId);
        // 随机失败概率=BASE+random，BASE可以被外部修改
        if (flag < BASE_RANDOM_FAIL + fail) {
            log.info("模拟访问随机失败 : 设备={} 姓名={} 当前失败概率={} ",
                    alias, personnelName, BASE_RANDOM_FAIL + fail);
            return;
        }

        /* step 2 */

        // 获取用户上次访问时间
        long lastaccesstime = personAccessInterval.getOrDefault(personnelId, 0L);
        // 用户第一次访问
        if (lastaccesstime == 0L) {
            personAccessInterval.put(personnelId, System.currentTimeMillis());
        }
        // 判断是否满足最小访问时间间隔
        else {
            long currentTime = System.currentTimeMillis();
            long subtract = currentTime - lastaccesstime;
            Long timeOut = personTimeOutRandom.get(personnelId);
            // 访问间隔过小
            if (subtract < timeOut) {
                log.info("模拟访问时间过短 : 设备={} 姓名={} 倒计时={} ",
                        alias, personnelName, TimeGenerator.getTimeFromSec(timeOut / 1000));
                return;
            }
        }

        /* step 3 */

        // 是否拒绝访问时间 (二十四小时制)
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String current = format.format(new Date());
        // 当前时间大于START且小于STOP，拒绝访问
        if (current.compareTo(ILLEGAL_START) > 0 && ILLEGAL_STOP.compareTo(current) > 0) {
            // 拒绝访问：极大概率随机失败
            if (Math.random() > REJECT_FAILURE_PROBABILITY) {
                log.info("模拟访问拒绝 : 设备={} 姓名={} 概率={} ",
                        alias, personnelName, fail);
                return;
            }
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
        log.warn("模拟访问成功!!!设备={} 姓名={} 访问间隔={} 失败概率={} IMEI={} ID={}",
                alias, personnelName, TimeGenerator.getTimeFromSec(personTimeOutRandom.get(personnelId) / 1000),
                personAccessRandomFail.get(personnelId), theDevice.getImei(), personnelId);

        // 发送http到后台服务
        BackRemoteApi.postForRecord(record);
    }

    /**
     * 增加所有用户的失败概率
     */
    public static void increaseFail(double increase) {
        for (Map.Entry<String, Double> entrySet : personAccessRandomFail.entrySet()) {
            Double value = entrySet.getValue();
            value += increase;
            entrySet.setValue(value);
        }
    }

    /**
     * 减少所有用户的失败概率
     */
    public static void decreaseFail(double decrease) {
        for (Map.Entry<String, Double> entrySet : personAccessRandomFail.entrySet()) {
            Double value = entrySet.getValue();
            value -= decrease;
            entrySet.setValue(value);
        }
    }

}

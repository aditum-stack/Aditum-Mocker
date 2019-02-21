package com.ten.aditum.mocker.execute;

import com.alibaba.fastjson.JSON;
import com.ten.aditum.mocker.config.CommunityMeta;
import com.ten.aditum.mocker.entity.Record;
import com.ten.aditum.mocker.excep.BackRemoteException;
import com.ten.aditum.mocker.model.ResultModel;
import com.ten.aditum.mocker.strategy.*;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 社区访问数据执行者与数据信息表
 */
@Slf4j
public class CommunityTable {
    /**
     * 社区元数据对象
     */
    private CommunityMeta communityMeta;

    public void setCommunityMeta(CommunityMeta communityMeta) {
        this.communityMeta = communityMeta;
    }

    /**
     * 访问策略
     */
    private AccessStrategyType accessStrategyType;
    private AccessStrategy accessStrategy;

    public void setAccessStrategyType(AccessStrategyType accessStrategyType) {
        this.accessStrategyType = accessStrategyType;
    }

    /**
     * 初始化
     */
    public void init() {
        // 初始化策略实现
        if (accessStrategyType == null) {
            throw new RuntimeException("AccessStrategyType must be init");
        } else {
            if (accessStrategyType == AccessStrategyType.GENERAL) {
                accessStrategy = new GeneralAccessStrategy();
            } else if (accessStrategyType == AccessStrategyType.RANDOM) {
                accessStrategy = new RandomAccessStrategy();
            } else {
                accessStrategy = new GeneralAccessStrategy();
            }
        }
        // 初始化访问执行方法
        if (communityMeta == null) {
            throw new RuntimeException("CommunityMeta must be init!");
        } else {
            executor = new AccessExecutor();
            executor.initAccess();
        }
    }

    /**
     * 访问执行对象
     */
    private AccessExecutor executor;

    /**
     * 访问结束后回调方法
     */
    interface AccessCallback {
        void handler();
    }

    /* 访问执行方法 */

    /**
     * 启动一次随机访问（单设备，单用户）
     */
    public void runOneRandomAccess() {
        executor.startOneAccess();
    }

    /**
     * 启动一次带回调的随机访问
     */
    public void runOneRandomAccessWithCallback(AccessCallback callback) {
        executor.startOneAccess();
        callback.handler();
    }

    /**
     * 启动一次随机的全设备访问
     */
    public void runAllRandomAccess() {
        executor.startAllAccess();
    }

    class AccessExecutor {

        private int deviceSize;
        private int personSize;

        /**
         * 用户访问时间间隔 {@literal <PersonnelId, LastAccessTime>}
         */
        private ConcurrentHashMap<String, Long> personAccessInterval;

        void initAccess() {
            deviceSize = communityMeta.getDeviceList().size();
            personSize = communityMeta.getPersonList().size();

            personAccessInterval = new ConcurrentHashMap<>(personSize * 2);
        }

        /**
         * 启动一次访问
         */
        void startOneAccess() {
            int deviceRandom = (int) (Math.random() * deviceSize);
            Device chooseDevice = communityMeta.getDeviceList().get(deviceRandom);

            int personRandom = (int) (Math.random() * personSize);
            Person choosePerson = communityMeta.getPersonList().get(personRandom);

            accessDevice(chooseDevice, choosePerson);
        }

        /**
         * 启动一次所有设备的访问
         */
        void startAllAccess() {
            communityMeta.getDeviceList().forEach(device -> {
                int personRandom = (int) (Math.random() * personSize);
                Person choosePerson = communityMeta.getPersonList().get(personRandom);

                accessDevice(device, choosePerson);
            });
        }

        // 随机失败概率 不进行任何模拟访问操作
        private static final double RANDOM_FAILURE_PROBABILITY = 0.15;
        // 用户访问时间最小间隔 6h = 1000ms * 60s * 60min * 6h
        private static final long TIME_OUT_MS = 1000 * 60 * 60 * 6;

        // 快速测试访问间隔 1min = 1000ms * 60s
        private static final long TEST_TIME_OUT_MS = 1000 * 60;

        /**
         * 访问设备并打印结果，TODO 模拟访问逻辑的添加
         */
        void accessDevice(Device theDevice, Person thePerson) {
            log.info("开始启动模拟访问逻辑...");

            // 随机失败
            double flag = Math.random();
            if (flag < RANDOM_FAILURE_PROBABILITY) {
                log.info("模拟访问随机失败...imei=" + theDevice.getImei());
                return;
            }

            // 获取用户上次访问时间
            String personnelId = thePerson.getPersonnelId();
            long lastaccesstime = personAccessInterval.getOrDefault(personnelId, 0L);
            // 用户第一次访问
            if (lastaccesstime == 0L) {
                personAccessInterval.put(personnelId, System.currentTimeMillis());
            }
            // 判断是否满足最小访问时间间隔
            else {
                long currentTime = System.currentTimeMillis();
                long subtract = currentTime - lastaccesstime;

                // 访问间隔过小
                if (subtract < TEST_TIME_OUT_MS) {
                    log.info("模拟访问访问间隔过小...imei=" + theDevice.getImei());
                    return;
                }
            }

            /* 执行访问 */

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
            postForRecord(record);
        }
    }

    private static final String RECORD_API = "http://localhost:9006/record";

    /**
     * http远程插入record数据
     */
    private void postForRecord(Record record) {
        ResultModel result = new RestTemplate().postForObject(RECORD_API, record, ResultModel.class);
        if (result.getCode() != 0) {
            throw new BackRemoteException("Post for record error!");
        }
    }
}

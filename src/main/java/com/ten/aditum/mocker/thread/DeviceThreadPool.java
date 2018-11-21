package com.ten.aditum.mocker.thread;

import com.ten.aditum.mocker.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Device线程池
 */
@Component
public class DeviceThreadPool {
    public static final DeviceThreadPool INSTANCE = new DeviceThreadPool();

    /**
     * 随机失败概率 不进行任何模拟访问操作
     */
    private static final double RANDOM_FAILURE_PROBABILITY = 0.25;
    /**
     * 用户访问时间最小间隔 6h = 1000ms * 60s * 60min * 6h
     */
    private static final long TIME_OUT_MS = 1000 * 60 * 60 * 6;

    /* --------------------------------------------------------------------- */

    /**
     * 初始用户数量
     */
    private Integer personCount = 16;

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    /**
     * 用户访问时间间隔 {@literal <PersonnelId, LastAccessTime>}
     */
    private ConcurrentHashMap<String, Long> personAccessInterval = new ConcurrentHashMap<>(personCount * 2);
    /**
     * 用户访问进出顺序 {@literal <PersonnelId, LastAccessOrder>} true:in false:out
     */
    private ConcurrentHashMap<String, Boolean> personAccessOrder = new ConcurrentHashMap<>(personCount * 2);

    /* --------------------------------------------------------------------- */

    /**
     * 用户集合
     */
    private List<Person> personList;
    /**
     * 设备线程集合
     */
    private List<DeviceThread> deviceThreads;

    /**
     * 设置用户集合
     */
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    /**
     * 设置设备线程集合
     */
    public void setDeviceThreads(List<DeviceThread> deviceThreads) {
        this.deviceThreads = deviceThreads;
    }

    /* --------------------------------------------------------------------- */

    /**
     * 启动线程池
     */
    public void runThreadPoll() {
        for (DeviceThread thread : deviceThreads) {
            runThread(thread);
        }
    }

    /**
     * 启动线程 进行线程状态设置
     */
    private void runThread(DeviceThread thread) {
        // 随机失败
        double flag = Math.random();
        if (flag < RANDOM_FAILURE_PROBABILITY) {
            return;
        }

        // 随机获取Person访问用户
        int index = (int) (Math.random() * personList.size());
        Person person = personList.get(index);

        // 获取用户上次访问时间
        String personnelId = person.getPersonnelId();
        long lastaccesstime = personAccessInterval.get(personnelId);

        // 判断是否满足最小访问时间间隔
        long currentTime = System.currentTimeMillis();
        long subtract = currentTime - lastaccesstime;

        if (subtract < TIME_OUT_MS) {
            return;
        }

        //

        thread.visite();
    }
}

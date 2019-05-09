package com.ten.aditum.mocker.config;

import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 社区数据元对象容器集合
 */
@Slf4j
@Service
public class CommunityConfigHolder {
    /**
     * 社区数据对象的集合
     */
    private static Map<String, CommunityConfig> communityMetaIdMap = new ConcurrentHashMap<>();
    private static Map<String, CommunityConfig> communityMetaNameMap = new ConcurrentHashMap<>();

    /**
     * 初始化对象数据
     */
    synchronized static void newCommunityMeta(Community community,
                                              List<Device> devices,
                                              List<Person> people) {
        CommunityConfig meta = new CommunityConfig();
        meta.setCommunity(community);
        meta.setDeviceList(devices);
        meta.setPersonList(people);
        putCommunityMetaMap(community, meta);
    }

    /**
     * 将元数据对象放入容器
     */
    private static void putCommunityMetaMap(Community community,
                                            CommunityConfig meta) {
        communityMetaIdMap.put(community.getCommunityId(), meta);
        communityMetaNameMap.put(community.getCommunityName(), meta);
    }

    /**
     * 获取所有社区Mata的集合
     */
    public static List<CommunityConfig> getAllCommunityMeta() {
        return new ArrayList<>(communityMetaIdMap.values());
    }

    /**
     * 通过社区名称获取Meta
     */
    public static CommunityConfig getCommunityMetaByName(String communityName) {
        return communityMetaNameMap.get(communityName);
    }

    /**
     * 通过社区ID获取Meta
     */
    public static CommunityConfig getCommunityMetaById(String communityId) {
        return communityMetaIdMap.get(communityId);
    }

    public static String getCommunityMetaString() {
        StringBuilder builder = new StringBuilder();
        communityMetaIdMap.forEach((key, value) -> {
            builder.append(value.toString());
            builder.append("\r\n");
        });
        return builder.toString();
    }
}

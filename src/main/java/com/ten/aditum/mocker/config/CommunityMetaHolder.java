package com.ten.aditum.mocker.config;

import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

/**
 * 社区数据元对象容器集合
 */
@Slf4j
public class CommunityMetaHolder {
    /**
     * 社区数据对象的集合
     */
    private static HashMap<String, CommunityMeta> communityMetaIdMap = new HashMap<>();
    private static HashMap<String, CommunityMeta> communityMetaNameMap = new HashMap<>();

    /**
     * 初始化对象数据
     */
    public static void newCommunityMeta(Community community,
                                        List<Device> devices,
                                        List<Person> people) {
        CommunityMeta meta = new CommunityMeta();
        meta.setCommunity(community);
        meta.setDeviceList(devices);
        meta.setPersonList(people);
        putCommunityMetaMap(community, meta);
    }

    /**
     * 将元数据对象放入容器：插入数据的操作为单线程操作，故采用非线程安全的hashmap也可
     */
    private static void putCommunityMetaMap(Community community,
                                            CommunityMeta meta) {
        communityMetaIdMap.putIfAbsent(community.getCommunityId(), meta);
        communityMetaNameMap.putIfAbsent(community.getCommunityName(), meta);
    }

    /**
     * 通过社区名称获取Meta
     */
    public static CommunityMeta getCommunityMetaByName(String communityName) {
        return communityMetaNameMap.get(communityName);
    }

    /**
     * 通过社区ID获取Meta
     */
    public static CommunityMeta getCommunityMetaById(String communityId) {
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

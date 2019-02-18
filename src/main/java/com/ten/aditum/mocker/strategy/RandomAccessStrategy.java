package com.ten.aditum.mocker.strategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 随机进出策略，随机进出
 */
public class RandomAccessStrategy implements AccessStrategy {

    public ConcurrentHashMap<String, AccessType> personAccessTypeMap =
            new ConcurrentHashMap<>(16);

    @Override
    public AccessType select(String personId) {
        AccessType type = personAccessTypeMap.get(personId);
        // 初始化人员
        if (type == null) {
            type = AccessType.Enter;
        }
        // 进出顺序翻转
        personAccessTypeMap.put(personId, random());
        return type;
    }

    /**
     * 随机进出顺序
     */
    private AccessType random() {
        return Math.random() > 0.5 ? AccessType.Enter : AccessType.Out;
    }

}

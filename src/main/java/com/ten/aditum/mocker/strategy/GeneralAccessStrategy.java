package com.ten.aditum.mocker.strategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 顺序进出策略，一进一出
 */
public class GeneralAccessStrategy implements AccessStrategy {

    public ConcurrentHashMap<String, AccessType> personAccessTypeMap =
            new ConcurrentHashMap<>(16);

    @Override
    public AccessType select(String personId) {
        AccessType type = personAccessTypeMap.get(personId);
        if (type == null) {
            // 初始化人员
            personAccessTypeMap.put(personId, AccessType.Enter);
        } else {
            // 进出顺序翻转
            personAccessTypeMap.put(personId, flip(type));
        }
        return type;
    }

    /**
     * 翻转进出顺序
     */
    public static AccessType flip(AccessType type) {
        switch (type) {
            case Enter:
                return AccessType.Out;
            case Out:
                return AccessType.Enter;
            default:
                throw new RuntimeException("Undefined access type! What do you want to do? ");
        }
    }

}

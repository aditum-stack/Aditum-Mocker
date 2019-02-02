package com.ten.aditum.mocker.strategy;

/**
 * 访问顺序策略
 */
public interface AccessStrategy {
    /**
     * 用户进出顺序
     */
    AccessType select(String personId);
}

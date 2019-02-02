package com.ten.aditum.mocker.strategy;

/**
 * 访问顺序策略类型
 */
public enum AccessStrategyType {
    // 顺序 or 随机
    GENERAL("一进一出"),
    RANDOM("随机进出");

    private String value;

    AccessStrategyType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

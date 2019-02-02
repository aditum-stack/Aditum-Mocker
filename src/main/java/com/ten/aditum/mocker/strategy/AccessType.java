package com.ten.aditum.mocker.strategy;

/**
 * 进门 or 出门
 */
public enum AccessType {
    // 进门 or 出门
    Enter(0, "进门"),
    Out(1, "出门");

    private int value;
    private String desc;

    AccessType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int num() {
        return this.value;
    }

    @Override
    public String toString() {
        return desc;
    }
}

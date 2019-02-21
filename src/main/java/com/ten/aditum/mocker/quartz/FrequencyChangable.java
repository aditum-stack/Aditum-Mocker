package com.ten.aditum.mocker.quartz;

/**
 * 可改变访问频率
 */
public interface FrequencyChangable {
    void changeFrequency(long val);
}

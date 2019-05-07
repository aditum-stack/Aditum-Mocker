package com.ten.aditum.mocker.config;

import com.ten.aditum.mocker.entity.Community;
import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.entity.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 社区数据元对象：包含一个社区的community、device、person数据
 */
@Data
public class CommunityMeta {
    /**
     * 社区对象数据信息
     */
    private Community community;
    private List<Device> deviceList;
    private List<Person> personList;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\r\nCommunityMeta init :");
        builder.append(this.community.toString());
        builder.append("\r\nDeviceList: ");
        this.deviceList.forEach(device -> builder.append(device.toString()));
        builder.append("\r\nPersonList: ");
        this.personList.forEach(person -> builder.append(person.toString()));
        return builder.toString();
    }
}

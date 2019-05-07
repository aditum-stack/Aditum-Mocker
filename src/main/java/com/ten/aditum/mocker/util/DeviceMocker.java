package com.ten.aditum.mocker.util;

import com.ten.aditum.mocker.entity.Device;
import com.ten.aditum.mocker.http.BackRemoteApi;

/**
 * Device模拟器
 */
public class DeviceMocker {

    /**
     * 江南大学ID
     */
    private static final String COMMUNITY_ID = "feeca8a4a93d4188ba5b98bdf0c211cd";

    private static final String[] ADDRESS = ("北门,南门,西门,东门," +
            "北区桃园,北区桂园,北区李园,北区杏园,北区桔园,北区梅园,北区榴园," +
            "南区涓苑,南区清苑,南区溪苑,南区澈苑,南区鸿苑,南区润苑,南区浩苑,南区翰苑,南区淳苑").split(",");

    private static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回别名
     */
    private static String getAlias() {
        int index = getNum(0, ADDRESS.length - 1);
        String first = ADDRESS[index];
        String second = getNum(1, 2) + "号";
        String third = "门禁";
        return first + second + third;
    }

    /**
     * 产生随机Device
     */
    public static Device newDevice() {
        Device device = new Device();
        device.setAlias(getAlias());
        device.setCommunityId(COMMUNITY_ID);
        return device;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            Device newDevice = newDevice();
            System.out.println(newDevice);
            BackRemoteApi.postForDevice(newDevice);
        }
    }

}

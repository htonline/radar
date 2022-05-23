package com.example.upload.entity;

import java.io.Serializable;

public class DeviceRes implements Serializable {
//    "deviceType": "天线",
//    "deviceBianhao": "10025",
//    "deviceModel": "cssss"
    String deviceType;
    String deviceBianhao;
    String deviceModel;

    @Override
    public String toString() {
        return "DeviceRes{" +
                "deviceType='" + deviceType + '\'' +
                ", deviceBianhao='" + deviceBianhao + '\'' +
                ", deviceModel='" + deviceModel + '\'' +
                '}';
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceBianhao() {
        return deviceBianhao;
    }

    public void setDeviceBianhao(String deviceBianhao) {
        this.deviceBianhao = deviceBianhao;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}

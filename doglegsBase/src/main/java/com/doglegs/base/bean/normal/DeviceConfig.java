package com.doglegs.base.bean.normal;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class DeviceConfig implements Parcelable {

    // 版本
    private String version;
    // 版本号
    private int versionCode;
    // 固件
    private String firmware;
    // 固件对应的sdk版本
    private int sdkVersion;
    // 分辨率
    private String resolution;
    // 硬件架构 armeabi|armeabi-v7a
    private String abi;
    // 屏幕密度
    private String density;
    // 设备名 Nexus One
    private String model;
    // 设备厂商
    private String brand;
    // 设备id(手机本身参数IMEI)
    private String imei;
    // 卡id
    private String simId;
    // mac
    private String mac;
    // 用户的uuid
    private String userUuid;
    // 是否模拟器
    private boolean isEmulator;

    public DeviceConfig() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version);
        dest.writeInt(this.versionCode);
        dest.writeString(this.firmware);
        dest.writeInt(this.sdkVersion);
        dest.writeString(this.resolution);
        dest.writeString(this.abi);
        dest.writeString(this.density);
        dest.writeString(this.model);
        dest.writeString(this.brand);
        dest.writeString(this.imei);
        dest.writeString(this.simId);
        dest.writeString(this.mac);
        dest.writeString(this.userUuid);
        dest.writeByte(this.isEmulator ? (byte) 1 : (byte) 0);
    }

    protected DeviceConfig(Parcel in) {
        this.version = in.readString();
        this.versionCode = in.readInt();
        this.firmware = in.readString();
        this.sdkVersion = in.readInt();
        this.resolution = in.readString();
        this.abi = in.readString();
        this.density = in.readString();
        this.model = in.readString();
        this.brand = in.readString();
        this.imei = in.readString();
        this.simId = in.readString();
        this.mac = in.readString();
        this.userUuid = in.readString();
        this.isEmulator = in.readByte() != 0;
    }

    public static final Creator<DeviceConfig> CREATOR = new Creator<DeviceConfig>() {
        @Override
        public DeviceConfig createFromParcel(Parcel source) {
            return new DeviceConfig(source);
        }

        @Override
        public DeviceConfig[] newArray(int size) {
            return new DeviceConfig[size];
        }
    };
}

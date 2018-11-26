package com.doglegs.base.bean.point;

import lombok.Data;

@Data
public class DeviceOperate {


    /**
     * abi : string
     * appVersion : string
     * businessId : 0
     * businessParam : string
     * currentSystem : string
     * density : string
     * firmware : string
     * idfa : string
     * imei : string
     * ip : string
     * lat : 0
     * lng : 0
     * mac : string
     * networkType : 0
     * openudid : string
     * operateId : string
     * operateType : 0
     * phoneBrand : string
     * phoneModel : string
     * resolution : string
     * sdkVersion : 0
     * simId : string
     * systemType : 0
     * versionCode : 0
     */

    //硬件架构
    private String abi;
    //版本（外部）
    private String appVersion;
    //业务ID
    private long businessId;
    //业务参数
    private String businessParam;
    //当前系统（例子ios8、安卓8.1）
    private String currentSystem;
    //屏幕密度
    private String density;
    //固件
    private String firmware;
    //广告标示符
    private String idfa;
    //手机设备标识(安卓)
    private String imei;
    //ip地址
    private String ip;
    //纬度
    private double lat;
    //经度
    private double lng;
    //mac
    private String mac;
    //网络类型（1、wifi 2、4G 3、3G 4、2G）
    private int networkType;
    //手机设备标识(苹果)
    private String openudid;
    //操作编号
    private String operateId;
    //操作类型（1打开、2关闭）
    private int operateType;
    //安装包来源
    private String packageSource;
    //手机品牌（例子苹果、华为）
    private String phoneBrand;
    //手机型号（例子7P、8P）
    private String phoneModel;
    //分辨率
    private String resolution;
    //固件对应的sdk版本
    private int sdkVersion;
    //卡id
    private String simId;
    //系统类型（1安卓、2苹果）
    private int systemType;
    //版本号（内部）
    private int versionCode;


}

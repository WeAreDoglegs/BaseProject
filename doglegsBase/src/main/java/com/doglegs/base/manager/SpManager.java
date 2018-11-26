package com.doglegs.base.manager;

import android.text.TextUtils;

import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.normal.DeviceConfig;
import com.doglegs.base.bean.normal.LocationInfo;
import com.doglegs.core.constants.Environment;
import com.doglegs.core.utils.SPUtils;
import com.google.gson.Gson;

public class SpManager {

    private static final String SP_LOGIN_INFO = "login_info";

    private static final String SP_LOGIN_PHONE = "login_phone";

    private static final String SP_UMENT_TOKEN = "ument_token";

    private static final String SP_LOCATION_INFO = "location_info";

    private static final String SP_DEVICE_INFO = "device_info";

    private static final String SP_ENVIRONMENT_INFO = "environment_info";

    private static final String SP_MERCHANT_STATUS = "merchant_status";

    private static final String SP_NOTICE_LAST_LOOK_TIME = "notice_last_look_time";

    private static final String SP_MENU_CONFIG = "menu_config";

    // 保存渠道信息
    private static final String SP_CHANNEL_NAME = "channel_name";

    // 操作id
    private static final String SP_OPERATE_ID = "operate_id";


    // 保存激活码信息
    private static final String SP_ACTIVATE_CODE = "activate_code";

    /**
     * 保存登录信息
     */
    public static void saveLoginInfo(LoginInfo loginInfo) {
        SPUtils.put(SP_LOGIN_INFO, new Gson().toJson(loginInfo));
    }

    /**
     * 获取登录信息
     */
    public static LoginInfo getLoginInfo() {
        if (TextUtils.isEmpty(SPUtils.getString(SP_LOGIN_INFO))) {
            return new LoginInfo();
        } else {
            return new Gson().fromJson(SPUtils.getString(SP_LOGIN_INFO), LoginInfo.class);
        }
    }

    /**
     * 清空登录信息
     */
    public static void removeLoginInfo() {
        SPUtils.remove(SP_LOGIN_INFO);
    }

    /**
     * 保存友盟token
     */
    public static void saveUmentToken(String token) {
        SPUtils.put(SP_UMENT_TOKEN, token);
    }

    /**
     * 获取友盟token
     */
    public static String getUmentToken() {
        return SPUtils.getString(SP_UMENT_TOKEN, "");
    }

    /**
     * 清空友盟token
     */
    public static void removeUmentToken() {
        SPUtils.remove(SP_UMENT_TOKEN);
    }


    /**
     * 保存登录手机号
     */
    public static void saveLoginPhone(String phone) {
        SPUtils.put(SP_LOGIN_PHONE, phone);
    }

    /**
     * 获取登录手机号
     */
    public static String getLoginPhone() {
        return SPUtils.getString(SP_LOGIN_PHONE);
    }

    /**
     * 保存定位信息
     */
    public static void saveLocationInfo(LocationInfo locationInfo) {
        SPUtils.put(SP_LOCATION_INFO, new Gson().toJson(locationInfo));
    }

    /**
     * 获取定位信息
     */
    public static LocationInfo getLocationInfo() {
        if (TextUtils.isEmpty(SPUtils.getString(SP_LOCATION_INFO))) {
            return new LocationInfo();
        } else {
            return new Gson().fromJson(SPUtils.getString(SP_LOCATION_INFO), LocationInfo.class);
        }
    }

    /**
     * 清空定位信息
     */
    public static void removeLocationInfo() {
        SPUtils.remove(SP_LOCATION_INFO);
    }


    /**
     * 保存设备信息
     */
    public static void saveDeviceInfo(DeviceConfig deviceConfig) {
        SPUtils.put(SP_DEVICE_INFO, new Gson().toJson(deviceConfig));
    }

    /**
     * 获取设备信息
     */
    public static DeviceConfig getDeviceInfo() {
        if (TextUtils.isEmpty(SPUtils.getString(SP_DEVICE_INFO))) {
            return new DeviceConfig();
        } else {
            return new Gson().fromJson(SPUtils.getString(SP_DEVICE_INFO), DeviceConfig.class);
        }
    }

    /**
     * 清空设备信息
     */
    public static void removeDeviceInfo() {
        SPUtils.remove(SP_DEVICE_INFO);
    }


    /**
     * 保存环境信息
     */
    public static void saveEnvironmentInfo(Environment environment) {
        SPUtils.putCommit(SP_ENVIRONMENT_INFO, new Gson().toJson(environment));
    }

    /**
     * 获取环境信息
     */
    public static Environment getEnvironmentInfo() {
        if (TextUtils.isEmpty(SPUtils.getString(SP_ENVIRONMENT_INFO))) {
            return null;
        } else {
            return new Gson().fromJson(SPUtils.getString(SP_ENVIRONMENT_INFO), Environment.class);
        }
    }

    /**
     * 清空环境信息
     */
    public static void removeEnvironmentInfo() {
        SPUtils.remove(SP_ENVIRONMENT_INFO);
    }

    /**
     * 清空环境信息
     */
    public static void removeMerchantVerifyInfo() {
        SPUtils.remove(SP_MERCHANT_STATUS);
    }

    /**
     * 保存通知最后查看时间
     */
    public static void saveNoticeLastLookTime(long time) {
        SPUtils.put(SP_NOTICE_LAST_LOOK_TIME, time);
    }

    /**
     * 获取通知最后查看时间
     */
    public static long getNoticeLastLookTime() {
        return SPUtils.getLong(SP_NOTICE_LAST_LOOK_TIME);
    }

    /**
     * 保存渠道名
     */
    public static void setChannelName(String channelName) {
        SPUtils.put(SP_CHANNEL_NAME, channelName);
    }

    /**
     * 获取渠道名
     */
    public static String getChannelName() {
        return SPUtils.getString(SP_CHANNEL_NAME);
    }

    /**
     * 保存激活码
     */
    public static void setActivateCode(String activateCode) {
        SPUtils.put(SP_ACTIVATE_CODE, activateCode);
    }

    /**
     * 获取激活码
     */
    public static String getActivateCode() {
        return SPUtils.getString(SP_ACTIVATE_CODE);
    }

    /**
     * 清空菜单配置
     */
    public static void removeMenuConfig() {
        SPUtils.remove(SP_MENU_CONFIG);
    }


    /**
     * 保存操作id
     */
    public static void saveOperateId(String operateId) {
        SPUtils.put(SP_OPERATE_ID, operateId);
    }

    /**
     * 获取操作id
     */
    public static String getOperateId() {
        return SPUtils.getString(SP_OPERATE_ID);
    }

    /**
     * 清空操作id
     */
    public static void removeOperateId() {
        SPUtils.remove(SP_OPERATE_ID);
    }
}

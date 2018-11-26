package com.doglegs.core.constants;

import android.os.Environment;

/**
 * desc : 配置信息
 */
public class Config {

    public static final String SP_FILE_NAME = "doglegs.sp";

    /**
     * 存储目录配置
     */
    public interface DIR {
        String ROOT = Environment.getExternalStorageDirectory() + "/yryc/store/";

        String IMG_DIR = ROOT + "images/";

        String VIDEO_DIR = ROOT + "videos/";

        String FILE_DIR = ROOT + "files/";

        String LOG_DIR = ROOT + "log/";
    }

    public interface WebAddress {
        String WEB_BASE = "/app/index.html#/app/";
        //营业汇总
        String WEB_SUMMARY = "summary?token=";
        //历史汇总
        String WEB_HISTORY_SUMMARY = "historySummary?token=";
        //流水明细
        String WEB_MERCHANTWALLETLIST = "merchantWalletList?token=";
        //商家钱包
        String WEB_MERCHANTWALLET = "merchantWallet";
        //服务条款
        String WEB_USERAGREEMENT = "servicePolicy?token=";
        //商家入驻
        String WEB_MERCHANTSTATUS = "merchantStatus";
        String WEB_MERCHANTENTER = "merchantEnter";
        //商家入驻第一步
        String WEB_MERCHANTBUSSINESSENTER1 = "bussinessEnter";

        //常用设置
        String WEB_SETTING = "merchantSetting";
        //优惠券
        String WEB_COUPON = "coupon";
        //优惠券详情
        String WEB_COUPON_DETAIL = "couponDetail";
        //新增优惠券
        String WEB_ADDCOUPON = "addcoupon";
    }

    /**
     * 请求头
     */
    public interface Headers {
        //用户登录token
        String AUTHORIZATION = "Authorization";
        //设备Imei
        String DEVICEID = "device-id";
        //平台标识，1为安卓,2为iOS,3为Web
        String YC_PLAT_ID = "yc-plat-id";
        //手机品牌
        String YC_PHONE_BRAND = "yc-phone-brand";
        //手机型号
        String YC_PHONE_MODEL = "yc-phone-model";
        //android sdk code
        String YC_SYSTEM = "yc-system";
        //时间戳
        String YC_TS = "yc-ts";
        //友盟token
        String YC_DEVICE_ID = "yc-device-id";
        //签名
        String YC_SIGN = "yc-sign";
        //uuid
        String YC_REQUEST_ID = "yc-request-id";
    }
}

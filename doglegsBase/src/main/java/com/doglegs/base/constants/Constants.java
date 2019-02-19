package com.doglegs.base.constants;

public class Constants {

    /**
     * js交互数据类型
     */
    public interface JsType {
        //退出登录
        String LOGIN = "LOGIN";
        //地图
        String MAP = "MAP";
        //拍照、从相册选择
        String UPLOAD = "UPLOAD";
        //进入首页
        String HOME = "HOME";
        //更新token
        String UPDATE_TOKEN = "UPDATE_TOKEN";
        //跳转webview
        String WEBVIEW = "WEB_VIEW";

        String SET_TITLE = "SET_TITLE";
        //跳转历史汇总页面
        String HISTORY = "HISTORY";
    }

    /**
     * 消息类型
     */
    public interface NoticeType {
        //系统通知类信息
        int NOTICE_TYPE_AFFICHE = 1;
        //公告类信息
        int NOTICE_TYPE_INFORM = 2;
    }

    /**
     * 支付类型
     */
    public interface PaymentType {
        //支付宝
        int ALIPAY = 1;
        //微信
        int WECHAT = 2;
    }

    /**
     * 支付状态
     */
    public interface PaymentStatus {
        //成功
        int SUCCESS = 1;
        //失败
        int FAIL = 2;
    }

    /**
     * activity request code
     */
    public interface RequestCode {
        /**
         * 系统拍照界面
         */
        int SYSTEM_CAPTURE_PHOTO_ACTIVTY = 1000;
        /**
         * 系统相片界面
         */
        int SYSTEM_CAPTURE_ALBUM_ACTIVTY = 1001;

        int MAINTENANCE_ITEM_ACTIVITY = 3000;

    }

    public interface UploadTwoImag {
        /**
         * 客户接待上传公里和车牌
         */
        int UPLOAD_LICENSE_PLATE = 222;
        int UPLOAD_KILOMETER = 333;
    }

    /**
     * 客户接待 startActivityForResult 选择车辆
     */
    public final static int SELECT_CAR = 444;
    /**
     * 客户登记完成跳开单的标示
     */
    public final static int REGISTRATION_COMPLETED_GO_BILL = 555;

    //友盟
    public static final String UM_MESSAGE_SECRET = "7b6dc77bcf1afa27c1f21b86c5d2bece";
    public static final String UM_ALIAS = "yryc_store";
    //魅族
    public static final String MEIZU_APP_ID = "115059";
    public static final String MEIZU_APP_KEY = "55000712e9ca44b4875a6183f3c35c39";
    //小米
    public static final String XIAOMI_APP_ID = "2882303761517847170";
    public static final String XIAOMI_APP_KEY = "5761784763170";
    //华为
    public static final String HUAWEI_APP_ID = "100374401";
    // 融云appKey
    public static final String RONG_APP_KEY_PRODUCT = "25wehl3u2s6kw";
    public static final String RONG_APP_KEY_TEST = "4z3hlwrv4o0it";
    //一键登录
    public static final String ONEPASS_APP_ID = "300011868963";
    public static final String ONEPASS_APP_KEY = "E20F276FD09CE03FBCAB38A37B267C45";

    public static final String CUSTOMER_SERVICE_HOTLINE = "059187311661";

    //高德请求正确状态码
    public static final Integer GAODE_OK_CODE = 1000;

}

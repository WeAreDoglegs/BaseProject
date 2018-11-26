package com.doglegs.core.rx;

import lombok.Data;

/**
 * desc:事件类型数据
 */
@Data
public class RxEvent {

    private int eventType;
    private Object data;

    public RxEvent(int eventType, Object data) {
        this.eventType = eventType;
        this.data = data;
    }

    /**
     * 事件类型
     */
    public static class EventType {

        /**
         * 首页数据刷新
         */
        public static final int REFRESH_HOME = 1001;

        /**
         * 新订单
         */
        public static final int NEW_ORDER = 2001;

        /**
         * 刷新新任务列表
         */
        public static final int HOME_REFRESH_NEW_TASK = 2006;

        /**
         * 刷新进行中任务列表、新任务接单
         */
        public static final int HOME_REFRESH_PROCESSING = 2007;

        /**
         * 刷新订单列表数据
         */
        public static final int HOME_REFRESH_ORDER_LIST = 2008;

        /**
         * 刷新店铺状态
         */
        public static final int HOME_REFRESH_BISINESS_STATE = 2009;

        /**
         * 刷新订单详情页面数据
         */
        public static final int ORDER_DETAIL_REFRESH = 2100;

        /**
         * 跳转到扫码详情页面数据
         */
        public static final int ORDER_VERIFICATION_DETAIL = 2101;

        /**
         * 支付结果处理
         */
        public static final int HOME_REFRESH_PAYMENT_STATUS = 2300;

        /**
         * 移动开单、添加服务项目、确认选择
         */
        public static final int BILLING_ADD_SERVICE_CONFIRM_CHOOSE = 2400;

        /**
         * 移动开单、添加服务项目、同步购物车数据
         */
        public static final int BILLING_ADD_SERVICE_SYSC_SHOPPINGG_CART_DATA = 2401;

        /**
         * 移动开单、添加商品配件、确认选择
         */
        public static final int BILLING_ADD_GOODS_CONFIRM_CHOOSE = 2403;

        /**
         * 移动开单、添加服务项目、同步购物车数据
         */
        public static final int BILLING_ADD_GOODS_SYSC_SHOPPINGG_CART_DATA = 2404;

        /**
         * 清空购物车
         */
        public static final int BILLING_ADD_CLEAR_SHOPPINGG_CART_DATA = 2405;

        /**
         * 订单保存成功
         */
        public static final int BILLING_ADD_SAVE_ORDER_SUCCESS = 2406;

        /**
         * js 交互 进入地图
         */
        public static final int JS_MAP = 2500;

        /**
         * js 交互 拍照
         */
        public static final int JS_CAPTURE = 2501;

        /**
         * js 进入首页
         */
        public static final int JS_HOME = 2502;
        /**
         * js 更新token
         */
        public static final int JS_UPDATE_TOKEN = 2504;

        /**
         * js 历史订单页面
         */
        public static final int JS_HISTORY= 2505;


        /**
         * js 设置title
         */
        public static final int JS_PAYLOAD = 2503;

        /**
         * 地图搜索结果
         */
        public static final int MAP_SEARCH_RESULT = 2600;

        /**
         * 地图搜索结果确认
         */
        public static final int MAP_SEARCH_RESULT_CONFIRM = 2601;

        /**
         * 退出登录
         */
        public static final int SYSTEM_LOGIN_OUT = 3001;

        /**
         * 关闭除了首页所有activity
         */
        public static final int SYSTEM_CLOSE_ACTIVITY = 3003;

        /**
         * 登录成功关闭登录界面
         */
        public static final int SYSTEM_FINISH_LOGIN = 3002;

        /**
         * 更新我的界面
         */
        public static final int MINE_UPDATA_VIEW = 4008;


    }

}

package com.doglegs.core.utils;

import com.orhanobut.logger.Logger;
import com.doglegs.core.BuildConfig;

public class LogUtils {
    /**
     * 是否开启debug
     */
    public static boolean isDebug = BuildConfig.LOG_DEBUG;

    /**
     * 错误
     */

    public static void e(String msg) {
        if (isDebug) {
            Logger.e(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).e(msg);
        }
    }

    /**
     * 调试
     */
    public static void d(String msg) {
        if (isDebug) {
            Logger.d(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).d(msg);
        }
    }

    /**
     * 信息
     */

    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug) {
            Logger.t(tag).i(msg);
        }
    }
}

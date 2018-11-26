package com.doglegs.base.constants;

/**
 * desc : 平台
 */
public enum AppPlatform {

    IOS(1, "ios"),
    ANDROID(2, "android");

    private Integer code;

    private String message;

    AppPlatform(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

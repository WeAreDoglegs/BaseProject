package com.doglegs.base.constants;

/**
 * desc : 客户端
 */
public enum AppTerminal {

    CUSTOMER(1, "车主端", "USER"),
    MERCHANT(2, "商家端", "MERCHANT"),
    DRIVER(3, "司机端", "DRIVER");

    private Integer code;

    private String message;

    private String remark;

    AppTerminal(Integer code, String message, String remark) {
        this.code = code;
        this.message = message;
        this.remark = remark;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRemark() {
        return remark;
    }
}

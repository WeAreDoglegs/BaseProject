package com.doglegs.core.constants;

/**
 * desc : App环境
 */
public enum Environment {

    DEV(1, "dev", "开发环境", "http://dev.devproxy.51yryc.com", "http://merchant-app.dev.51yryc.com", "yryc-merchant-app-dev", "KEFU153441320068009"),
    TEST(2, "test", "测试环境", "http://test.devproxy.51yryc.com", "http://merchant-app.test.51yryc.com", "yryc-merchant-app-test", "KEFU153441320068009"),
    PRE_PROD(3, "pre", "预生产环境", "https://owner-api-pre.51yryc.com", "http://merchant-app-pre.51yryc.com", "yryc-merchant-app-pre", "KEFU153829434637206"),
    PROD(4, "prod", "生产环境", "https://owner-api.51yryc.com", "http://merchant-app.51yryc.com", "yryc-merchant-app-prod", "KEFU153829434637206");

    private Integer code;
    private String message;
    private String remark;
    private String httpAddress;
    private String webAddress;
    private String ossBucket;
    private String rongYunKey;

    Environment(Integer code, String message, String remark, String httpAddress, String webAddress, String ossBucket, String rongYunKey) {
        this.code = code;
        this.message = message;
        this.remark = remark;
        this.httpAddress = httpAddress;
        this.webAddress = webAddress;
        this.ossBucket = ossBucket;
        this.rongYunKey = rongYunKey;
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

    public String getWebAddress() {
        return webAddress;
    }

    public String getOssBucket() {
        return ossBucket;
    }

    public String getHttpAddress() {
        return httpAddress;
    }

    public String getRongYunKey() {
        return rongYunKey;
    }

    public static String getValueByKey(int key) {
        for (Environment c : Environment.values()) {
            if (c.getCode() == key) {
                return c.getRemark();
            }
        }
        return null;
    }
}

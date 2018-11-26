package com.doglegs.base.constants;

/**
 * desc : 渠道
 */

public enum AppChannel {

    TENCENT_MARKET("TencentMarket", 1, "应用宝"),
    BAIDU("BaiDu", 2, "百度"),
    ALI_MARKET("ALIMarket", 3, "阿里"),
    XIAO_MI("XiaoMi", 4, "小米"),
    MEI_ZU("MeiZu", 5, "魅族"),
    OPPO("OPPO", 6, "OPPO"),
    VIVO("VIVO", 7, "VIVO"),
    HUA_WEI("HuaWei", 8, "华为"),
    MARKET360("Market360", 9, "Market360"),
    SAM_SUNG("SamSung", 10, "SamSung"),
    LENOVO("Lenovo", 11, "联想");

    private String name;
    private Integer type;
    private String message;

    AppChannel(String name, Integer type, String message) {
        this.name = name;
        this.type = type;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}

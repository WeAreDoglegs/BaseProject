package com.doglegs.base.bean.normal;

import lombok.Data;

@Data
public class JsInterface {


    /**
     * type : SET_TITLE
     * payload : {"title":"商家入驻"}
     */

    private String type;
    private PayloadBean payload;

    @Data
    public static class PayloadBean {
        /**
         * title : 商家入驻
         */

        //标题栏
        private String title;
        private String url;
        //arrow:0   隐藏  1 显示
        private Integer arrow;
        //默认蓝色、true蓝色
        private boolean isDark;
        //1隐藏
        private int hideBar;

        private String token;

    }
}

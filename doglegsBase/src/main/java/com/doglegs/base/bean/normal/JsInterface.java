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

        private String title;
        //arrow:0   隐藏  1 显示
        private Integer arrow;
        private String token;

    }
}

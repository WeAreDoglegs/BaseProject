package com.doglegs.core.base;

/**
 * author: Mai_Xiao_Peng
 * email  : Mai_Xiao_Peng@163.com
 * time  : 2017/4/21
 */

public class BaseResponse<T> {

    private Integer code;

    private String message;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

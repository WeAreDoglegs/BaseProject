package com.doglegs.core.base;

/**
 * author: Mai_Xiao_Peng
 * email  : Mai_Xiao_Peng@163.com
 * time  : 2017/4/20
 */

public interface IBaseView {
    
    //开始加载
    void onStartLoad();

    //加载成功
    void onLoadSuccess();

    //加载失败
    void onLoadError();
}

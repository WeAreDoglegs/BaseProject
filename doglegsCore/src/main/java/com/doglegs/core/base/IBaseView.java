package com.doglegs.core.base;

public interface IBaseView {
    
    //开始加载
    void onStartLoad();

    //加载成功
    void onLoadSuccess();

    //加载失败
    void onLoadError();
}

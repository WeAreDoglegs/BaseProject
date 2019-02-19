package com.doglegs.core.base;

import android.os.Bundle;

public interface IBaseView {

    int getLayoutId();

    void inject();

    void initView(Bundle savedInstanceState);

    void initData();

    //开始加载
    void onStartLoad();

    //加载成功
    void onLoadSuccess();

    //加载失败
    void onLoadError();
}

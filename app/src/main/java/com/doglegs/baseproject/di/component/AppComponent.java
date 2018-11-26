package com.doglegs.baseproject.di.component;

import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.normal.LocationInfo;
import com.doglegs.baseproject.App;
import com.doglegs.baseproject.di.module.AppModule;
import com.doglegs.baseproject.di.scope.GlobleScope;
import com.doglegs.core.download.DownloadManager;

import dagger.Component;
import retrofit2.Retrofit;

@GlobleScope
@Component(modules = {AppModule.class})
public interface AppComponent {

    //获取AppModule提供的AppContext
    App getContext();

    //获取HttpModule提供的Retrofit
    Retrofit getRetrofit();

    LoginInfo getLoginInfo();

    LocationInfo getLocationInfo();

    DownloadManager getDownloadManager();

    void inject(App app);

}

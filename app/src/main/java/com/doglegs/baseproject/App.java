package com.doglegs.baseproject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.doglegs.base.manager.SpManager;
import com.doglegs.baseproject.di.component.AppComponent;
import com.doglegs.baseproject.di.component.DaggerAppComponent;
import com.doglegs.baseproject.di.module.AppModule;
import com.doglegs.core.BaseApp;
import com.doglegs.core.base.BaseResponse;
import com.doglegs.core.constants.Environment;
import com.doglegs.core.utils.CrashUtils;
import com.doglegs.core.utils.StringUtils;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class App extends BaseApp {

    @Inject
    public Lazy<Retrofit> mRetrofitLazy;

    public static AppComponent sAppComponent;

    public static Environment sEnvironment;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onAppCreate() {
        sAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        sAppComponent.inject(this);
        initAppEnvironment();
        initARouter();
    }


    /**
     * 初始化App环境
     */
    private void initAppEnvironment() {
        if (BuildConfig.LOG_DEBUG) {
            CrashUtils.getInstance().init();
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
        } else {
        }
        if (BuildConfig.API_TYPE != Environment.PROD.getCode()) {
            if (SpManager.getEnvironmentInfo() == null) {
                if (BuildConfig.API_TYPE == Environment.DEV.getCode()) {
                    sEnvironment = Environment.DEV;
                } else if (BuildConfig.API_TYPE == Environment.TEST.getCode()) {
                    sEnvironment = Environment.TEST;
                } else {
                    sEnvironment = Environment.PRE_PROD;
                }
                SpManager.saveEnvironmentInfo(sEnvironment);
            } else {
                sEnvironment = SpManager.getEnvironmentInfo();
            }
        } else {
            sEnvironment = Environment.PROD;
        }

        // 加载渠道信息
        ApplicationInfo appInfo = null;
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String channelName = appInfo.metaData.getString("UMENG_CHANNEL");
            if (!StringUtils.isEmptyString(channelName)) {
                SpManager.setChannelName(channelName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.LOG_DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    interface IAppApi {

        @POST("/b/api/v1/message/receive")
        Flowable<BaseResponse> postMessageReceive(@Body Map<String, Object> body);

        @POST("/b/api/v1/message/read")
        Flowable<BaseResponse> postMessageRead(@Body Map<String, Object> body);

    }
}

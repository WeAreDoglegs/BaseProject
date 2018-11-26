package com.doglegs.baseproject.di.module;

import android.util.Log;


import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.normal.DeviceConfig;
import com.doglegs.base.bean.normal.LocationInfo;
import com.doglegs.base.manager.SpManager;
import com.doglegs.baseproject.App;
import com.doglegs.baseproject.di.scope.GlobleScope;
import com.doglegs.core.constants.Config;
import com.doglegs.core.constants.Environment;
import com.doglegs.core.download.DownloadManager;
import com.doglegs.core.security.SSL;
import com.doglegs.core.security.SecurityUtil;
import com.doglegs.widget.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private String TAG = AppModule.class.getSimpleName();

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @GlobleScope
    public App provideApplicationContext() {
        return application;
    }

    @Provides
    public LoginInfo provideLoginInfo() {
        return SpManager.getLoginInfo();
    }

    @Provides
    @GlobleScope
    public LocationInfo provideLocationInfo() {
        return SpManager.getLocationInfo();
    }

    /**
     * 接口请求
     *
     * @return
     */
    @Provides
    @GlobleScope
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //添加日志拦截器,BEBUG打印HTTP请求日志
        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                try {
                    message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                    message = message.replaceAll("\\+", "%2B");
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.i(TAG, text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e(TAG, message);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        builder.addNetworkInterceptor(chain -> {
            LoginInfo loginInfo = SpManager.getLoginInfo();
            DeviceConfig deviceConfig = SpManager.getDeviceInfo();
            //基本信息
            String salt = "0S8iqFBjpQRFMfVj7YmnqHNBCnff9PnKvZYRuB49UnO90YBW65hy8qlzTrDd3QXC";
            String signBody = null;
            String ts = String.valueOf(System.currentTimeMillis());
            String deviceId = deviceConfig.getImei();
            String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //构建请求
            Request request = chain.request();
            if (request.method() == "GET") {
                Set<String> valueMap = request.url().queryParameterNames();
                List<String> paramas = new ArrayList<>();
                for (String s : valueMap) {
                    paramas.add(s);
                }
                Collections.sort(paramas, (o1, o2) -> o1.compareTo(o2));
                StringBuffer stringBuffer = new StringBuffer();
                if (paramas.size() > 0) {
                    for (String str : paramas) {
                        stringBuffer.append(str);
                        stringBuffer.append(request.url().queryParameter(str));
                    }
                }
                signBody = stringBuffer.toString() + ts + deviceId + salt + uuid;
            } else if (request.method() == "POST") {
                RequestBody body = request.body();
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset();
                }
                signBody = buffer.readString(charset) + ts + deviceId + salt + uuid;
            }
            request = request.newBuilder().addHeader(Config.Headers.DEVICEID, deviceId)
                    .addHeader(Config.Headers.YC_PLAT_ID, "1")
                    .addHeader(Config.Headers.YC_PHONE_BRAND, deviceConfig.getBrand())
                    .addHeader(Config.Headers.YC_PHONE_MODEL, deviceConfig.getModel())
                    .addHeader(Config.Headers.YC_SYSTEM, String.valueOf(deviceConfig.getSdkVersion()))
                    .addHeader(Config.Headers.YC_TS, ts)
                    .addHeader(Config.Headers.YC_DEVICE_ID, SpManager.getUmentToken())
                    .addHeader(Config.Headers.YC_REQUEST_ID, uuid)
                    .addHeader(Config.Headers.YC_SIGN, SecurityUtil.SHA1(signBody)).build();
            Response originalResponse = chain.proceed(request);
            return originalResponse;
        });
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS));
        //错误重连
        builder.retryOnConnectionFailure(true);
        if (App.sEnvironment.getCode() == Environment.PRE_PROD.getCode()) {
            builder.sslSocketFactory(SSL.createSSLSocketFactory());
        }
        return builder.build();
    }

    /**
     * 域名接口请求
     *
     * @param client
     * @return
     */
    @Provides
    @GlobleScope
    public Retrofit provideDomainRetrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (App.sEnvironment.getCode() == Environment.DEV.getCode()) {
            return builder.baseUrl(Environment.DEV.getHttpAddress()).
                    client(client).addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        } else if (App.sEnvironment.getCode() == Environment.TEST.getCode()) {
            return builder.baseUrl(Environment.TEST.getHttpAddress()).
                    client(client).addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        } else if (App.sEnvironment.getCode() == Environment.PRE_PROD.getCode()) {
            return builder.baseUrl(Environment.PRE_PROD.getHttpAddress()).
                    client(client).addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        } else {
            return builder.baseUrl(Environment.PROD.getHttpAddress()).
                    client(client).addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }
    }

    @Provides
    @GlobleScope
    public DownloadManager provideDownloadManager() {
        return new DownloadManager(App.sEnvironment.getHttpAddress());
    }

}

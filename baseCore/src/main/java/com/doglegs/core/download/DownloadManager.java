package com.doglegs.core.download;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.doglegs.core.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/22 15:46
 * @describe :
 */


public class DownloadManager {

    private String TAG = DownloadManager.class.getSimpleName();
    private Api api;

    public DownloadManager(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new FileResponseBody(originalResponse))//将自定义的ResposeBody设置给它
                            .build();
                }).build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    /**
     * 是否需要更新,需要则下载
     *
     * @param url     新版本地址
     * @param apkPath 本地apk保存路径
     */
    public void downloadApk(final String url, final String apkPath, CompositeDisposable cd, OnDownloadLisener onDownloadLisener) {
        api.down(url).map(responseBody -> responseBody.source()).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<BufferedSource>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(BufferedSource bufferedSource) {
                        try {
                            writeFile(bufferedSource, new File(apkPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        unSubscribe(cd);
                    }

                    @Override
                    public void onComplete() {
                        if (onDownloadLisener != null) {
                            onDownloadLisener.onDownloadFinish(url, apkPath);
                        }
                        unSubscribe(cd);
                    }
                });
    }


    /**
     * 写入文件
     */
    private void writeFile(BufferedSource source, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file.exists())
            file.delete();

        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeAll(source);

        bufferedSink.close();
        source.close();
    }


    /**
     * 解除订阅
     *
     * @param cd 订阅关系集合
     */
    private void unSubscribe(CompositeDisposable cd) {
        if (cd != null && !cd.isDisposed())
            cd.dispose();
    }

    public interface OnDownloadLisener {
        void onDownloadFinish(String url, String path);
    }
}

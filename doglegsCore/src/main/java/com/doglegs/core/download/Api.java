package com.doglegs.core.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * desc : 网络下载接口声明
 */
public interface Api {

    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);
}

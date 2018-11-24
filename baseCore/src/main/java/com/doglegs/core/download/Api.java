package com.doglegs.core.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/22 15:49
 * @describe : 网络下载接口声明
 */

public interface Api {

    @Streaming
    @GET
    Observable<ResponseBody> down(@Url String url);



}

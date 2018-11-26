package com.doglegs.baseproject.start.model;


import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.net.UpdateInfo;
import com.doglegs.core.base.BaseResponse;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 登录业务流程数据接口声明
 */
public interface IStartApi {

    /**
     * 登录
     *
     * @param body
     * @return
     */
    @POST("/b/api/v1/loginByPhone")
    Flowable<BaseResponse<LoginInfo>> login(@Body Map<String, String> body);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @POST("/b/api/v1/register")
    Flowable<BaseResponse<LoginInfo>> userRegister(@Body Map<String, String> body);

    /**
     * 一键登录
     *
     * @param body
     * @return
     */
    @POST("/b/api/v1/checkLogin")
    Flowable<BaseResponse<LoginInfo>> checkLogin(@Body Map<String, String> body);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("/b/api/v1/logout")
    Flowable<BaseResponse> postLoginOut();

    /**
     * 刷新用户信息
     */
    @GET("/b/api/v1/user/info")
    Flowable<BaseResponse<LoginInfo>> getUserInfo();

    /**
     * 获取版本信息
     *
     * @param appTerminal
     * @param versionCode
     * @param appPlatform
     * @param appChannel
     */
    @GET("/b/api/v1/version/last")
    Flowable<BaseResponse<UpdateInfo>> getVersionLast(@Query("appTerminal") int appTerminal, @Query("versionCode") int versionCode,
                                                      @Query("appPlatform") int appPlatform, @Query("appChannel") int appChannel);

    /**
     * 解析剪切板信息
     *
     * @param code
     * @return
     */
    @GET("/b/api/v1/clipboard/decode")
    Flowable<BaseResponse> parseClipboard(@Query("code") String code);

    /**
     * 提交商家认领日志
     *
     * @param body
     * @return
     */
    @POST("/b/api/v1/browseMerchant/add")
    Flowable<BaseResponse> submitMerchantClaimLog(@Body Map<String, Long> body);
}

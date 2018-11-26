package com.doglegs.baseproject.start.model;

import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.net.UpdateInfo;
import com.doglegs.core.base.BaseResponse;
import com.doglegs.core.base.BaseRetrofit;
import com.doglegs.core.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * 登录流程接口声明
 */
public class StartRetrofit extends BaseRetrofit {

    private IStartApi startApi;

    public StartRetrofit(IStartApi startApi) {
        this.startApi = startApi;
    }

    /**
     * 登录
     *
     * @param telephone 手机号
     * @param smsCode   验证码
     * @return
     */
    public Flowable<BaseResponse<LoginInfo>> login(String telephone, String smsCode) {
        Map<String, String> params = new HashMap<>();
        params.put("telephone", telephone);
        params.put("smsCode", smsCode);
        return startApi.login(params);
    }

    /**
     * 一键登录
     *
     * @return
     */
    public Flowable<BaseResponse<LoginInfo>> checkLogin(String telephone, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", telephone);
        params.put("token", token);
        return startApi.checkLogin(params);
    }

    /**
     * 退出登录
     *
     * @return
     */
    public Flowable<BaseResponse> postLoginOut() {
        return startApi.postLoginOut();
    }

    /**
     * 刷新用户信息
     */
    public Flowable<BaseResponse<LoginInfo>> getUserInfo() {
        return startApi.getUserInfo();
    }

    /**
     * 获取版本信息
     * <p>
     * appTerminal 是
     * 终端类型 1.车主端 2.商家端 3.司机端
     * <p>
     * versionCode 是
     * 客户端当前版本号
     * <p>
     * appPlatform 是
     * app的系统平台 1.iOS 2.android
     * <p>
     * appChannel 是
     * 应用发布的渠道1=>’官网渠道’,2=>’应用宝’,3=>’百度’,4=>’阿里’,5=>’小米’
     *
     * @return
     */
    public Flowable<BaseResponse<UpdateInfo>> getVersionLast(int appTerminal, int versionCode, int appPlatform, int appChannel) {
        return startApi.getVersionLast(appTerminal, versionCode, appPlatform, appChannel);
    }

    /**
     * 注册
     *
     * @param telephone    手机号
     * @param smsCode      验证码
     * @param activateCode 激活码
     * @return
     */
    public Flowable<BaseResponse<LoginInfo>> userRegister(String telephone, String smsCode, String activateCode) {
        Map<String, String> params = new HashMap<>();
        params.put("telephone", telephone);
        params.put("smsCode", smsCode);
        if (!StringUtils.isEmptyString(activateCode)) {
            params.put("activateCode", activateCode);
        }
        return startApi.userRegister(params);
    }

    /**
     * 剪切板信息提交
     *
     * @param code 激活code
     * @return
     */
    public Flowable<BaseResponse> parseClipboard(String code) {
        return startApi.parseClipboard(code);
    }

    /**
     * 提交商家认领日志
     *
     * @param merchantId
     * @return
     */
    public Flowable<BaseResponse> submitMerchantClaimLog(long merchantId) {
        Map<String, Long> params = new HashMap<>();
        params.put("merchantId", merchantId);
        return startApi.submitMerchantClaimLog(params);
    }
}

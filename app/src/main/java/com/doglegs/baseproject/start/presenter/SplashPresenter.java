package com.doglegs.baseproject.start.presenter;

import com.doglegs.baseproject.start.model.StartRetrofit;
import com.doglegs.baseproject.start.presenter.contract.ISplashContract;
import com.doglegs.core.rx.RxPresenter;
import com.doglegs.core.rx.RxThrowableConsumer;
import com.doglegs.core.rx.RxUtils;

import javax.inject.Inject;

public class SplashPresenter extends RxPresenter<ISplashContract.IView> implements ISplashContract.IPresenter {


    private StartRetrofit startRetrofit;

    @Inject
    public SplashPresenter(StartRetrofit startRetrofit) {
        this.startRetrofit = startRetrofit;
    }

    /**
     * 刷新用户信息
     */
    @Override
    public void getUserInfo() {
        addSubscribe(
                startRetrofit.getUserInfo().compose(RxUtils.rxSchedulerHelper())
                        .compose(RxUtils.handleResult()).subscribe(loginInfo -> mView.refreshUserInfoSuccess(loginInfo),
                        new RxThrowableConsumer() {
                            @Override
                            public void handleThrowable(Throwable throwable) {
                                mView.refreshUserInfoError();
                            }

                            @Override
                            public void handleConnectException() {
                                mView.refreshUserInfoError();
                            }
                        })
        );
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
    @Override
    public void getVersionLast(int appTerminal, int versionCode, int appPlatform, int appChannel) {
        addSubscribe(
                startRetrofit.getVersionLast(appTerminal, versionCode, appPlatform, appChannel).compose(RxUtils.rxSchedulerHelper())
                        .compose(RxUtils.handleResult()).subscribe(updateInfo -> {
                    mView.getVersionLastSuccess(updateInfo);
                }, new RxThrowableConsumer() {
                    @Override
                    public void handleThrowable(Throwable throwable) {
                        mView.getVersionLastSuccess(null);
                    }

                    @Override
                    public void handleConnectException() {
                        mView.getVersionLastSuccess(null);
                    }
                })
        );
    }


}

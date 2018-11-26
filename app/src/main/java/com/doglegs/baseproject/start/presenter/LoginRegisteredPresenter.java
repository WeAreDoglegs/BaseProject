package com.doglegs.baseproject.start.presenter;

import android.content.Context;

import com.doglegs.baseproject.start.model.StartRetrofit;
import com.doglegs.baseproject.start.presenter.contract.ILoginRegisteredContract;
import com.doglegs.core.rx.RxPresenter;
import com.doglegs.core.rx.RxThrowableConsumer;
import com.doglegs.core.rx.RxUtils;

import javax.inject.Inject;

/**
 * 登录注册界面契约类
 */
public class LoginRegisteredPresenter extends RxPresenter<ILoginRegisteredContract.IView> implements ILoginRegisteredContract.IPresenter {

    private Context context;
    private StartRetrofit startRetrofit;

    @Inject
    public LoginRegisteredPresenter(Context context, StartRetrofit startRetorifit) {
        this.context = context;
        this.startRetrofit = startRetorifit;
    }

    /**
     * 解析剪切板信息
     *
     * @param code
     */
    @Override
    public void parseClipboard(String code) {
        addSubscribe(
                startRetrofit.parseClipboard(code).compose(RxUtils.rxSchedulerHelper())
                        .compose(RxUtils.handleResultCode()).subscribe(integer -> {

                }, new RxThrowableConsumer())
        );
    }
}

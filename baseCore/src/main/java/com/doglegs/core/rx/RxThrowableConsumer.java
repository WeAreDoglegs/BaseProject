package com.doglegs.core.rx;


import com.doglegs.core.base.IBaseView;
import com.doglegs.core.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/29 16:58
 * @describe :
 */

public class RxThrowableConsumer implements Consumer<Throwable> {

    private IBaseView view;

    public RxThrowableConsumer() {
    }

    public RxThrowableConsumer(IBaseView view) {
        this.view = view;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        if (throwable instanceof SocketTimeoutException || throwable instanceof SocketException) {
            ToastUtils.showShortToastSafe("网络连接超时");
            if (view != null) {
                view.onLoadError();
            }
            handleConnectException();
        } else if (throwable instanceof ConnectException || throwable instanceof UnknownHostException) {
            ToastUtils.showShortToastSafe("网络连接断开");
            if (view != null) {
                view.onLoadError();
            }
            handleConnectException();
        } else {
            handleThrowable(throwable);
        }
    }

    public void handleThrowable(Throwable throwable) {
        ToastUtils.showShortToastSafe(throwable.getMessage());
        if (view != null) {
            view.onLoadError();
        }
    }

    public void handleConnectException() {
    }

}

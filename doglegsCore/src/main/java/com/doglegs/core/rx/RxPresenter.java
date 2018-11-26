package com.doglegs.core.rx;


import com.doglegs.core.base.IBasePresenter;
import com.doglegs.core.base.IBaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<T extends IBaseView> implements IBasePresenter<T> {

    protected final String TAG = getClass().getSimpleName();

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

    /**
     * 将Rxjava操作对象添加到集合中统一管理
     *
     * @param subscription
     */
    public void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 将添加到集合中的Rxjava操作对象进行统一资源释放，防止内存泄漏
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 对Activity的View进行绑定
     *
     * @param view
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * 取消绑定View
     */
    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }

}

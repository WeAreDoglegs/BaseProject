package com.doglegs.core.rx;

import com.doglegs.core.base.IBasePresenter;
import com.doglegs.core.base.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected final String TAG = getClass().getSimpleName();

    private WeakReference<V> v;
    private CompositeDisposable mCompositeDisposable;

    public RxPresenter() {
    }

    /**
     * 将Rxjava操作对象添加到集合中统一管理
     */
    protected void addSubscribe(Disposable subscription) {
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
     */
    @Override
    public void attachV(V view) {
        v = new WeakReference<V>(view);
    }

    /**
     * 取消绑定View
     */
    @Override
    public void detachV() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    public V getV() {
        if (v == null || v.get() == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v.get();
    }

    @Override
    public boolean hasV() {
        return v != null && v.get() != null;
    }

}

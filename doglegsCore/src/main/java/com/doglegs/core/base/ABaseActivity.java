package com.doglegs.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doglegs.core.BaseApp;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.rx.RxPresenter;
import com.doglegs.core.rx.RxUtils;
import com.doglegs.core.utils.StatusBarUtila;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import qiu.niorgai.StatusBarCompat;

public abstract class ABaseActivity<T extends RxPresenter> extends AActivity {

    @Inject
    protected T mPresenter;

    private Unbinder mBind;

    protected boolean isTranslucentStatusBar = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getLayoutId());
        if (isTranslucentStatusBar) {
            StatusBarCompat.translucentStatusBar(this, isTranslucentStatusBar);
        }
        StatusBarUtila.darkMode(this, isDarkMode());
        mBind = ButterKnife.bind(this);
        BaseApp.addActivity(this);
        inject();
        registerDefaultEvent();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        initData();
    }

    protected void beforeSetContentView() {
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mBind.unbind();
        BaseApp.removeActivity(this);
        super.onDestroy();
    }

    /**
     * 設置是否深色樣式
     *
     * @return
     */
    protected boolean isDarkMode() {
        return false;
    }

    /**
     * 注册rxbus订阅事件
     */
    public void registerDefaultEvent() {
        mPresenter.addSubscribe(RxBus.getInstance().toFlowable(RxEvent.class).compose(RxUtils.rxSchedulerHelper())
                .subscribe(event -> handleDefaultEvent(event)));
    }

    protected abstract int getLayoutId();

    protected abstract void inject();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 处理默认订阅事件
     *
     * @param event
     */
    public abstract void handleDefaultEvent(RxEvent event);

    public void setTranslucentStatusBar(boolean translucentStatusBar) {
        isTranslucentStatusBar = translucentStatusBar;
    }
}

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

public abstract class ABaseActivity<P extends RxPresenter> extends AActivity {

    @Inject
    protected P mPresenter;
    private Unbinder mBind;
    protected boolean isTranslucentStatusBar = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApp.addActivity(this);
        beforeSetContentView();
        setContentView(getLayoutId());
        if (isTranslucentStatusBar) {
            StatusBarCompat.translucentStatusBar(this, isTranslucentStatusBar);
        }
        StatusBarUtila.darkMode(this, isDarkMode());
        mBind = ButterKnife.bind(this);
        beforeInject(savedInstanceState);
        inject();
        registerDefaultEvent();
        if (mPresenter != null) {
            mPresenter.attachV(this);
        }
        initView(savedInstanceState);
        initData();
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
        if (mPresenter != null)
            mPresenter.detachV();
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

    public void setTranslucentStatusBar(boolean translucentStatusBar) {
        isTranslucentStatusBar = translucentStatusBar;
    }

    /**
     * 注册rxbus订阅事件
     */
    public void registerDefaultEvent() {
        RxBus.getInstance().toFlowable(RxEvent.class).compose(RxUtils.rxSchedulerHelper())
                .compose(bindToLifecycle())
                .subscribe(event -> handleDefaultEvent(event));
    }

    /**
     * 处理默认订阅事件
     *
     * @param event
     */
    public abstract void handleDefaultEvent(RxEvent event);

    protected void beforeSetContentView() {
    }

    protected void beforeInject(Bundle savedInstanceState) {

    }
}

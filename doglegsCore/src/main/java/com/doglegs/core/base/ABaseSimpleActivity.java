package com.doglegs.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doglegs.core.BaseApp;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.rx.RxUtils;
import com.doglegs.core.utils.StatusBarUtila;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import qiu.niorgai.StatusBarCompat;

public abstract class ABaseSimpleActivity extends AActivity {

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
        BaseApp.addActivity(this);
        beforeInject(savedInstanceState);
        inject();
        registerDefaultEvent();
        initView(savedInstanceState);
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

    protected void beforeInject(Bundle savedInstanceState) {

    }

    /**
     * 处理默认订阅事件
     *
     * @param event
     */
    public abstract void handleDefaultEvent(RxEvent event);

}

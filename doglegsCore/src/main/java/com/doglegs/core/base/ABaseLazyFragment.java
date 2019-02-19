package com.doglegs.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.rx.RxPresenter;
import com.doglegs.core.rx.RxUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc : 懒加载fragment 配合FragmentStatePagerAdapter 使用
 */
public abstract class ABaseLazyFragment<T extends RxPresenter> extends RxFragment implements IBaseView {

    protected final String TAG = getClass().getSimpleName();
    protected View mView;
    @Inject
    public Context mContext;
    @Inject
    public RxPermissions mRxPermissions;
    @Inject
    public Activity mActivity;
    @Inject
    public T mPresenter;
    private Unbinder mUnBind;

    private boolean isViewCreated;
    private boolean isVisible;
    private boolean isDataLoaded;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inject();
        registerDefaultEvent();
        mView = View.inflate(mContext, getLayoutId(), null);
        mUnBind = ButterKnife.bind(this, mView);
        if (mPresenter != null) {
            mPresenter.attachV(this);
        }
        initView(savedInstanceState);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * fangment懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachV();
        }
        mUnBind.unbind();
        super.onDestroyView();
    }

    protected void lazyLoad() {
        if (isVisible && isViewCreated && !isDataLoaded) {
            isDataLoaded = true;
            initData();
        }
    }

    @Override
    public void onStartLoad() {
        ((AActivity) getActivity()).onStartLoad();
    }

    @Override
    public void onLoadSuccess() {
        ((AActivity) getActivity()).onLoadSuccess();
    }

    @Override
    public void onLoadError() {
        ((AActivity) getActivity()).onLoadError();
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
    public void handleDefaultEvent(RxEvent event) {

    }

}
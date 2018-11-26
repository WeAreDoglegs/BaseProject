package com.doglegs.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.rx.RxPresenter;
import com.doglegs.core.rx.RxUtils;
import com.doglegs.core.utils.BarUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc : 懒加载fragment 配合FragmentStatePagerAdapter 使用
 */
public abstract class ABaseFragment<T extends RxPresenter> extends Fragment implements IBaseView {

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        initInject();
        registerDefaultEvent();
        mView = View.inflate(mContext, getLayoutId(), null);
        mUnBind = ButterKnife.bind(this, mView);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnBind.unbind();
        super.onDestroyView();
    }

    public abstract int getLayoutId();

    public abstract void initInject();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onStartLoad() {
        ((ABaseActivity) getActivity()).onStartLoad();
    }

    @Override
    public void onLoadSuccess() {
        ((ABaseActivity) getActivity()).onLoadSuccess();
    }

    @Override
    public void onLoadError() {
        ((ABaseActivity) getActivity()).onLoadError();
    }

    /**
     * 注册rxbus订阅事件
     */
    public void registerDefaultEvent() {
        mPresenter.addSubscribe(RxBus.getInstance().toFlowable(RxEvent.class).compose(RxUtils.<RxEvent>rxSchedulerHelper())
                .subscribe(event -> handleDefaultEvent(event)));
    }

    /**
     * 处理默认订阅事件
     *
     * @param event
     */
    public void handleDefaultEvent(RxEvent event) {

    }

    /**
     * 沉浸式状态栏填充布局
     *
     * @param view
     */
    public void setStatusBarFillView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = BarUtils.getStatusBarHeight(getContext());
        view.setLayoutParams(layoutParams);
    }

    public void setSupportFragment(int contentId, Fragment fragment) {
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(contentId, fragment);
        beginTransaction.commitAllowingStateLoss();
    }
}

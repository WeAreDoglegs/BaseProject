package com.doglegs.core.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.doglegs.core.R;
import com.doglegs.core.dialog.LoadingProgressDialog;
import com.doglegs.core.utils.BarUtils;

import javax.inject.Inject;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/9/3 17:00
 * @describe :
 */


public abstract class AActivity extends AppCompatActivity implements IBaseView {

    protected final String TAG = getClass().getSimpleName();

    @Inject
    protected RxPermissions mRxPermissions;
    @Inject
    protected Activity mActivity;
    @Inject
    protected Context mContext;
    @Inject
    protected LoadingProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 显示加载进度dialog
     */
    public void showProgressDialog(String text) {
        if (progressDialog.isShowing()) return;
        progressDialog.show(text);
    }

    /**
     * 隐藏加载进度dialog
     */
    public void hideProgressDialog() {
        if (!progressDialog.isShowing()) return;
        progressDialog.dismiss();
    }

    public void setSupportFragment(int contentId, Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(contentId, fragment);
        beginTransaction.commitAllowingStateLoss();
    }

    public void switchSupportFragment(int containerId, Fragment from, Fragment to) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            beginTransaction.hide(from).add(containerId, to).commitAllowingStateLoss();
        } else {
            beginTransaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    /**
     * 沉浸式状态栏填充布局
     *
     * @param view
     */
    public void setStatusBarFillView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = BarUtils.getStatusBarHeight(this);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 打开界面
     */
    public void startActivity(Class<?> dest) {
        Intent intent = new Intent(mContext, dest);
        mContext.startActivity(intent);
    }

    /**
     * 打开界面
     */
    public void startActivity(Class<?> dest, Bundle bundle) {
        Intent intent = new Intent(mContext, dest);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    @Override
    public void onStartLoad() {
        showProgressDialog(getString(R.string.loaded_wait_moment));
    }

    @Override
    public void onLoadSuccess() {
        hideProgressDialog();
    }

    @Override
    public void onLoadError() {
        hideProgressDialog();
    }

}

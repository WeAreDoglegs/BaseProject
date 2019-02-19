package com.doglegs.baseproject.start.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.doglegs.base.manager.SpManager;
import com.doglegs.base.route.RouteMap;
import com.doglegs.baseproject.App;
import com.doglegs.baseproject.R;
import com.doglegs.baseproject.base.DogLegsBaseActivity;
import com.doglegs.baseproject.di.component.DaggerActivityComponent;
import com.doglegs.baseproject.di.module.ActivityModule;
import com.doglegs.baseproject.di.module.DialogModule;
import com.doglegs.baseproject.di.module.RetrofitModule;
import com.doglegs.baseproject.start.presenter.LoginRegisteredPresenter;
import com.doglegs.baseproject.start.presenter.contract.ILoginRegisteredContract;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.utils.StringUtils;

import butterknife.OnClick;

@Route(path = RouteMap.ACTIVITY_START_LOGIN_REGISTERED)
public class LoginRegisteredActivity extends DogLegsBaseActivity<LoginRegisteredPresenter> implements ILoginRegisteredContract.IView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_registered;
    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder().appComponent(App.sAppComponent).
                activityModule(new ActivityModule(this))
                .retrofitModule(new RetrofitModule())
                .dialogModule(new DialogModule(this)).build().inject(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        handleClipboardData();
    }

    private void handleClipboardData() {
        ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (mClipboardManager != null && mClipboardManager.hasPrimaryClip()) {
            ClipData mClipData = mClipboardManager.getPrimaryClip();
            ClipData.Item item = mClipData.getItemAt(0);
            if (item != null && item.getText() != null && !StringUtils.isEmptyString(item.getText().toString())) {
                String content = item.getText().toString();
                String[] arrayStr = content.split("\\$");
                if (content.startsWith("ycmc") && arrayStr != null && arrayStr.length >= 2) {
                    mPresenter.parseClipboard(arrayStr[1]);
                    SpManager.setActivateCode(arrayStr[1]);

                    // 清空剪切板
                    mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""));
                }
            }

        }
    }

    @OnClick({R.id.tv_registered, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_registered:
                ARouter.getInstance().build(RouteMap.ACTIVITY_START_REGISTERED).navigation();
                break;
            case R.id.tv_login:
                ARouter.getInstance().build(RouteMap.ACTIVITY_START_LOGIN).navigation();
                break;
        }
    }

    @Override
    public void handleDefaultEvent(RxEvent event) {
        //TODO RxBus通知 RxBus.getInstance().post(new RxEvent(RxEvent.EventType.SYSTEM_FINISH_LOGIN, null));

        if (event.getEventType() == RxEvent.EventType.SYSTEM_FINISH_LOGIN) {
            finish();
        }
        super.handleDefaultEvent(event);
    }
}

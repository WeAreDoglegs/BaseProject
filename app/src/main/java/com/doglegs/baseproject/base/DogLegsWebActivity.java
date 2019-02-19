package com.doglegs.baseproject.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.doglegs.base.bean.wrap.CommonWebWrap;
import com.doglegs.base.constants.Constants;
import com.doglegs.base.constants.IntentKey;
import com.doglegs.base.route.RouteMap;
import com.doglegs.base.view.XWebView;
import com.doglegs.baseproject.App;
import com.doglegs.baseproject.R;
import com.doglegs.baseproject.di.component.DaggerActivityComponent;
import com.doglegs.baseproject.di.module.ActivityModule;
import com.doglegs.baseproject.di.module.DialogModule;
import com.doglegs.baseproject.di.module.RetrofitModule;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.utils.LogUtils;
import com.doglegs.core.utils.StatusBarUtila;
import com.doglegs.core.utils.StringUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通用WEB页面
 */

@Route(path = RouteMap.ACTIVITY_COMMON_WEB)
public class DogLegsWebActivity extends DoglegsSimpleActivity {

    @BindView(R.id.view_fill)
    View viewFill;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.iv_toolbar_left_icon)
    ImageView ivToolbarLeftIcon;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.xwv_content)
    XWebView xwvContent;

    private CommonWebWrap commonWebWrap;

    @Override
    protected void onResume() {
        super.onResume();
        xwvContent.onResume();
    }

    @Override
    protected void onDestroy() {
        xwvContent.clearWebViewCache();
        xwvContent.onDestroy();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder().appComponent(App.sAppComponent).
                activityModule(new ActivityModule(this)).retrofitModule(new RetrofitModule())
                .dialogModule(new DialogModule(this)).build().inject(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setStatusBarFillView(viewFill);
        commonWebWrap = getIntent().getExtras().getParcelable(IntentKey.WEB_ACTIVITY_KEY_INFO);
        xwvContent.enableJavascriptInterface();
        xwvContent.setOnInvokeNativePushSelector((obj, jsInterface) -> {
            if (jsInterface.getType().equals(Constants.JsType.WEBVIEW)) {
                CommonWebWrap commonWebWrap = new CommonWebWrap();
                commonWebWrap.setTitle(jsInterface.getPayload().getTitle());
                commonWebWrap.setUrl(jsInterface.getPayload().getUrl());
                commonWebWrap.setIsShowToolbar(jsInterface.getPayload().getHideBar());
                commonWebWrap.setTheme(jsInterface.getPayload().isDark() ? 0 : 1);
                Bundle bundle = new Bundle();
                bundle.putParcelable(IntentKey.WEB_ACTIVITY_KEY_INFO, commonWebWrap);
                ARouter.getInstance().build(RouteMap.ACTIVITY_COMMON_WEB).with(bundle).navigation();
            }
        });
    }

    @Override
    public void initData() {
        if (commonWebWrap != null) {
            if (commonWebWrap.getTheme() == 1) {
                StatusBarUtila.darkMode(this, true);
                toolbar.setBackgroundColor(getResources().getColor(R.color.common_white));
                viewFill.setBackgroundColor(getResources().getColor(R.color.common_white));
                tvToolbarTitle.setTextColor(getResources().getColor(R.color.common_black));
                ivToolbarLeftIcon.setImageResource(R.mipmap.ic_arrow_left_black);
            } else {
                viewFill.setBackgroundColor(getResources().getColor(R.color.common_blue));
                tvToolbarTitle.setTextColor(getResources().getColor(R.color.common_white));
                ivToolbarLeftIcon.setImageResource(R.mipmap.ic_arrow_left_white);
            }
            if (!StringUtils.isEmptyString(commonWebWrap.getTitle())) {
                tvToolbarTitle.setText(commonWebWrap.getTitle());
            }
            if (commonWebWrap.getIsShowToolbar() == 1) {
                toolbar.setVisibility(View.GONE);
            }
            String url = null;
            if (!StringUtils.isEmptyString(commonWebWrap.getUrl())) {
                if (!commonWebWrap.getParams().isEmpty()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("?");
                    for (Map.Entry<String, String> entry : commonWebWrap.getParams().entrySet()) {
                        stringBuffer.append(entry.getKey() + "=");
                        stringBuffer.append(entry.getValue() + "&");
                    }
                    String params = stringBuffer.toString();
                    params = params.substring(0, params.length() - 1);
                    url = commonWebWrap.getUrl() + params;
                    xwvContent.loadUrl(url);
                } else {
                    url = commonWebWrap.getUrl();
                    xwvContent.loadUrl(url);
                }
            }
            LogUtils.i(url);
        }
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && xwvContent.canGoBack()) {
            xwvContent.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_toolbar_left_icon)
    public void onViewClicked() {
        if (xwvContent.canGoBack()) {
            xwvContent.goBack();
        } else {
            if (commonWebWrap.isRefreshHome()) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.REFRESH_HOME, null));
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (commonWebWrap.isRefreshHome()) {
            RxBus.getInstance().post(new RxEvent(RxEvent.EventType.REFRESH_HOME, null));
        }
        super.onBackPressed();
    }

    @Override
    public void handleDefaultEvent(RxEvent event) {
        if (event.getEventType() == RxEvent.EventType.JS_HOME) {
            ARouter.getInstance().build(RouteMap.ACTIVITY_MAIN_HOME).navigation();
            finish();
        }
        super.handleDefaultEvent(event);
    }
}

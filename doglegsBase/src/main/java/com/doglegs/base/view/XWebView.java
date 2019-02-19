package com.doglegs.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.doglegs.base.R;
import com.doglegs.base.R2;
import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.normal.JsInterface;
import com.doglegs.base.constants.Constants;
import com.doglegs.base.manager.SpManager;
import com.doglegs.base.route.RouteMap;
import com.doglegs.core.rx.RxBus;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.utils.LogUtils;
import com.doglegs.core.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XWebView extends RelativeLayout {

    @BindView(R2.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R2.id.wb_finance)
    WebView wbFinance;

    private OnInvokeNativePushSelector onInvokeNativePushSelector;

    public XWebView(Context context) {
        this(context, null);
    }

    public XWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_xweb, this);
        ButterKnife.bind(this, view);
        initWebSetting();
    }

    private void initWebSetting() {
        WebSettings webSettings = wbFinance.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            wbFinance.setWebContentsDebuggingEnabled(true);
        }
        wbFinance.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            //加载 HTTPS 页面时导致的问题
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });
        wbFinance.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pbLoading.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pbLoading.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pbLoading.setProgress(newProgress);//设置进度值
                }
            }
        });
    }

    public void onResume() {
        if (wbFinance != null) {
            wbFinance.onResume();
        }
    }

    public void onDestroy() {
        if (wbFinance != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = wbFinance.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wbFinance);
            }
            wbFinance.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wbFinance.getSettings().setJavaScriptEnabled(false);
            wbFinance.clearHistory();
            wbFinance.clearView();
            wbFinance.removeAllViews();
            wbFinance.destroy();
        }
        clearWebViewCache();
    }

    public void goBack() {
        wbFinance.goBack();
    }

    public boolean canGoBack() {
        return wbFinance.canGoBack();
    }

    public void loadUrl(String url) {
        wbFinance.loadUrl(url);
    }

    public void loadHtml(String html) {
        wbFinance.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    public void clearWebViewCache() {
        File file = getContext().getCacheDir().getAbsoluteFile();
        deleteFile(file);
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
        }
    }

    @SuppressLint("JavascriptInterface")
    public void enableJavascriptInterface() {
        wbFinance.addJavascriptInterface(this, "yrycJsInterface");
    }

    @JavascriptInterface
    public void invokeNativePushSelector(String obj) {
        LogUtils.i(obj);
        try {
            JsInterface jsInterface = new Gson().fromJson(obj, JsInterface.class);
            if (jsInterface.getType().equals(Constants.JsType.LOGIN)) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.SYSTEM_LOGIN_OUT, null));
            } else if (jsInterface.getType().equals(Constants.JsType.MAP)) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.JS_MAP, null));
            } else if (jsInterface.getType().equals(Constants.JsType.UPLOAD)) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.JS_CAPTURE, null));
            } else if (jsInterface.getType().equals(Constants.JsType.HOME)) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.JS_HOME, null));
            } else if (jsInterface.getType().equals(Constants.JsType.UPDATE_TOKEN)) {
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.JS_UPDATE_TOKEN, jsInterface.getPayload().getToken()));
            } else if (jsInterface.getType().equals(Constants.JsType.SET_TITLE)) {
                JsInterface.PayloadBean payload = jsInterface.getPayload();
                RxBus.getInstance().post(new RxEvent(RxEvent.EventType.JS_PAYLOAD, payload));
            }else {
                if (onInvokeNativePushSelector != null) {
                    onInvokeNativePushSelector.invokeNativePopSelector(obj,jsInterface);
                }
            }
        } catch (Exception exception) {
            ToastUtils.showShortToastSafe(exception.getMessage());
        }
    }

    @JavascriptInterface
    public void invokeNativePopSelector(String obj) {
        try {
            LogUtils.i(obj);
            JSONObject jsonObject = new JSONObject(obj);
            boolean root = jsonObject.getBoolean("root");
            if (root) {
                ARouter.getInstance().build(RouteMap.ACTIVITY_MAIN_HOME).navigation();
            }
            if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public String invokeNativeGetUser() {
        LoginInfo loginInfo = SpManager.getLoginInfo();
        return new Gson().toJson(loginInfo);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void evaluateJavascript(String functionName, ValueCallback<String> valueCallback) {
        wbFinance.evaluateJavascript(functionName, valueCallback);
    }

    public void evaluateJavascript(String functionName) {
        wbFinance.loadUrl(functionName);
    }

    public interface OnInvokeNativePushSelector {
        void invokeNativePopSelector(String obj, JsInterface jsInterface);
    }

    public void setOnInvokeNativePushSelector(OnInvokeNativePushSelector onInvokeNativePushSelector) {
        this.onInvokeNativePushSelector = onInvokeNativePushSelector;
    }
}

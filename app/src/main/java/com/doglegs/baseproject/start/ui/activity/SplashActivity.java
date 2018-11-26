package com.doglegs.baseproject.start.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.net.UpdateInfo;
import com.doglegs.base.bean.normal.DeviceConfig;
import com.doglegs.base.constants.AppChannel;
import com.doglegs.base.constants.AppPlatform;
import com.doglegs.base.constants.AppTerminal;
import com.doglegs.base.manager.SpManager;
import com.doglegs.base.route.RouteMap;
import com.doglegs.base.service.LocationService;
import com.doglegs.base.uitls.DateUtils;
import com.doglegs.baseproject.App;
import com.doglegs.baseproject.R;
import com.doglegs.baseproject.base.DogLegsBaseActivity;
import com.doglegs.baseproject.di.component.DaggerActivityComponent;
import com.doglegs.baseproject.di.module.ActivityModule;
import com.doglegs.baseproject.di.module.DialogModule;
import com.doglegs.baseproject.di.module.RetrofitModule;
import com.doglegs.baseproject.start.presenter.SplashPresenter;
import com.doglegs.baseproject.start.presenter.contract.ISplashContract;
import com.doglegs.baseproject.widget.dialog.ConfirmDialog;
import com.doglegs.core.constants.Config;
import com.doglegs.core.download.DownloadIntentService;
import com.doglegs.core.glide.GlideApp;
import com.doglegs.core.rx.RxUtils;
import com.doglegs.core.utils.AppUtils;
import com.doglegs.core.utils.LogUtils;
import com.doglegs.core.utils.PixelUtils;
import com.doglegs.core.utils.ToastUtils;
import com.doglegs.core.utils.Utils;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Flowable;

/**
 * 启动闪屏页
 */
@Route(path = RouteMap.ACTIVITY_START_SPLASH)
public class SplashActivity extends DogLegsBaseActivity<SplashPresenter> implements ISplashContract.IView {

    // 提高体验，进入主页至少要等待的时间2s
    public static final int MIN_WAIT_PREVIEW = 2000;
    @BindView(R.id.app_icon)
    AppCompatImageView appIcon;
    @Inject
    public ConfirmDialog mConfirmDialog;

    private int channelType = 1;

    @Override
    protected void beforeSetContentView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void inject() {
        DaggerActivityComponent.builder().appComponent(App.sAppComponent).
                activityModule(new ActivityModule(this)).retrofitModule(new RetrofitModule())
                .dialogModule(new DialogModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {
        GlideApp.with(this).load(R.mipmap.ic_launcher)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .apply(new RequestOptions().transform(new RoundedCorners(PixelUtils.dip2px(10))))
                .into(appIcon);
    }

    @Override
    protected void initData() {
        Flowable.timer(MIN_WAIT_PREVIEW, TimeUnit.MILLISECONDS).
                compose(RxUtils.rxSchedulerHelper()).subscribe(aLong -> {
            mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE
            ).subscribe(aBoolean -> {
                if (aBoolean) {
                } else {
                    ToastUtils.showShortToastSafe(getString(R.string.tip_permisions_error));
                }
                //保存用户设备信息
                saveDeviceConfig();
                //启动定位服务
                startService(new Intent(this, LocationService.class));

                //获取渠道类型
                String channelName = SpManager.getChannelName();
                for (AppChannel channelInfo : AppChannel.values()) {
                    if (channelInfo.getName().equals(channelName)) {
                        channelType = channelInfo.getType();
                        break;
                    }
                }

                //检测版本更新
                mPresenter.getVersionLast(AppTerminal.MERCHANT.getCode(),
                        AppUtils.getAppVersionCode(this), AppPlatform.ANDROID.getCode(), channelType);
            });

        });
    }

    /**
     * 保存设备信息
     */
    @SuppressLint("MissingPermission")
    private void saveDeviceConfig() {
        DeviceConfig deviceConfig = new DeviceConfig();
        deviceConfig.setBrand(Build.MANUFACTURER);
        deviceConfig.setModel(Build.MODEL);
        // 是否模拟器
        deviceConfig.setEmulator(AppUtils.isEmulator(mContext));
        // 固件版本
        deviceConfig.setFirmware(Build.VERSION.RELEASE);
        //sdk
        deviceConfig.setSdkVersion(Build.VERSION.SDK_INT);
        // 硬件架构
        deviceConfig.setAbi(AppUtils.getCpuAbi());
        //分辨率
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        deviceConfig.setResolution(width + "x" + height);
        //dpi
        int density = AppUtils.getDensityDpi(displayMetrics);
        deviceConfig.setDensity(String.valueOf(density));
        //version
        try {
            PackageInfo pi = Utils.getContext().getPackageManager().getPackageInfo(Utils.getContext().getPackageName
                    (), 0);
            deviceConfig.setVersion(pi.versionName);
            deviceConfig.setVersionCode(pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //mac
        try {
            WifiManager wifi = (WifiManager) Utils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String mac = info.getMacAddress();
            deviceConfig.setMac(mac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //imei simid
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        deviceConfig.setImei(telephonyManager.getDeviceId());
        deviceConfig.setSimId(telephonyManager.getSubscriberId());
        SpManager.saveDeviceInfo(deviceConfig);
    }

    /**
     * 刷新用户数据成功
     *
     * @param loginInfo
     */
    @Override
    public void refreshUserInfoSuccess(LoginInfo loginInfo) {
        LogUtils.i(TAG, new Gson().toJson(mLoginInfo));
        SpManager.removeLoginInfo();
        SpManager.saveLoginInfo(loginInfo);
        ARouter.getInstance().build(RouteMap.ACTIVITY_MAIN_HOME).navigation();
        finish();
    }

    @Override
    public void refreshUserInfoError() {
        ARouter.getInstance().build(RouteMap.ACTIVITY_START_LOGIN_REGISTERED).navigation();
        finish();
    }

    /**
     * 获取到版本更新信息
     *
     * @param updateInfo
     */
    @Override
    public void getVersionLastSuccess(UpdateInfo updateInfo) {
        if (updateInfo == null) {
            next();
            return;
        }
        if (updateInfo.getAppPlatform() != AppPlatform.ANDROID.getCode()
                && updateInfo.getAppChannel() != channelType) {
            ToastUtils.showShortToastSafe("版本更新信息异常");
            next();
            return;
        }
        if (updateInfo.getVersionCode() <= AppUtils.getAppVersionCode(this)) {
            next();
            return;
        }
        if (updateInfo.getForceUpdate() == 1) {
            //不强制更新
            mConfirmDialog.setTitle("发现新版本是否更新？");
            mConfirmDialog.setOnDialogListener(() -> {
                String apkPath = Config.DIR.FILE_DIR + DateUtils.format(System.currentTimeMillis()) + ".apk";
                DownloadIntentService.startDownloadService(mContext, App.sEnvironment.getHttpAddress(), updateInfo.getAppUrl(), apkPath);
                mConfirmDialog.dismiss();
            });
            mConfirmDialog.show();
            mConfirmDialog.setOnDismissListener(dialog -> {
                next();
            });
        } else {
            //强制更新
        }
    }

    public void next() {
        //检验token是否过期
//        if (mLoginInfo != null && StringUtils.isNotBlank(mLoginInfo.get().getToken())) {
//            mPresenter.getUserInfo();
//        } else {
            ARouter.getInstance().build(RouteMap.ACTIVITY_START_LOGIN_REGISTERED).navigation();
            finish();
//        }
    }
}

package com.doglegs.baseproject.base;

import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.doglegs.base.api.ICoreApi;
import com.doglegs.base.bean.net.LoginInfo;
import com.doglegs.base.bean.normal.DeviceConfig;
import com.doglegs.base.bean.point.DeviceOperate;
import com.doglegs.base.manager.SpManager;
import com.doglegs.base.route.RouteMap;
import com.doglegs.core.base.ABaseSimpleActivity;
import com.doglegs.core.rx.RxEvent;
import com.doglegs.core.rx.RxThrowableConsumer;
import com.doglegs.core.rx.RxUtils;
import com.doglegs.core.utils.AppUtils;

import java.util.UUID;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Retrofit;

public abstract class DoglegsSimpleActivity extends ABaseSimpleActivity {

    private Handler handler = new Handler();

    @Inject
    protected Retrofit retrofit;

    @Inject
    public Lazy<LoginInfo> mLoginInfo;

    //最后一次退出时间
    private long mLastPressBackTime;

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(() -> uploadDeviceOperate(false, 1), 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.postDelayed(() -> uploadDeviceOperate(false, 2), 2000);
    }

    @Override
    public void onBackPressed() {
//        if (this instanceof LoginRegisteredActivity) {
//            long pressBackTime = System.currentTimeMillis();
//            if (mLastPressBackTime == 0
//                    || pressBackTime - mLastPressBackTime > 3000) {
//                mLastPressBackTime = pressBackTime;
//                ToastUtils.showShortToastSafe(getString(R.string.tip_press_again_exit_app));
//                return;
//            }
//        }
        super.onBackPressed();
    }

    /**
     * 处理默认订阅事件
     *
     * @param event
     */
    public void handleDefaultEvent(RxEvent event) {
        if (event.getEventType() == RxEvent.EventType.SYSTEM_LOGIN_OUT) {
            SpManager.removeLoginInfo();
            SpManager.removeUmentToken();
            ARouter.getInstance().build(RouteMap.ACTIVITY_START_LOGIN_REGISTERED).navigation();
            finish();
        }
    }

    public void uploadDeviceOperate(boolean isResume, int operateType) {
        DeviceOperate deviceOperate = new DeviceOperate();
        DeviceConfig deviceInfo = SpManager.getDeviceInfo();
        deviceOperate.setAbi(deviceInfo.getAbi());
        deviceOperate.setAppVersion(deviceInfo.getVersion());
//            deviceOperate.setBusinessId();
//            deviceOperate.setBusinessParam();
        deviceOperate.setCurrentSystem(Build.VERSION.RELEASE);
        deviceOperate.setDensity(deviceInfo.getDensity());
        deviceOperate.setFirmware(deviceInfo.getFirmware());
//            deviceOperate.setIdfa();
        deviceOperate.setImei(deviceInfo.getImei());
//            deviceOperate.setIp();
        deviceOperate.setLat(SpManager.getLocationInfo().getLatitude());
        deviceOperate.setLng(SpManager.getLocationInfo().getLongitude());
        deviceOperate.setMac(deviceInfo.getMac());
//            deviceOperate.setNetworkType();
//            deviceOperate.setOpenudid();
        deviceOperate.setOperateType(operateType);
        deviceOperate.setPackageSource(SpManager.getChannelName());
        deviceOperate.setPhoneBrand(deviceInfo.getBrand());
        deviceOperate.setPhoneModel(deviceInfo.getModel());
        deviceOperate.setResolution(deviceInfo.getResolution());
        deviceOperate.setSdkVersion(deviceInfo.getSdkVersion());
        deviceOperate.setSimId(deviceInfo.getSimId());
        deviceOperate.setSystemType(1);//安卓
        deviceOperate.setVersionCode(deviceInfo.getVersionCode());
        String deviceOperateId = SpManager.getOperateId();
        if (isResume && AppUtils.isAppForeground(mContext) && TextUtils.isEmpty(deviceOperateId)) {
            deviceOperateId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            SpManager.saveOperateId(deviceOperateId);
            deviceOperate.setOperateId(deviceOperateId);
            retrofit.create(ICoreApi.class).deviceOperate(deviceOperate).compose(RxUtils.handleResultCode())
                    .compose(RxUtils.rxSchedulerHelper()).subscribe(integer -> {

            }, new RxThrowableConsumer());
        } else if (!isResume && !AppUtils.isAppForeground(mContext) && !TextUtils.isEmpty(deviceOperateId)) {
            SpManager.removeOperateId();
            deviceOperate.setOperateId(deviceOperateId);
            retrofit.create(ICoreApi.class).deviceOperate(deviceOperate).compose(RxUtils.handleResultCode())
                    .compose(RxUtils.rxSchedulerHelper()).subscribe(integer -> {

            }, new RxThrowableConsumer());
        }
    }


}

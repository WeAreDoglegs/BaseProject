package com.doglegs.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.doglegs.core.utils.LogUtils;
import com.google.gson.Gson;
import com.doglegs.base.bean.normal.LocationInfo;
import com.doglegs.base.manager.SpManager;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/14 15:13
 * @describe :
 */


public class LocationService extends Service implements AMapLocationListener {

    private static final String TAG = LocationService.class.getName();

    private AMapLocationClient mLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "LocationService onStartCommand");
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否单次定位
        locationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
//        locationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
//        locationOption.setInterval(5000);
        //缓存机制
        locationOption.setLocationCacheEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(locationOption);
        //启动定位
        mLocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
        mLocationClient = null;
        LogUtils.i(TAG, "LocationService onDestroy");
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setLongitude(aMapLocation.getLongitude());
            locationInfo.setLatitude(aMapLocation.getLatitude());
            locationInfo.setCity(aMapLocation.getCity());
            locationInfo.setCityCode(aMapLocation.getCityCode());
            locationInfo.setProvince(aMapLocation.getProvince());
            locationInfo.setAoiName(aMapLocation.getAoiName());
            locationInfo.setDistrict(aMapLocation.getDistrict());
            locationInfo.setAddress(aMapLocation.getAddress());
            locationInfo.setStreet(aMapLocation.getStreet());
            locationInfo.setStreetNum(aMapLocation.getStreetNum());
            locationInfo.setAddress(aMapLocation.getAddress());
            locationInfo.setTime(System.currentTimeMillis());
            locationInfo.setAdCode(aMapLocation.getAdCode());
            locationInfo.setDescription(aMapLocation.getDescription());
            SpManager.saveLocationInfo(locationInfo);
            LogUtils.i(TAG, "onLocationChanged location success--->" + new Gson().toJson(locationInfo));
        } else {
            LogUtils.e(TAG, "onLocationChanged location error!!!");
        }
    }

}

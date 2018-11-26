package com.doglegs.base.uitls;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.doglegs.base.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 高德地图工具类
 */
public class AMapHelper {

    /**
     * 设置
     */
    public static void setting(Context context, AMap amap) {
        UiSettings settings = amap.getUiSettings();
        // 设置地图logo显示
        settings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        settings.setLogoBottomMargin(-50);//隐藏logo
        // 设置缩放按钮
        settings.setZoomControlsEnabled(false);
        settings.setZoomGesturesEnabled(true);
        // 设置比例尺
        settings.setScaleControlsEnabled(false);
        // 设置地图是否可以倾斜
        settings.setTiltGesturesEnabled(false);
        settings.setRotateGesturesEnabled(true);
        //显示默认的定位按钮
        settings.setMyLocationButtonEnabled(false);
        //高德地图自定义样式
        //setMapCustomStyleFile(amap, Utils.getContext(), "amap_style_3.7.4_1.data");
        amap.setMinZoomLevel(Float.valueOf(5));
        amap.setMaxZoomLevel(Float.valueOf(20));
        //可触发定位并显示当前位置
        amap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        BitmapDescriptor locDescriptor = BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_map_direction));
        myLocationStyle.myLocationIcon(locDescriptor);
        amap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
    }

    /**
     * 设置地图自定义样式
     *
     * @param amap
     * @param context
     */
    private static void setMapCustomStyleFile(AMap amap, Context context, String styleName) {
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (!file.exists()) {
                file.createNewFile();
                outputStream = new FileOutputStream(file);
                outputStream.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        amap.setCustomMapStylePath(filePath + "/" + styleName);
        amap.showMapText(true);
        amap.setMapCustomEnable(true);
    }

    /**
     * 获取地图缩放比例
     */
    public static float getMapZoomLevel() {
        return 15f;
    }

    /**
     * 计算两个位置间的距离
     */
    public static float distance(double startLat, double startLng, double endLat, double endLng) {
        LatLng startPoint = new LatLng(startLat, startLng);
        LatLng endPoint = new LatLng(endLat, endLng);
        return AMapUtils.calculateLineDistance(startPoint, endPoint);
    }

    /**
     * 移动地图到目标中心点
     *
     * @param aMap
     * @param targetLat
     * @param targetLng
     */
    public static void moveMapToTarget(AMap aMap, double targetLat, double targetLng) {
        LatLng point = new LatLng(targetLat, targetLng);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                point, 15, 0, 0)));
    }

}

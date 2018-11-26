package com.doglegs.base.uitls;

import com.amap.api.location.DPoint;
import com.doglegs.base.bean.normal.LocationInfo;
import com.doglegs.base.manager.SpManager;


public class MapUtils {

    /**
     * 计算两点间距离
     *
     * @param lat 目标终点纬度
     * @param lon 目标终点经度
     * @return
     */
    public static String getDistance(double lat, double lon) {
        LocationInfo locationInfo = SpManager.getLocationInfo();
        if (lat != 0 && lon != 0 && locationInfo.getLatitude() != 0 && locationInfo.getLongitude() != 0) {
            DPoint start = new DPoint(locationInfo.getLatitude(), locationInfo.getLongitude());
            DPoint end = new DPoint(lat, lon);
            return getDisDsrc(calculateLineDistance(start, end));
        } else {
            return "";
        }
    }

    /**
     * 计算距离
     *
     * @param start 定位DPoint
     * @param end   目的地DPoint
     * @return 距离
     */
    private static double calculateLineDistance(DPoint start, DPoint end) {
        if ((start == null) || (end == null)) {
            throw new IllegalArgumentException("非法坐标值，不能为null");
        }
        double d1 = 0.01745329251994329D;
        double d2 = start.getLongitude();
        double d3 = start.getLatitude();
        double d4 = end.getLongitude();
        double d5 = end.getLatitude();
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

    private static String getDisDsrc(double dis) {
        if (dis <= 0) {
            return "";
        }
        String disStr = null;
        if (dis > 1000) {
            disStr = (double) Math.round(dis / 1000 * 10) / 10 + "km";
        } else {
            disStr = (int) dis + "m";
        }
        return disStr;
    }

}

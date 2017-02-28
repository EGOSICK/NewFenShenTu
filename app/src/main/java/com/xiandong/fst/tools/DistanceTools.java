package com.xiandong.fst.tools;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xiandong.fst.utils.StringUtil;

/**
 * Created by dell on 2017/1/16.
 */

public class DistanceTools {

    /**
     * 计算两坐标点位置
     *
     * @param p1j p1 经度
     * @param p1w p1 纬度
     * @param p2j p2 经度
     * @param p2w p2 纬度
     * @return 距离
     */
    public static double calculateDistance(double p1j, double p1w, double p2j, double p2w) {
        LatLng p1 = new LatLng(p1j, p1w);
        LatLng p2 = new LatLng(p2j, p2w);
        double dis = DistanceUtil.getDistance(p1, p2);
        return dis;
    }

    public static String changeDistance(String position, LatLng LocationPosition) {
        String distance = "0m";
        if (LocationPosition != null) {
            if (!StringUtil.isEmpty(position) && position.length() > 0) {
                String dis[] = position.split(";");
                if (dis.length == 2) {
                    double j = Double.parseDouble(dis[0]);
                    double w = Double.parseDouble(dis[1]);
                    double lj = LocationPosition.latitude;
                    double lw = LocationPosition.longitude;
                    double m = calculateDistance(j, w, lj, lw);
                    if (m / 1000 > 1) {
                        distance = StringUtil.doubleFormat(m / 1000) + "km";
                    } else {
                        distance = StringUtil.doubleFormat(m) + "m";
                    }
                }
            }
        }
        return distance;
    }

    public static double calculationDistance(String position, LatLng latLng) {
        if (latLng != null) {
            if (!StringUtil.isEmpty(position) && position.length() > 0){
                String dis[] = position.split(";");
                if (dis.length == 2) {
                    double j = Double.parseDouble(dis[0]);
                    double w = Double.parseDouble(dis[1]);
                    double lj = latLng.latitude;
                    double lw = latLng.longitude;
                    double m = calculateDistance(j, w, lj, lw);
                    return m;
                }
            }
        }
        return 0;
    }

}

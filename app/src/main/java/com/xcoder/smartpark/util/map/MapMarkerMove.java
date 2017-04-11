package com.xcoder.smartpark.util.map;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by xcoder_xz on 2016/12/25 0025.
 * 高德2地图，两个点之间的平滑移动
 */

public  class MapMarkerMove {


    /**
     * 循环设置经纬度，造成平滑移动的效果
     */
    private LatLng getLatLng(LatLng newLatLng, LatLng oldLatLng) {
        Double newLat = newLatLng.latitude;
        Double newLon = newLatLng.longitude;
        Double oldLat = oldLatLng.latitude;
        Double oldLon = oldLatLng.longitude;
        Double latScale;//纬度单位最小单位值（0.000001）
        Double lonScale;//经度最小单位值（0.000001）
        Boolean latLean = true;//true》newlat++。false》newlat--；
        Boolean lonLean = true;//true》newlon++.false>newlon--
        //先比较，两个坐标的大小,计算出小的经纬度的小数点的位数
        if (newLat > oldLat) {
            latLean = true;
            latScale = getDoubleScale(newLat);
        } else {
            latLean = false;
            latScale = getDoubleScale(oldLat);
        }
        if (newLon > oldLon) {
            lonLean = true;
            lonScale = getDoubleScale(newLon);
        } else {
            lonLean = false;
            lonScale = getDoubleScale(oldLon);
        }
        return null;
    }


    /**
     * 得到小数点后面的位数,
     * 的最小单位
     * 例(9.12313121>>0.00000001)
     */
    private Double getDoubleScale(Double number) {
        String numberStr = "0.";
        int numberLength = (String.valueOf(number).length()) - (String.valueOf(number).indexOf(".") + 1);

        for (int i = 0; i < numberLength - 1; i++) {
            numberStr += "0";
        }
        numberStr += "1";
        return Double.valueOf(numberStr);
    }


}

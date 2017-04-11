package com.xcoder.smartpark.util.map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.util.ToastUtil;

/**
 * Created by xcoder_xz on 2016/12/25 0025.
 * 得到用户的当前地点
 */

public class GetUserLocation {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;




    public GetUserLocation(){

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，
        // 启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        // mLocationOption.setInterval(1000);
        mLocationOption.setNeedAddress(true);// 可选，设置是否返回逆地理地址信息。默认是ture
        mLocationOption.setGpsFirst(false);// 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mLocationOption.setHttpTimeOut(20000);// 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        //   mLocationOption.setInterval(2000);// 可选，设置定位间隔。默认为2秒
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //初始化定位
        mLocationClient = new AMapLocationClient(SmartParkApplication.context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (null != aMapLocation) {
                MapUtils.getLocationStr(aMapLocation);
                doLocation.location(true);
            } else {
                ToastUtil.showToast("定位失败,请检查设置");
                doLocation.location(false);
            }
        }
    };

    public void setDoLocation(DoLocation doLocation) {
        this.doLocation = doLocation;
    }

    public DoLocation doLocation;

    public interface DoLocation {
        public void location(Boolean isGetLocation);
    }

}


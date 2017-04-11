package com.xcoder.smartpark.service.other;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.activity.other.BusDriverLocationActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.OtherCarMessageMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.map.GetUserLocation;
import com.xcoder.smartpark.util.map.LocationModel;
import com.xcoder.smartpark.view.other.BusDriverLocationView;
import com.xcoder.smartpark.view.other.BusDriverView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONObject;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 公车司机---车辆位置
 */

public class BusDriverLocationServcice {

    @Injection
    BusDriverLocationView bdlv;

    private LocationModel location;
    private AMap map;
    private int mapZoom = 19;// 地图缩放级别
    private Marker userMarker;
    private GeocodeSearch geocodeSearch;//反地理编译的

    private BusDriverLocationActivity bdlActivity;
    //可能需要上传的信息
    private double latTag = 0;//纬度
    private double lonTag = 0;//经度
    private String addrTag = "";//地址信息

    private AppUser userInfo;//用户信息
    private String CAR_ID;//车辆ID

    private OtherCarMessageMoudle otherCarMessageMoudle;//上个页面传入的车辆信息

    public void init(BusDriverLocationActivity bdlActivity) {
        this.bdlActivity = bdlActivity;

        Intent intent = bdlActivity.getIntent();
        Bundle bundle = intent.getExtras();
        otherCarMessageMoudle = (OtherCarMessageMoudle)bundle.getSerializable("carmsg");
        if(otherCarMessageMoudle == null) {
            LogUtils.d("未获取到传入的车辆信息");
            otherCarMessageMoudle = new OtherCarMessageMoudle();
        }

        userInfo = ConfigSP.getUserInfo();
        initview();
    }


    public void initview() {
        //地图处理
        map = bdlv.driver_loc_map.getMap();
        //地图加载完毕
        map.setOnMapLoadedListener(onMapLoaded);
    }

    /**
     * 地图加载完毕
     */
    AMap.OnMapLoadedListener onMapLoaded = new AMap.OnMapLoadedListener() {
        @Override
        public void onMapLoaded() {
            map.setOnMapClickListener(mapClick);
            //反坐标编译
            geocodeSearch = new GeocodeSearch(bdlActivity);
            geocodeSearch.setOnGeocodeSearchListener(geoCodeSearch);
            //加载完毕，应显示坐标
            getUserLocation();

        }
    };


    /**
     * 反地理编码，根据坐标得到地址
     * 与地图点击相对应
     */
    GeocodeSearch.OnGeocodeSearchListener geoCodeSearch = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            AppProgressDialog.dismiss(bdlActivity);
            if (i == 1000) {
                if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                        && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                    LatLonPoint latLonPoint = regeocodeResult.getRegeocodeAddress().getStreetNumber().getLatLonPoint();
                    userLocationOptions(latLonPoint.getLatitude(), latLonPoint.getLongitude(),
                            regeocodeResult.getRegeocodeAddress().getFormatAddress());
                } else {
                    ToastUtil.showToast("此地点不可用");
                }
            } else {
                ToastUtil.showToast("此地点不可用");
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    /**
     * 地图的点击监听
     */
    AMap.OnMapClickListener mapClick = new AMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            AppProgressDialog.showProgress(bdlActivity, "搜索中...");
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 500, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(query);
        }
    };

    /**
     * 设置用户的实时位置
     */
    public void getUserLocation() {
        AppProgressDialog.showProgress(bdlActivity, "定位当前位置中...");
        //开启一下定位，获得用户的位置
        GetUserLocation getUserLocation = new GetUserLocation();
        getUserLocation.setDoLocation(new GetUserLocation.DoLocation() {
            @Override
            public void location(Boolean isGetLocation) {
                AppProgressDialog.dismiss(bdlActivity);
                location = ConfigSP.getLocation();
                if (location != null || location.getLat() > 0) {
                    userLocationOptions(location.getLat(), location.getLon(), location.getAddress());

                } else {
                    ToastUtil.showToast("定位失败,请稍后再试");
                }
            }
        });
    }

    /**
     * 用户的坐标信息
     *
     * @param lat
     * @param lon
     * @param title
     */
    public void userLocationOptions(double lat, double lon, String title) {
        try {
            latTag = lat;
            lonTag = lon;
            addrTag = title;
            if (userMarker != null) {
                userMarker.remove();
            }
            String snippetStr = "";
            //对title进行处理
            if (title.indexOf(location.getProvince()) > -1) {
                snippetStr += location.getProvince();
                title = title.replace(location.getProvince(), "");
            }
            if (title.indexOf(location.getCity()) > -1) {
                snippetStr += location.getCity();
                title = title.replace(location.getCity(), "");
            }
            MarkerOptions userOption = new MarkerOptions();

            userOption.anchor(0.5f, 0.5f)
                    .position(new LatLng(lat, lon))
                    .title(snippetStr + "")
                    .snippet(title)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(
                                    bdlActivity.getResources(), R.drawable.app_mark_icon)));
            userMarker = map.addMarker(userOption);

            userMarker.showInfoWindow();
            // 移动到那个位置// 动画时间是1秒
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
        } catch (Exception e) {
            ToastUtil.showToast("定位错误，请稍后再试");
            e.printStackTrace();
        }
    }


    /**
     * 提交位置，所需要的经纬度，
     */
    public void doSubmit() {
        try {
            AppProgressDialog.showProgress(bdlActivity);
            Rest rest = new Rest("/appcar/carPosition.nla",userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
            rest.addParam("LONGITUDE",String.valueOf(lonTag));
            rest.addParam("LATITUDE",String.valueOf(latTag));
            rest.addParam("CAR_ID",otherCarMessageMoudle.getCAR_ID());
            rest.post("BusDriverLocationActivity", new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    AppProgressDialog.dismiss(bdlActivity);
                    ToastUtil.showToast("上传成功");
                    bdlActivity.finish();
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                    AppProgressDialog.dismiss(bdlActivity);
                    ToastUtil.showToast(msg);
                }

                @Override
                public void onError(Exception exception) {
                    AppProgressDialog.dismiss(bdlActivity);
                    ToastUtil.noNet();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Exception:"+e.getMessage());
        }
    }

}

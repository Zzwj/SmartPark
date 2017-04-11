package com.xcoder.smartpark.service.other;

import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.UseAuditCarLocationActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.OtherAuditCarLocationMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.map.LocationModel;
import com.xcoder.smartpark.view.other.UseAuditCarLocationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车审核---车辆位置
 */

public class UseAuditCarLocationService {
    @Injection
    UseAuditCarLocationView uaclv;
    private UseAuditCarLocationActivity uaclActivity;

    private AMap map;
    private LocationModel location;
    private float mapZoom = 19;// 地图缩放级别
    private Marker locationMarker;
    private AppUser userInfo;
    private List<OtherAuditCarLocationMoudle> listLocation = new ArrayList<OtherAuditCarLocationMoudle>();

    public void init(UseAuditCarLocationActivity uaclActivity) {
        this.uaclActivity = uaclActivity;
        location = ConfigSP.getLocation();
        userInfo = ConfigSP.getUserInfo();
        initview();
    }

    /**
     * 请求接口：获取所有车辆位置信息
     */
    public void initData() {
        Rest rest = new Rest("/appcar/getAllCarPosition.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.post("UseAuditCarLocationActivity", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    JSONArray dataset_line = jsonObject.getJSONArray("dataset_line");
                    List<OtherAuditCarLocationMoudle> listData = JSON.parseArray(dataset_line.toString(), OtherAuditCarLocationMoudle.class);
                    if (listData.size() > 0) {
                        carLocationOptions(listData);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                ToastUtil.noNet();
            }
        });
    }

    public void initview() {
        //地图处理
        map = uaclv.audit_location_map.getMap();
        //地图加载完毕
        map.setOnMapLoadedListener(onMapLoaded);
        //监听用户的手势，来动态设置地图的缩放级别
        map.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mapZoom = cameraPosition.zoom;
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
    }

    /**
     * 地图加载完毕
     */
    AMap.OnMapLoadedListener onMapLoaded = new AMap.OnMapLoadedListener() {
        @Override
        public void onMapLoaded() {
            //加载完毕，应显示坐标
            initData();
        }
    };

    /**
     * 生产用车的坐标信息
     */
    public void carLocationOptions(List<OtherAuditCarLocationMoudle> listdata) {
        try {
            if (listdata != null && listdata.size() > 0) {
                for (OtherAuditCarLocationMoudle locationMoudle : listdata) {
                    LatLng latLng = new LatLng(Double.valueOf(locationMoudle.getLATITUDE()), Double.valueOf(locationMoudle.getLONGITUDE()));
                    MarkerOptions carOption = new MarkerOptions().anchor(0.5f, 0.5f)
                            .position(latLng)
                            .snippet(locationMoudle.getADDRESS()+"")
                            .title(locationMoudle.getBRAND() + " " + locationMoudle.getCOLOR() + " " + locationMoudle.getNUMBER())
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(BitmapFactory.decodeResource(
                                            uaclActivity.getResources(), R.drawable.app_redcar_ico)));
                    locationMarker = map.addMarker(carOption);
                    locationMarker.showInfoWindow();

                    //        // 移动到那个位置// 动画时间是1秒
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom), 1000, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Exception:" + e.getMessage());
        }
    }
}

package com.xcoder.smartpark.service.bus;

import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusMyOrderLocationActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.map.GetUserLocation;
import com.xcoder.smartpark.util.map.LocationModel;
import com.xcoder.smartpark.util.map.MapMarkerMove;
import com.xcoder.smartpark.view.bus.BusMyOrderLocationView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xcoder_Xz on 2016/12/25 0025.
 * 我的预约的 已预约，实时位置
 */

public class BusMyOrderLocationService {
    @Injection
    BusMyOrderLocationView bmlv;

    private BusMyOrderLocationActivity bmlActivity;
    private AMap map;
    private LocationModel location;
    private float mapZoom = 19;// 地图缩放级别
    private Marker userMarker;
    private Marker locationMarker;
    private MapMarkerMove mmm;
    public Boolean isLoadMap = false;//用来判断，地图是否加载成功
    private AppUser userInfo;
    private String BUS_ID;

    public void init(BusMyOrderLocationActivity bmlActivity) {
        this.bmlActivity = bmlActivity;
        try {
            if (bmlActivity.getIntent() != null && bmlActivity.getIntent().getStringExtra("BUS_ID") != null) {
                BUS_ID = bmlActivity.getIntent().getStringExtra("BUS_ID");
            }
            userInfo = ConfigSP.getUserInfo();
            location = ConfigSP.getLocation();
            initview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 循环访问接口，5秒一次；
     */
    public void initCarLocation() {
        if (TextUtils.isEmpty(BUS_ID)) {
            return;
        }
        Rest rest = new Rest("/appbus/getBusPosition.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("BUS_ID", BUS_ID);
        rest.post("BusMyOrderLocationActivity", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    String ADDRESS = jsonObject.getJSONObject("dataset").getString("ADDRESS");
                    String LONGITUDE = jsonObject.getJSONObject("dataset").getString("LONGITUDE");
                    String LATITUDE = jsonObject.getJSONObject("dataset").getString("LATITUDE");
                    if (!TextUtils.isEmpty(LATITUDE) && !TextUtils.isEmpty(LONGITUDE))
                        carLocationOptions(Double.valueOf(LATITUDE), Double.valueOf(LONGITUDE), ADDRESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {

            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }

    public void initview() {
        //地图处理
        map = bmlv.bus_location_map.getMap();
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
            isLoadMap = true;
            //加载完毕，应显示坐标
            getUserLocation();
            startLoad();
        }
    };

    /**
     * 用户的坐标信息
     *
     * @param lat
     * @param lon
     * @param title
     */
    public void userLocationOptions(double lat, double lon, String title) {
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
//                .title(snippetStr + "")
//                .snippet(title)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(BitmapFactory.decodeResource(
                                bmlActivity.getResources(), R.drawable.app_mark_icon)));
        userMarker = map.addMarker(userOption);

        //  locationMarker.showInfoWindow();
        // 移动到那个位置// 动画时间是1秒
      //  map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
    }

    /**
     * 班车的坐标信息
     *
     * @param lat
     * @param lon
     * @param title
     */
    public void carLocationOptions(double lat, double lon, String title) {
        if (locationMarker != null) {

        }
        MarkerOptions carOption = new MarkerOptions().anchor(0.5f, 0.5f)
                .position(new LatLng(lat, lon))
//                .title("车辆位置")
//                .snippet(title)
                .icon(BitmapDescriptorFactory
                        .fromBitmap(BitmapFactory.decodeResource(
                                bmlActivity.getResources(), R.drawable.app_bus_car)));
        locationMarker = map.addMarker(carOption);
        //   locationMarker.showInfoWindow();
//        // 移动到那个位置// 动画时间是1秒
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
    }


    /**
     * 设置用户的实时位置
     */
    public void getUserLocation() {
        AppProgressDialog.showProgress(bmlActivity, "定位中...");
        //开启一下定位，获得用户的位置
        GetUserLocation getUserLocation = new GetUserLocation();
        getUserLocation.setDoLocation(new GetUserLocation.DoLocation() {
            @Override
            public void location(Boolean isGetLocation) {
                AppProgressDialog.dismiss(bmlActivity);
                location = ConfigSP.getLocation();
                if (location != null || location.getLat() > 0) {
                    userLocationOptions(location.getLat(), location.getLon(), location.getAddress());

                } else {
                    ToastUtil.showToast("定位失败,请稍后再试");
                }
            }
        });
    }

    Timer timer;
    TimerTask timerTask;

    /**
     * 开始循环加载
     */
    public void startLoad() {
        try {
            if (isLoadMap && timer == null) {
                timer = new Timer();
            }
            if (timerTask == null) {
                timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        initCarLocation();
                    }
                };
            }
            if (timer != null && timerTask != null)
                timer.schedule(timerTask, 1000, 5000);//循环访问服务器，拿车的坐标
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止循环加载
     */
    public void stopLoad() {
        try {
            if (timer != null && timerTask != null) {
                timer.cancel();
                timer = null;
                timerTask = null;
                OkHttpUtils.getInstance().cancelTag("BusMyOrderLocationActivity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

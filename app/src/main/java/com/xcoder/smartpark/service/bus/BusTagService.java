package com.xcoder.smartpark.service.bus;

import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.KeyBoardUtils;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusTagActivity;
import com.xcoder.smartpark.adapter.bus.BusTagMapAdapter;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.map.GetUserLocation;
import com.xcoder.smartpark.util.map.LocationModel;
import com.xcoder.smartpark.view.bus.BusTagView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/22 0022.
 * 班车---标注
 */

public class BusTagService {
    @Injection
    BusTagView btv;

    private BusTagActivity btActivity;
    private AMap map;
    private Marker locationMarker;//
    private int mapZoom = 17;// 地图缩放级别
    private LocationModel location;//位置的信息
    private BusTagMapAdapter btmAdapter;
    private boolean Setflag = true;// 是否点击listview进行赋值
    private List<Tip> listTip = new ArrayList<Tip>();//poi搜素的数据
    private GeocodeSearch geocodeSearch;//反地理编译的
    private double latTag = 0;//纬度
    private double lonTag = 0;//经度
    private String addrTag = "";//地址信息

    public void init(BusTagActivity btActivity) {
        this.btActivity = btActivity;
        location = ConfigSP.getLocation();
        initView();
    }

    public void initView() {
        //地图处理
        map = btv.bus_tag_map.getMap();
        //地图加载完毕
        map.setOnMapLoadedListener(onMapLoaded);
    }

    /**
     * 地图加载完毕，所要处理
     */
    AMap.OnMapLoadedListener onMapLoaded = new AMap.OnMapLoadedListener() {

        @Override
        public void onMapLoaded() {
            map.setOnMapClickListener(mapClick);
            //监听输入
            btv.bus_tag_et.addTextChangedListener(textWatcher);
            //配置适配器
            btmAdapter = new BusTagMapAdapter(btActivity);
            btv.bus_tag_listview.setAdapter(btmAdapter);
            btv.bus_tag_listview.setOnItemClickListener(listViewItem);
            //反坐标编译
            geocodeSearch = new GeocodeSearch(btActivity);
            geocodeSearch.setOnGeocodeSearchListener(geoCodeSearch);
            //infowindow是的自定义
            //  map.setInfoWindowAdapter(infoWindow);
            //加载完毕，应显示坐标
            doUserLoca();
        }
    };

    /**
     * infowindow是的自定义
     */
    AMap.InfoWindowAdapter infoWindow = new AMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };

    /**
     * 反地理编码，根据坐标得到地址
     * 与地图点击相对应
     */
    GeocodeSearch.OnGeocodeSearchListener geoCodeSearch = new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            AppProgressDialog.dismiss(btActivity);
            if (i == 1000) {
                if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                        && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                    LatLonPoint latLonPoint = regeocodeResult.getRegeocodeAddress().getStreetNumber().getLatLonPoint();
                    setMarker(latLonPoint.getLatitude(), latLonPoint.getLongitude(),
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
            AppProgressDialog.showProgress(btActivity, "搜索中...");
            // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 500, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(query);
        }
    };

    /**
     * POI搜索的条目点击
     */
    AdapterView.OnItemClickListener listViewItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Tip tip = listTip.get(position);

            if (tip.getPoint() != null) {
                setMarker(tip.getPoint().getLatitude(), tip.getPoint()
                        .getLongitude(), tip.getDistrict() + tip.getName());
            }
            //   Setflag = false;
            btv.bus_tag_et.setText(tip.getName());
            // 隐藏listview
            btv.bus_tag_listview.setVisibility(View.GONE);
            KeyBoardUtils.hideSoftInput(btActivity);
            /*setMarker(tip.getPoint().getLatitude(),tip.getPoint().getLongitude(),"当前位置", snippet);*/
        }
    };

    /**
     * Edittext框输入监听
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            // 表示最终内容
            String etText = btv.bus_tag_et.getText().toString().trim();
            if (!Utils.isEmpty(etText)) {
                //第二个参数默认代表全国，也可以为城市区号
                //输入自动提示
                InputtipsQuery inputquery = new InputtipsQuery(etText, location.getCityCode());
                inputquery.setCityLimit(false);
                Inputtips inputTips = new Inputtips(btActivity, inputquery);
                inputTips.setInputtipsListener(inputSearch);
                inputTips.requestInputtipsAsyn();
            } else {
                btv.bus_tag_listview.setVisibility(View.GONE);
            }
        }
    };

    /**
     * @see_输入框的监听后的数据
     */
    Inputtips.InputtipsListener inputSearch = new Inputtips.InputtipsListener() {
        @Override
        public void onGetInputtips(List<Tip> list, int i) {
            if (i == 1000) {
                listTip = list;
                ViewGroup.LayoutParams layoutParams = btv.bus_tag_listview.getLayoutParams();
                // 如果焦点再edittext上面，就显示
                if (!Setflag) {
                    Setflag = true;
                    return;
                }
                if (btv.bus_tag_listview.getVisibility() == View.GONE
                        && btv.bus_tag_et.isFocused()) {
                    btv.bus_tag_listview.setVisibility(View.VISIBLE);
                }
                if (list != null && list.size() > 5) {
                    layoutParams.height = 500;
                } else {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                btv.bus_tag_listview.setLayoutParams(layoutParams);
                btmAdapter.setListData(list);
            }
        }
    };

    /**
     * 提交标注
     */
    public void doSubmit() {
        if (latTag != 0 && lonTag != 0) {
            //ToastUtil.showToast(latTag + "-" + lonTag + location.getAddress());
            AppProgressDialog.showProgress(btActivity);
            AppUser appUser = ConfigSP.getUserInfo();
            Rest rest = new Rest("/appuser/setPosition.nla", appUser.getUSER_ID(), appUser.getUSER_TOKEN());
            rest.addParam("LONGITUDE", String.valueOf(lonTag));
            rest.addParam("LATITUDE", String.valueOf(latTag));
            rest.addParam("ADDRESS", addrTag);
            rest.post(new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    AppProgressDialog.dismiss(btActivity);
                    ToastUtil.showToast("标注成功");
                    btActivity.finish();
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                    AppProgressDialog.dismiss(btActivity);
                    ToastUtil.showToast(msg);
                }

                @Override
                public void onError(Exception exception) {
                    AppProgressDialog.dismiss(btActivity);
                    LogUtils.e("exception:" + exception.getMessage());
                    ToastUtil.showToast("网络繁忙，请稍后再试");
                }
            });


        } else {
            ToastUtil.showToast("位置信息为空，请点击地图");
        }


    }

    /**
     * 设置用户当前的地点
     */
    public void doUserLoca() {
        AppProgressDialog.showProgress(btActivity, "定位当前位置中...");
        //开启一下定位，获得用户的位置
        GetUserLocation getUserLocation = new GetUserLocation();
        getUserLocation.setDoLocation(new GetUserLocation.DoLocation() {
            @Override
            public void location(Boolean isGetLocation) {
                AppProgressDialog.dismiss(btActivity);
                location = ConfigSP.getLocation();
                if (location == null || location.getLat() < 0) {
                } else {
                    setMarker(location.getLat(), location.getLon(), location.getAddress());
                }
            }
        });

    }

    /**
     * 设置标记点，并动画移过去 lat:纬度 lon:经度
     */
    public void setMarker(double lat, double lon, String title) {
        this.latTag = lat;
        this.lonTag = lon;
        this.addrTag = title;
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
        try {
            if (locationMarker != null) {
                // 去除以前的标记
                locationMarker.remove();
            }
            // 设置标记
            locationMarker = map.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .position(new LatLng(lat, lon))
                    .title(snippetStr + "")
                    .snippet(title)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(
                                    btActivity.getResources(), R.drawable.app_mark_icon))));

            locationMarker.showInfoWindow();
            // 移动到那个位置// 动画时间是1秒
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

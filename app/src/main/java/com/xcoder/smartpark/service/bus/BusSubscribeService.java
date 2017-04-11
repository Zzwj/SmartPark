package com.xcoder.smartpark.service.bus;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.xcoder.lib.adapter.recyclerview.RvItemListener;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusSubscribeActivity;
import com.xcoder.smartpark.adapter.bus.BusSubShiftAdapter;
import com.xcoder.smartpark.adapter.bus.BusSubSiteAdapter;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHomeMoudel;
import com.xcoder.smartpark.moudel.BusLineMoudel;
import com.xcoder.smartpark.moudel.BusMessageMoudle;
import com.xcoder.smartpark.moudel.BusSubSiteMoudel;
import com.xcoder.smartpark.moudel.BusTemPoraryMoudel;
import com.xcoder.smartpark.moudel.BusWrokMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.outside.pickerview.TimePicker;
import com.xcoder.smartpark.outside.pickerview.TimePickerView;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.map.GetUserLocation;
import com.xcoder.smartpark.util.map.LocationModel;
import com.xcoder.smartpark.view.bus.BusSubscribeView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.xcoder.smartpark.widget.dialog.BusSubDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xcoder_xz on 2016/12/21 0021.
 * 班车---预约--
 */

public class BusSubscribeService {

    private BusSubscribeActivity bsActivity;
    @Injection
    BusSubscribeView bsv;

    public TimePicker timePicker;
    private AMap map;
    private LocationModel location;//位置的信息
    private float mapZoom = 17;// 地图缩放级别
    private Marker locationMarker;//
    private Marker busMarker;
    private String snippet = "userMarker";//当前位置的marker命名
    private List<BusSubSiteMoudel> listSite = new ArrayList<BusSubSiteMoudel>();//站点的数据
    private BusSubShiftAdapter bsShiftAdapter;//时间段的适配器
    private BusSubSiteAdapter bsSiteAdapter;//站点的适配器
    public Intent intent;//传过来的班车线路id
    private AppUser userInfo;
    private List<BusMessageMoudle> listTime = new ArrayList<BusMessageMoudle>();//本线路的班车信息列表，发车时间为班次信息
    public Boolean isLoadMap = false;//用来判断，地图是否加载成功

    public String Flag = "";//0:上班 1：回家 2：临时 根据传入对象判断
    public String str_dialog_addre;//上车地点
    public String str_dialog_time;//时间
    public String dayTime;//选择预约时间

    private BusMessageMoudle busMessageMoudle;//班车信息对象,点击时获取
    private BusSubSiteMoudel busSubSiteMoudel;//站点，调用接口获取
    private BusLineMoudel busLineMoudel;//线路对象，上个页面传入
    private String TYPE = "";//0:预约当天 1:长期预约,上个页面传入

    private String BUS_ID = "";//公车id，


    public void init(BusSubscribeActivity busSubscribeActivity) {
        this.bsActivity = busSubscribeActivity;
        try {
            location = ConfigSP.getLocation();
            userInfo = ConfigSP.getUserInfo();

            //获取当前时间第二天日期，进入预约界面，默认是第二天的日期，调用接口需要传入这个时间，但是用户可以改成第一天
            //20170318 郭骏确认默认获取第一天的时间
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date date = calendar.getTime();
            dayTime = sf.format(date);
            bsv.bus_sub_time.setText(dayTime);

            initView();
            initViewData();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 给View赋值
     * 目前班车类型是一致的，暂时使用班车基类BusMoudel
     */
    public void initViewData() {
        try {
            if (intent != null) {
                if (intent.getSerializableExtra("BusHomeMoudel") != null) {
                    //回家
                    Flag = "1";
                    busLineMoudel = (BusHomeMoudel) intent.getSerializableExtra("BusHomeMoudel");
                    listTime = busLineMoudel.getRESULT();//班车时间段
                } else if (intent.getSerializableExtra("BusTemPoraryMoudel") != null) {
                    //临时
                    Flag = "2";
                    busLineMoudel = (BusTemPoraryMoudel) intent.getSerializableExtra("BusTemPoraryMoudel");
                    listTime = busLineMoudel.getRESULT();//班车时间段
                } else if (intent.getSerializableExtra("BusWrokMoudel") != null) {
                    //上班
                    Flag = "0";
                    busLineMoudel = (BusWrokMoudel) intent.getSerializableExtra("BusWrokMoudel");
                    listTime = busLineMoudel.getRESULT();//班车时间段
                }

                if (intent.getStringExtra("TYPE").equals("0")) {
                    //预约当天
                    TYPE = "0";
                    bsv.bus_sub_timepicker_rl.setVisibility(View.VISIBLE);
                    bsv.bus_sub_timepicker_view.setVisibility(View.VISIBLE);
                } else if (intent.getStringExtra("TYPE").equals("1")) {
                    //长期预约
                    TYPE = "1";
                    bsv.bus_sub_timepicker_rl.setVisibility(View.GONE);
                    bsv.bus_sub_timepicker_view.setVisibility(View.GONE);
                }
            }

            if (busLineMoudel != null) {
                bsv.bus_sub_name.setText(busLineMoudel.getLINE_NAME());//名字
                bsv.bus_sub_path.setText(busLineMoudel.getSTART_STATION() + "-" + busLineMoudel.getEND_STATION());//线路

                //时间段的初始化
                bsShiftAdapter.setData(listTime);
                bsShiftAdapter.notifyDataSetChanged();

                if (listTime != null && listTime.size() > 0) {
                    //默认班次选中第一条
                    bsShiftAdapter.indexPos = 0;
                    bsShiftAdapter.notifyDataSetChanged();
                    busMessageMoudle = listTime.get(0);
                    str_dialog_time = listTime.get(0).getSEND_TIME();
                    //默认设置班车位置
//                    if (!TextUtils.isEmpty(listTime.get(0).getLATITUDE()) && !TextUtils.isEmpty(listTime.get(0).getLONGITUDE()))
//                        setBusMarker(Double.valueOf(listTime.get(0).getLATITUDE()), Double.valueOf(listTime.get(0).getLONGITUDE()), listTime.get(0).getADDRESS());
                    if (!TextUtils.isEmpty(listTime.get(0).getBUS_ID()))
                        BUS_ID = listTime.get(0).getBUS_ID();

                    //班车信息显示,点击班次时切换
                    bsv.bus_sub_busmsg.setVisibility(View.VISIBLE);
                    bsv.bus_sub_det.setText(busMessageMoudle.getBRAND() + " " + busMessageMoudle.getNUMBER() + " " + busMessageMoudle.getCOLOR() + " 预约"+busMessageMoudle.getYYNUM()+"人 "+"上车"+busMessageMoudle.getUPNUM()+"人"+" 剩余座位"+(Integer.valueOf(busMessageMoudle.getBUS_NUM()) - Integer.valueOf(busMessageMoudle.getYYNUM())));//班车详情
                    bsv.bus_sub_user.setText(busMessageMoudle.getDRIVER_NAME());//司机名字
                    bsv.bus_sub_phone.setText(busMessageMoudle.getDRIVER_TEL());//司机电话

                    getListSite(busLineMoudel.getBUS_LINE_ID(), busMessageMoudle.getBUS_ID(), dayTime);//获取站点
                }else{
                    LogUtils.d("lastTime == null || lastTime.size==0");
                    ToastUtil.showToast("该线路下没有车，暂时不能预约");
                    bsv.bus_sub_time_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.showToast("该线路下没有车，暂时不能选择时间");
                            return;
                        }
                    });
                    bsv.bus_sub_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ToastUtil.showToast("该线路下没有车，暂时不能提交预约");
                            return;
                        }
                    });
                }


            } else {
                ToastUtil.showToast("网络繁忙，请稍后再试");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据线路ID、和班车ID、当前时间查询站点信息
     *
     * @param BUS_LINE_ID
     * @param BUS_ID
     */
    public void getListSite(String BUS_LINE_ID, String BUS_ID, String dayTime) {
        try {
            AppProgressDialog.showProgress(bsActivity);
            Rest rest = new Rest("/appbus/loadBusSite.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
            rest.addParam("BUS_LINE_ID", BUS_LINE_ID);
            rest.addParam("BUS_ID", BUS_ID);
            rest.addParam("RESERVE_DAY", dayTime);
            rest.post("BusSubscribeActivity", new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    AppProgressDialog.dismiss(bsActivity);
//                    try {
//                        listSite = JSON.parseArray(jsonObject.getJSONArray("dataset_line").toString(), BusSubSiteMoudel.class);
//                        bsSiteAdapter.setData(listSite);
//                        bsSiteAdapter.notifyDataSetChanged();
//                    } catch (Exception e) {
//                        onError(e);
//                        e.printStackTrace();
//                        LogUtils.e("Exception=" + e);
//                    }


                    try {
                        JSONObject dataset = jsonObject.getJSONObject("dataset");
                        listSite = JSON.parseArray(dataset.getJSONArray("result").toString(), BusSubSiteMoudel.class);
                        bsSiteAdapter.setData(listSite);
                        bsSiteAdapter.notifyDataSetChanged();

                        JSONObject info = dataset.getJSONObject("info");

                        BusMessageMoudle bmm = JSON.parseObject(info.toString(),BusMessageMoudle.class);

                        //班车信息显示,点击班次时切换
                        bsv.bus_sub_busmsg.setVisibility(View.VISIBLE);
                        bsv.bus_sub_det.setText(bmm.getBRAND() + " " + bmm.getNUMBER() + " " + bmm.getCOLOR() + " 预约"+bmm.getYYNUM()+"人 "+"上车"+bmm.getUPNUM()+"人"+" 剩余座位"+(Integer.valueOf(bmm.getBUS_NUM()) - Integer.valueOf(bmm.getYYNUM())));//班车详情
                        bsv.bus_sub_user.setText(bmm.getDRIVER_NAME());//司机名字
                        bsv.bus_sub_phone.setText(bmm.getDRIVER_TEL());//司机电话


                    } catch (Exception e) {
                        onError(e);
                        e.printStackTrace();
                        LogUtils.e("Exception=" + e);
                    }
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                    ToastUtil.showToast(msg);
                    AppProgressDialog.dismiss(bsActivity);
                }

                @Override
                public void onError(Exception exception) {
                    ToastUtil.noNet();
                    AppProgressDialog.dismiss(bsActivity);
                }
            });
        } catch (Exception e) {
            LogUtils.e("e:",e);
        }
    }


    public void initView() {
        //RecyclerView处理
        LinearLayoutManager layoutManagerShift = new LinearLayoutManager(bsActivity);
        layoutManagerShift.setOrientation(OrientationHelper.HORIZONTAL);//水平
        bsv.bus_sub_shift_rl.setLayoutManager(layoutManagerShift);
        LinearLayoutManager layoutManagerSite = new LinearLayoutManager(bsActivity);
        layoutManagerSite.setOrientation(OrientationHelper.HORIZONTAL);//水平
        bsv.bus_sub_site_rl.setLayoutManager(layoutManagerSite);
        //adapter
        bsShiftAdapter = new BusSubShiftAdapter();
        bsv.bus_sub_shift_rl.setAdapter(bsShiftAdapter);

        //item条目点击事件
        //班次点击事件
        bsShiftAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int postion) {
                try {
                    //得到班次信息
                    str_dialog_time = listTime.get(postion).getSEND_TIME();
                    busMessageMoudle = listTime.get(postion);
                    bsShiftAdapter.indexPos = postion;
                    bsShiftAdapter.notifyDataSetChanged();

                    //班车信息显示，点击时切换(这部分切换应该用不上了 20170318)
                    bsv.bus_sub_busmsg.setVisibility(View.VISIBLE);
                    bsv.bus_sub_det.setText(busMessageMoudle.getBRAND() + " " + busMessageMoudle.getNUMBER() + " " + busMessageMoudle.getCOLOR());//班车详情
                    bsv.bus_sub_user.setText(busMessageMoudle.getDRIVER_NAME());//司机名字
                    bsv.bus_sub_phone.setText(busMessageMoudle.getDRIVER_TEL());//司机电话

                    //根据班车的班次时间来设置班车位置
                    if (!TextUtils.isEmpty(listTime.get(postion).getBUS_ID())) {
                        //切换班次时间时需要清除marker
                        if (busMarker != null) {
                            // 去除以前的标记
                            busMarker.remove();
                            map.clear();
                        }
                        BUS_ID = listTime.get(postion).getBUS_ID();
                    }

//                  setBusMarker(Double.valueOf(listTime.get(postion).getLATITUDE()), Double.valueOf(listTime.get(postion).getLONGITUDE()), listTime.get(postion).getADDRESS());

                    //点击班次信息，需要重新获取站点信息，站点信息带有人数
                    //目前没有获取到站点的有效人数
                    getListSite(busLineMoudel.getBUS_LINE_ID(), busMessageMoudle.getBUS_ID(), dayTime);


                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e("Exception:" + e.getMessage());
                }
            }
        });

        //站点的
        bsSiteAdapter = new BusSubSiteAdapter();
        bsv.bus_sub_site_rl.setAdapter(bsSiteAdapter);
        bsSiteAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
            @Override
            public void onItemClickListener(View view, int postion) {
                //         BusSubSiteMoudel item = bsSiteAdapter.getItem(postion);

                //得到班次的  站点
                str_dialog_addre = listSite.get(postion).getSITE_NAME();
                busSubSiteMoudel = listSite.get(postion);
                bsSiteAdapter.indexPos = postion;
                bsSiteAdapter.notifyDataSetChanged();
            }
        });
        //地图处理
        map = bsv.bus_sub_mapview.getMap();
        map.setOnMapLoadedListener(onMapLoaded);
        map.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        bsv.bus_sub_mapview.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        bsv.bus_sub_scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        break;
                    default:
                        bsv.bus_sub_mapview.requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
        });
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

            startLoad();
        }
    };

    /**
     * 设置用户当前的地点
     */
    public void doUserLoca() {
       // AppProgressDialog.showProgress(bsActivity, "定位当前位置中...");
        //开启一下定位，获得用户的位置
        GetUserLocation getUserLocation = new GetUserLocation();
        getUserLocation.setDoLocation(new GetUserLocation.DoLocation() {
            @Override
            public void location(Boolean isGetLocation) {
                AppProgressDialog.dismiss(bsActivity);
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
        try {
            if (locationMarker != null) {
                // 去除以前的标记
                locationMarker.remove();
            }
            // 设置标记
            locationMarker = map
                    .addMarker(new MarkerOptions()
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(BitmapFactory.decodeResource(
                                            bsActivity.getResources(), R.drawable.app_mark_icon)))
                            .position(new LatLng(lat, lon)));

            // 移动到那个位置// 动画时间是1秒
              map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 选择时间
     */
    public void selectTime() {
        //时间选择器
        timePicker = new TimePicker(bsActivity, TimePickerView.Type.YEAR_MONTH_DAY);
        timePicker.setCyclic(false);
        timePicker.setCancelable(true);
        //timePicker.setRange(2017,2018);
        // 时间选择后回调
        timePicker.setOnTimeSelectListener(new TimePicker.OnTimeSelectListener() {


            @Override
            public void onTimeSelect(Date date) {
                Date dateNow = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                int flag = sf.format(date).compareTo(sf.format(dateNow));

                if (flag < 0) {//当天及当天之后，<0就是在日期之前
                    //用户选择的时间小于当前时间，则提醒，不能设置
                    ToastUtil.showToast("您选择的时间不能小于当前时间");
                } else if ((long) ((date.getTime() - dateNow.getTime()) / (1000 * 60 * 60 * 24) + 0.5) > 7) {
                    ToastUtil.showToast("只能预约七天内的车，请重新选择");
                } else {
                    //用户选择的时间大于当前
                    // 时间，则可以设置
                    dayTime = sf.format(date);
                    bsv.bus_sub_time.setText(dayTime);
                    getListSite(busLineMoudel.getBUS_LINE_ID(), busMessageMoudle.getBUS_ID(), dayTime);
                }


            }
        });
        timePicker.show();
    }

    /**
     * 数据提交
     */
    public void doSubmit() {
        //对数据判断
        if (TextUtils.isEmpty(str_dialog_time)) {
            ToastUtil.showToast("请选择班次时间");
            return;
        }
        if (TextUtils.isEmpty(str_dialog_addre)) {
            ToastUtil.showToast("请选择班次站点");
            return;
        }
        if (TextUtils.isEmpty(dayTime) && TYPE.equals("0")) {
            ToastUtil.showToast("请选择预约时间");
            return;
        }
        try {
            BusSubDialog busSubDialog = new BusSubDialog(bsActivity) {

                @Override
                public void doSubmit(View view) {

                    LogUtils.d("busSubSiteMoudel======="+busSubSiteMoudel.toString());


                    //确认提交的网络请求
                    AppProgressDialog.showProgress(bsActivity);
                    //获取缓存的用户信息
                    AppUser appUser = ConfigSP.getUserInfo();

                    Rest rest = new Rest("/appbus/busReserve.nla", appUser.getUSER_ID(), appUser.getUSER_TOKEN());
                    rest.addParam("LINE_TYPE", Flag);//线路类型0:上班 1：回家 2：临时
                    rest.addParam("TYPE", TYPE);//0： 预约当天 1：长期预约
                    rest.addParam("BUS_ID", busMessageMoudle.getBUS_ID());//班车ID
                    rest.addParam("BUS_SITE_ID", busSubSiteMoudel.getBUS_SITE_ID());//站点
                    rest.addParam("DRIVER_ID", busMessageMoudle.getDRIVER_USERID());

                    //如果是临时预约，正常传入时间，如果是长期预约，传入时间为空会报错，需要传入一个
                    if (TYPE.equals("0")) {
                        rest.addParam("RESERVE_TIME", dayTime);//预约时间
                    } else {
                        rest.addParam("RESERVE_TIME", dayTime);//预约时间
                    }
                    rest.addParam("BUS_LINE_ID", busLineMoudel.getBUS_LINE_ID());//线路ID
                    rest.addParam("BUS_NUM", busMessageMoudle.getBUS_NUM());//座位数
                    rest.post("BusSubscribeActivity", new Callback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject, String state, String msg) {
                            AppProgressDialog.dismiss(bsActivity);
                            ToastUtil.showToast("预约成功");
                            bsActivity.finish();
                        }

                        @Override
                        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                            AppProgressDialog.dismiss(bsActivity);
                            ToastUtil.showToast(msg);
                        }

                        @Override
                        public void onError(Exception exception) {
                            AppProgressDialog.dismiss(bsActivity);
                            ToastUtil.showToast("网络繁忙，请稍后再试");
                        }
                    });
                    dismiss();
                }
            };

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //给dialog赋值
            busSubDialog.str_dialog_name = busLineMoudel.getLINE_NAME();
            busSubDialog.str_dialog_shift = busMessageMoudle.getBUS_NAME();
            busSubDialog.str_dialog_det = busMessageMoudle.getMODEL() + " " + busMessageMoudle.getNUMBER() + " " + busMessageMoudle.getCOLOR();
            busSubDialog.str_dialog_driver = busMessageMoudle.getDRIVER_NAME();
            busSubDialog.str_dialog_phone = busMessageMoudle.getDRIVER_TEL();
            busSubDialog.str_dialog_addre = this.str_dialog_addre;
            busSubDialog.str_dialog_time = this.str_dialog_time;
            busSubDialog.str_dialog_time_det = dayTime;
            busSubDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
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
        rest.post("BusSubscribeActivity", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    String ADDRESS = jsonObject.getJSONObject("dataset").getString("ADDRESS");
                    String LONGITUDE = jsonObject.getJSONObject("dataset").getString("LONGITUDE");
                    String LATITUDE = jsonObject.getJSONObject("dataset").getString("LATITUDE");
                    if (!TextUtils.isEmpty(LONGITUDE) && !TextUtils.isEmpty(LATITUDE)) {
                        setBusMarker(Double.valueOf(LATITUDE), Double.valueOf(LONGITUDE), ADDRESS);
                    }else{
                        doUserLoca();//初始设置用户的点
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.d("Exception:" + e.getMessage());
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

    /**
     * 设置班车标记点，并动画移过去 lat:纬度 lon:经度
     */
    public void setBusMarker(double lat, double lon, String title) {
        try {
            if (busMarker != null) {
                // 去除以前的标记
                busMarker.remove();
            }
            // 设置标记
            busMarker = map
                    .addMarker(new MarkerOptions()
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(BitmapFactory.decodeResource(
                                            bsActivity.getResources(), R.drawable.app_bus_car)))
                            .title("班车位置")
                            .snippet(title)
                            .position(new LatLng(lat, lon)));
            busMarker.showInfoWindow();
            // 移动到那个位置// 动画时间是1秒
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            LogUtils.d("Exception:" + e.getMessage());
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
                OkHttpUtils.getInstance().cancelTag("BusSubscribeActivity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

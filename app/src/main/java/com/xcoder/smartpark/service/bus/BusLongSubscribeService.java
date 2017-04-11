//package com.xcoder.smartpark.service.bus;
//
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.OrientationHelper;
//import android.text.TextUtils;
//import android.view.MotionEvent;
//import android.view.TextureView;
//import android.view.View;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.CameraUpdateFactory;
//import com.amap.api.maps2d.model.BitmapDescriptorFactory;
//import com.amap.api.maps2d.model.LatLng;
//import com.amap.api.maps2d.model.Marker;
//import com.amap.api.maps2d.model.MarkerOptions;
//import com.xcoder.lib.adapter.recyclerview.RvItemListener;
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.activity.bus.BusLongSubscribeActivity;
//import com.xcoder.smartpark.adapter.bus.BusSubShiftAdapter;
//import com.xcoder.smartpark.adapter.bus.BusSubSiteAdapter;
//import com.xcoder.smartpark.moudel.AppUser;
//import com.xcoder.smartpark.moudel.BusArriveTimes;
//import com.xcoder.smartpark.moudel.BusHomeMoudel;
//import com.xcoder.smartpark.moudel.BusSubSiteMoudel;
//import com.xcoder.smartpark.moudel.BusTemPoraryMoudel;
//import com.xcoder.smartpark.moudel.BusWrokMoudel;
//import com.xcoder.smartpark.network.Callback;
//import com.xcoder.smartpark.network.Rest;
//import com.xcoder.smartpark.outside.pickerview.TimePicker;
//import com.xcoder.smartpark.util.ConfigSP;
//import com.xcoder.smartpark.util.ToastUtil;
//import com.xcoder.smartpark.util.map.GetUserLocation;
//import com.xcoder.smartpark.util.map.LocationModel;
//import com.xcoder.smartpark.view.bus.BusLongSubscribeView;
//import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
//import com.xcoder.smartpark.widget.dialog.BusSubDialog;
//
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by xcoder_xz on 2016/12/24 0024.
// * 班车   长期预约
// */
//
//public class BusLongSubscribeService {
//
//    private BusLongSubscribeActivity blsActivity;
//    @Injection
//    BusLongSubscribeView blsv;
//
//    private TimePicker timePicker;
//    private AMap map;
//    private LocationModel location;//位置的信息
//    private int mapZoom = 17;// 地图缩放级别
//    private Marker locationMarker;//
//    private String snippet = "userMarker";//当前位置的marker命名
//    private List<BusSubSiteMoudel> listSite = new ArrayList<BusSubSiteMoudel>();//站点的数据
//    private BusSubShiftAdapter bsShiftAdapter;//时间段的适配器
//    private BusSubSiteAdapter bsSiteAdapter;//站点的适配器
//    public Intent intent;//传过来的班车线路id
//    private AppUser userInfo;
//    private String BUS_LINE_ID;//班车线路id
//    private String BUS_ID;//班车id
//    private List<BusArriveTimes> listTime = new ArrayList<BusArriveTimes>();//班次时间
//    //--//
//    String BUS_NAME = "";//名字
//    String LINE_NAME = "";//路线
//    String DRIVER_NAME = "";//司机
//    String DRIVER_TEL = "";//电话号码
//    String Bus_Detail = "";//详情，拼//详细
//    int BUS_NUM = 0;//剩余座位数
//    public String str_dialog_addre;//上车地点
//    public String str_dialog_time;//时间
//    //--//
//
//    public String Flag = "";//0:上班 1：回家 2：临时
//    private BusArriveTimes busArriveTimes;//班次
//    private BusSubSiteMoudel busSubSiteMoudel;//站点
//
//    public void init(BusLongSubscribeActivity busLongSubscribeActivity) {
//        this.blsActivity = busLongSubscribeActivity;
//        location = ConfigSP.getLocation();
//        userInfo = ConfigSP.getUserInfo();
//        initView();
//        initViewData();
//        doUserLoca();//初始设置用的点
//    }
//
//    /**
//     * 给View赋值
//     */
//    public void initViewData() {
//
//        if (intent != null) {
//            if (intent.getSerializableExtra("BusHomeMoudel") != null) {
//                //回家
//                Flag = "1";
//                BusHomeMoudel busHomeMoudel = (BusHomeMoudel) intent.getSerializableExtra("BusHomeMoudel");
//                this.BUS_LINE_ID = busHomeMoudel.getBUS_LINE_ID();
//                this.BUS_ID = busHomeMoudel.getBUS_ID();
//                BUS_NUM = busHomeMoudel.getBUS_NUM();
//                BUS_NAME = busHomeMoudel.getBUS_NAME();
//                LINE_NAME = busHomeMoudel.getLINE_NAME();
//                DRIVER_NAME = busHomeMoudel.getDRIVER_NAME();
//                DRIVER_TEL = busHomeMoudel.getDRIVER_TEL();
//                Bus_Detail = busHomeMoudel.getMODEL() + " " + busHomeMoudel.getNUMBER() + " " + busHomeMoudel.getCOLOR();//详情，拼
//                listTime = busHomeMoudel.getArriveTimes();//班车时间段
//            } else if (intent.getSerializableExtra("BusTemPoraryMoudel") != null) {
//                //临时
//                Flag = "2";
//                BusTemPoraryMoudel busTemPoraryMoudel = (BusTemPoraryMoudel) intent.getSerializableExtra("BusTemPoraryMoudel");
//                this.BUS_LINE_ID = busTemPoraryMoudel.getBUS_LINE_ID();
//                this.BUS_ID = busTemPoraryMoudel.getBUS_ID();
//                BUS_NUM = busTemPoraryMoudel.getBUS_NUM();
//                BUS_NAME = busTemPoraryMoudel.getBUS_NAME();
//                LINE_NAME = busTemPoraryMoudel.getLINE_NAME();
//                DRIVER_NAME = busTemPoraryMoudel.getDRIVER_NAME();
//                DRIVER_TEL = busTemPoraryMoudel.getDRIVER_TEL();
//                Bus_Detail = busTemPoraryMoudel.getMODEL() + " " + busTemPoraryMoudel.getNUMBER() + " " + busTemPoraryMoudel.getCOLOR();//详情，拼
//                listTime = busTemPoraryMoudel.getArriveTimes();//班车时间段
//            } else if (intent.getSerializableExtra("BusWrokMoudel") != null) {
//                //上班
//                Flag = "0";
//                BusWrokMoudel busWrokMoudel = (BusWrokMoudel) intent.getSerializableExtra("BusWrokMoudel");
//                this.BUS_LINE_ID = busWrokMoudel.getBUS_LINE_ID();
//                this.BUS_ID = busWrokMoudel.getBUS_ID();
//                BUS_NUM = busWrokMoudel.getBUS_NUM();
//                BUS_NAME = busWrokMoudel.getBUS_NAME();
//                LINE_NAME = busWrokMoudel.getLINE_NAME();
//                DRIVER_NAME = busWrokMoudel.getDRIVER_NAME();
//                DRIVER_TEL = busWrokMoudel.getDRIVER_TEL();
//                Bus_Detail = busWrokMoudel.getMODEL() + " " + busWrokMoudel.getNUMBER() + " " + busWrokMoudel.getCOLOR();//详情，拼
//                listTime = busWrokMoudel.getArriveTimes();//班车时间段
//            }
//        }
//        if (!TextUtils.isEmpty(BUS_NAME) && !TextUtils.isEmpty(BUS_LINE_ID) && userInfo != null && !TextUtils.isEmpty(BUS_ID)) {
//            blsv.bus_sub_name.setText(BUS_NAME);//名字
//            blsv.bus_sub_path.setText(LINE_NAME);//线路
//            blsv.bus_sub_det.setText(Bus_Detail);//班车详情
//            blsv.bus_sub_user.setText(DRIVER_NAME);//司机名字
//            blsv.bus_sub_phone.setText(DRIVER_TEL);//司机电话
//            //时间段的初始化
//            bsShiftAdapter.setData(listTime);
//            bsShiftAdapter.notifyDataSetChanged();
//            initData();//获取站点
//        } else {
//            ToastUtil.showToast("网络繁忙，请稍后再试");
//        }
//    }
//
//    public void initData() {
//        Rest rest = new Rest("/appbus/loadBusSite.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
//        rest.addParam("BUS_LINE_ID", BUS_LINE_ID);
//        rest.addParam("BUS_ID", BUS_ID);
//        rest.post("BusLongSubscribeActivity",new Callback() {
//            @Override
//            public void onSuccess(JSONObject jsonObject, String state, String msg) {
//                try {
//                    listSite = JSON.parseArray(jsonObject.getJSONArray("dataset_line").toString(), BusSubSiteMoudel.class);
//                    bsSiteAdapter.setData(listSite);
//                    bsSiteAdapter.notifyDataSetChanged();
//                } catch (Exception exc) {
//                    onError(exc);
//                    exc.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
//                ToastUtil.showToast(msg);
//            }
//
//            @Override
//            public void onError(Exception exception) {
//                ToastUtil.noNet();
//            }
//        });
//
//
//    }
//
//
//    public void initView() {
//        //RecyclerView处理
//        LinearLayoutManager layoutManagerShift = new LinearLayoutManager(blsActivity);
//        layoutManagerShift.setOrientation(OrientationHelper.HORIZONTAL);//水平
//        blsv.bus_sub_shift_rl.setLayoutManager(layoutManagerShift);
//        LinearLayoutManager layoutManagerSite = new LinearLayoutManager(blsActivity);
//        layoutManagerSite.setOrientation(OrientationHelper.HORIZONTAL);//水平
//        blsv.bus_sub_site_rl.setLayoutManager(layoutManagerSite);
//        //adapter
//        bsShiftAdapter = new BusSubShiftAdapter();
//        blsv.bus_sub_shift_rl.setAdapter(bsShiftAdapter);
//        //item条目点击事件
//        //时间段
//        bsShiftAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int postion) {
//                //得到班次的时间段
//                str_dialog_time=listTime.get(postion).getARRIVAL_TIME();
//                busArriveTimes = listTime.get(postion);
//                bsShiftAdapter.indexPos = postion;
//                bsShiftAdapter.notifyDataSetChanged();
//            }
//        });
//        //站点的
//        bsSiteAdapter = new BusSubSiteAdapter();
//        blsv.bus_sub_site_rl.setAdapter(bsSiteAdapter);
//        bsSiteAdapter.setItemClickListener(new RvItemListener.onItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int postion) {
//                //得到班次的  站点
//                str_dialog_addre=listSite.get(postion).getSITE_NAME();
//                busSubSiteMoudel = listSite.get(postion);
//                bsSiteAdapter.indexPos = postion;
//                bsSiteAdapter.notifyDataSetChanged();
//            }
//        });
//        //地图处理
//        map = blsv.bus_sub_mapview.getMap();
//        map.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//            @Override
//            public void onTouch(MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        blsv.bus_sub_mapview.requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                        blsv.bus_sub_scroll.fullScroll(ScrollView.FOCUS_DOWN);
//                        break;
//                    default:
//                        blsv.bus_sub_mapview.requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置用户当前的地点
//     */
//    public void doUserLoca() {
//        AppProgressDialog.showProgress(blsActivity, "定位当前位置中...");
//        //开启一下定位，获得用户的位置
//        GetUserLocation getUserLocation = new GetUserLocation();
//        getUserLocation.setDoLocation(new GetUserLocation.DoLocation() {
//            @Override
//            public void location(Boolean isGetLocation) {
//                AppProgressDialog.dismiss(blsActivity);
//                location = ConfigSP.getLocation();
//                if (location == null || location.getLat() < 0) {
//                } else {
//                    setMarker(location.getLat(), location.getLon(), location.getAddress());
//                }
//            }
//        });
//    }
//
//    /**
//     * 设置标记点，并动画移过去 lat:纬度 lon:经度
//     */
//    public void setMarker(double lat, double lon, String title) {
//        try {
//            if (locationMarker != null) {
//                // 去除以前的标记
//                locationMarker.remove();
//            }
//            // 设置标记
//            locationMarker = map
//                    .addMarker(new MarkerOptions()
//                            .anchor(0.5f, 0.5f)
//                            .icon(BitmapDescriptorFactory
//                                    .fromBitmap(BitmapFactory.decodeResource(
//                                            blsActivity.getResources(), R.drawable.app_mark_icon)))
//                            .position(new LatLng(lat, lon)));
//
//            // 移动到那个位置// 动画时间是1秒
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), mapZoom), 1000, null);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * 确认预约
//     */
//    public void doSubmit() {
//        //对数据判断
//        if(TextUtils.isEmpty(str_dialog_time)){
//            ToastUtil.showToast("请选择班次时间");
//            return;
//        }
//        if(TextUtils.isEmpty(str_dialog_addre)){
//            ToastUtil.showToast("请选择班次站点");
//            return;
//        }
//        try {
//            BusSubDialog busSubDialog = new BusSubDialog(blsActivity) {
//
//                @Override
//                public void doSubmit(View view) {
//                    //确认提交的网络请求
//                    //ToastUtil.showToast("啊，你好像请求了网络....");
//                    AppProgressDialog.showProgress(blsActivity);
//                    //获取缓存的用户信息
//                    AppUser appUser = ConfigSP.getUserInfo();
//
//                    Rest rest=new Rest("/appbus/busReserve.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
//                    rest.addParam("LINE_TYPE", Flag);//线路类型0:上班 1：回家 2：临时
//                    rest.addParam("TYPE","1");//0： 预约当天 1：长期预约
//                    rest.addParam("BUS_ID", BUS_ID);//班车ID
//                    rest.addParam("BUS_SITE_ID",busSubSiteMoudel.getBUS_SITE_ID());//站点
//                    rest.addParam("RESERVE_TIME", "2017");//长期预约没有时间，暂时写死等后台改
//                    rest.addParam("BUS_NUM_ID",busArriveTimes.getBUS_NUM_ID());//车次
//                    rest.addParam("BUS_LINE_ID",busSubSiteMoudel.getBUS_LINE_ID());//线路ID
//                    rest.addParam("BUS_NUM", String.valueOf(BUS_NUM));//座位数
//                    rest.post("BusLongSubscribeActivity",new Callback() {
//                        @Override
//                        public void onSuccess(JSONObject jsonObject, String state, String msg) {
//                            AppProgressDialog.dismiss(blsActivity);
//                            ToastUtil.showToast("预约成功");
//                            blsActivity.finish();
//                        }
//
//                        @Override
//                        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
//                            AppProgressDialog.dismiss(blsActivity);
//                            ToastUtil.showToast(msg);
//                        }
//
//                        @Override
//                        public void onError(Exception exception) {
//                            AppProgressDialog.dismiss(blsActivity);
//                            ToastUtil.showToast("网络繁忙，请稍后再试");
//                        }
//                    });
//                    dismiss();
//
//                }
//            };
//            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//            //给dialog赋值
//            busSubDialog.str_dialog_name = this.BUS_NAME;
//            busSubDialog.str_dialog_shift =this.LINE_NAME;
//            busSubDialog.str_dialog_det =this.Bus_Detail;
//            busSubDialog.str_dialog_driver = this.DRIVER_NAME;
//            busSubDialog.str_dialog_phone = this.DRIVER_TEL;
//            busSubDialog.str_dialog_addre =this.str_dialog_addre;
//            busSubDialog.str_dialog_time =this.str_dialog_time;
//            busSubDialog.str_dialog_time_det = sf.format(new Date());
//            busSubDialog.show();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//}

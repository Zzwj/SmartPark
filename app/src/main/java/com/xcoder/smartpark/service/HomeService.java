package com.xcoder.smartpark.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.activity.home.HomeSelectActivity;
import com.xcoder.smartpark.activity.my.MyMessageActivity;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.activity.other.PatrolManActivity;
import com.xcoder.smartpark.activity.other.UseAuditActivity;
import com.xcoder.smartpark.activity.other.demo.BLEMainActivity;
import com.xcoder.smartpark.adapter.home.HomeGvAdapter;
import com.xcoder.smartpark.fragment.HomeFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.HomeGvEntity;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.StaticString;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.HomeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 首页
 */

public class HomeService {

    @Injection
    HomeView hv;
    private HomeFragment homeFragment;
    private HomeGvAdapter homeGvAdapter1;
    private HomeGvAdapter homeGvAdapter2;
    private HomeGvAdapter homeGvAdapter3;

    private List<HomeGvEntity> list1 = new ArrayList<>();
    private List<HomeGvEntity> list2 = new ArrayList<>();
    private List<HomeGvEntity> list3 = new ArrayList<>();


    private List<HomeGvEntity> listToService = new ArrayList<>();

    private AppUser userInfo;

    public void init(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();
        initData();
    }

    public void initData() {

        Rest rest = new Rest("/appuser/getAppResourceMenuList.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    listToService = JSON.parseArray(jsonObject.getJSONArray("dataset_line").toString(), HomeGvEntity.class);
                    getMap();
                } catch (Exception e) {
                    e.printStackTrace();
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

    /**
     * 根据服务器数据解析对应String，得到Drawable
     */
    public void getMap() throws Exception {

        //因为有3个模块，需要3个map，存放（map取值较快）
        ArrayMap<String, Integer> am1 = new ArrayMap<>();
        am1.put("BUS", R.drawable.bus_ib);//班车
        am1.put("VISITOR", R.drawable.tou_ib);//访客
        am1.put("CAR", R.drawable.use_ib);//用车
        am1.put("NOTICE", R.drawable.notice_ib);//通知公告
        am1.put("RESTAURANT", R.drawable.food_ib);//餐饮服务
        am1.put("PROPERTY", R.drawable.property_ib);//物业服务
        am1.put("CONFERENCEROOM", R.drawable.meeting_ib);//会议室
        am1.put("TSMARKET", R.drawable.market_ib);//跳骚市场
        am1.put("INTELLIGENTLIFE", R.drawable.life_ib);//智慧生活
        am1.put("INTRODUCE", R.drawable.introduce_ib);//信息港园区介绍

        am1.put("ASSETS", R.drawable.assets_ib);//资产管理
        am1.put("PATROL", R.drawable.patrolling_ib);//巡更管理
        am1.put("PERSONNEL_STATISTICS", R.drawable.personne_ib);//人员统计
        am1.put("CARAPPROVAL", R.drawable.car_approval_ib);//用车审批
        am1.put("ENERGY", R.drawable.energy_ib);//能源管理
        am1.put("VEDIO", R.drawable.monitoring_ib);//视屏监控

        am1.put("CARDRIVER", R.drawable.surname_driver_ib);//公车司机
        am1.put("BUSDRIVER", R.drawable.bus_driver_ib);//班车司机
        am1.put("PROPERTY_USER", R.drawable.property_personnel_ib);//物业人员
        am1.put("CATERING", R.drawable.food_personnel_ib);//餐饮人员

//        List<HomeGvEntity> listHge=new ArrayList<>();
//        listHge.add(new HomeGvEntity("BUS", R.drawable.bus_ib,1));//班车

        //根据name的不同，放入不同list中，
        for (HomeGvEntity hge : listToService) {
            String strJ = hge.getAPPRESOURCE_ID();

            HomeGvEntity homeGvEntity = new HomeGvEntity(strJ, am1.get(strJ), hge.getLEVE());

            if (hge.getLEVE() == 1) {

                list1.add(homeGvEntity);

            } else if (hge.getLEVE() == 2) {

                list2.add(homeGvEntity);

            } else if (hge.getLEVE() == 3) {

                list3.add(homeGvEntity);
            }
        }
        //检查每个集合%3是否为0，如果不为0，说名最后一排有空的则添加
        if (list1.size() % 3 != 0) {
            int i1 = list1.size() % 3;
            for (int i = 0; i <3-i1; i++) {
                list1.add(new HomeGvEntity("null", R.drawable.home_bg_img, 1));
            }
        }
        if (list2.size() % 3 != 0) {
            int i2 = list2.size() % 3;
            for (int i = 0; i < 3-i2; i++) {
                list2.add(new HomeGvEntity("null", R.drawable.home_bg_img, 2));
            }
        }
        if (list3.size() % 3 != 0) {
            int i3 = list3.size() % 3;
            for (int i = 0; i < 3-i3; i++) {
                list3.add(new HomeGvEntity("null", R.drawable.home_bg_img, 3));
            }
        }
        //判断每个集合是否0，如果是，要把gridview隐藏
        if (list1.size() == 0) {
            hv.home_mgv1.setVisibility(View.GONE);
        } else {
            homeGvAdapter1.setListData(list1);
        }
        if (list2.size() == 0) {
            hv.home_mgv2.setVisibility(View.GONE);
        } else {
            homeGvAdapter2.setListData(list2);
        }
        if (list3.size() == 0) {
            hv.home_mgv3.setVisibility(View.GONE);
        } else {
            homeGvAdapter3.setListData(list3);
        }


    }


    private void initView() {

//        Glide.with(homeFragment).load(R.drawable.home_main_head_iv).crossFade().
//                placeholder(R.drawable.home_ib_bg).error(R.drawable.home_ib_bg).fitCenter()
//                .into(hv.home_main_iv_head);

        //图标第一部分
        homeGvAdapter1 = new HomeGvAdapter(homeFragment);
        hv.home_mgv1.setAdapter(homeGvAdapter1);
        hv.home_mgv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeGvEntity hge = (HomeGvEntity) homeGvAdapter1.getItem(position);
                if (TextUtils.equals(hge.getAPPRESOURCE_ID(),"BUS")) {
                    //班车
                    Bundle bundle = new Bundle();
                    bundle.putString(StaticString.ACTIVITYSTR, "班车");
                    homeFragment.openActivity(HomeSelectActivity.class, bundle);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "VISITOR")) {
                    //访客
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(StaticString.ACTIVITYSTR, "访客");
                    homeFragment.openActivity(HomeSelectActivity.class, bundle2);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "CAR")) {
                    //用车
                    Bundle bundle3 = new Bundle();
                    bundle3.putString(StaticString.ACTIVITYSTR, "用车");
                    homeFragment.openActivity(HomeSelectActivity.class, bundle3);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "NOTICE")) {
                    //通知公告
                    homeFragment.openActivity(MyMessageActivity.class);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "RESTAURANT")) {
                    //餐饮服务
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "PROPERTY")) {
                    //物业服务
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "CONFERENCEROOM")) {
                    //会议室
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "TSMARKET")) {
                    //跳骚市场
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "INTELLIGENTLIFE")) {
                    //智慧生活
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "INTRODUCE")) {
                    //信息港园区介绍
                }
            }
        });


        //图标第二部分
        homeGvAdapter2 = new HomeGvAdapter(homeFragment);
        hv.home_mgv2.setAdapter(homeGvAdapter2);
        hv.home_mgv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeGvEntity hge = (HomeGvEntity) homeGvAdapter2.getItem(position);
                if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "ASSETS")) {
                    //资产管理
                    homeFragment.openActivity(BLEMainActivity.class);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "PATROL")) {
                    //巡更管理
                    homeFragment.openActivity(PatrolManActivity.class);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "PERSONNEL_STATISTICS")) {
                    //人员统计
                    Intent intent15 = new Intent(homeFragment.getContext(), HtmlActivity.class);
                    intent15.putExtra("htmlTitle", "人员统计");
                    intent15.putExtra("htmlUrl", AppNet.appNet + AppNet.apppatrol);
                    homeFragment.startActivity(intent15);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "CARAPPROVAL")) {
                    //用车审批
                    homeFragment.openActivity(UseAuditActivity.class);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "ENERGY")) {
                    //能源管理
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "VEDIO")) {
                    //视频监控
                }
            }
        });

        //图标第三部分
        homeGvAdapter3 = new HomeGvAdapter(homeFragment);
        hv.home_mgv3.setAdapter(homeGvAdapter3);
        hv.home_mgv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeGvEntity hge = (HomeGvEntity) homeGvAdapter3.getItem(position);
                if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "CARDRIVER")) {
                    //公车司机
                    homeFragment.openActivity(BusDriverActivity.class);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "BUSDRIVER")) {
                    //班车司机
                    Intent intent20 = new Intent(homeFragment.getContext(), HtmlActivity.class);
                    intent20.putExtra("htmlTitle", "班车信息");
                    intent20.putExtra("htmlUrl", AppNet.appNet + AppNet.appbus + "?USER_ID=" + userInfo.getUSER_ID());
                    homeFragment.startActivity(intent20);
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "PROPERTY_USER")) {
                    //物业人员
                } else if (TextUtils.equals(hge.getAPPRESOURCE_ID(), "CATERING")) {
                    //餐饮人员
                }
            }
        });

    }

}

package com.xcoder.smartpark.service.bus;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.adapter.bus.BusHomeAdapter;
import com.xcoder.smartpark.fragment.bus.BusHomeFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHomeMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.bus.BusHomeView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 班车-回家
 * Created by xcoder_xz on 2016/12/14 0014.
 */

public class BusHomeService {
    private BusHomeFragment busHomeFragment;
    private List<BusHomeMoudel> listData = new ArrayList<BusHomeMoudel>();
    public RefreshAndload refreshAndload;
    private BusHomeAdapter busAdapter;
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    @Injection
    BusHomeView bhv;

    public void init(BusHomeFragment busHomeFragment) {
        this.busHomeFragment = busHomeFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();
    }


    /**
     * 数据初始化
     */
    public void initData() {
        Rest rest = new Rest("/appbus/loadLine.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.addParam("LINE_TYPE","1");//0:上班 1：回家 2：临时
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<BusHomeMoudel> listBH = JSON.parseArray(jsonArray.toString(), BusHomeMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listBH);
                    busAdapter.setListData(listData);
                    refreshAndload.stopRefreshAndLoad();
                } catch (Exception exception) {
                    onError(exception);
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                refreshAndload.stopRefreshAndLoad();
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                refreshAndload.stopRefreshAndLoad();
                ToastUtil.noNet();
            }
        });
    }

    /**
     * 初始化View相关的
     */
    public void initView() {

        //添加头部  必须在设置适配器之前
        bhv.bus_home_main_lv.addHeaderView(new View(busHomeFragment.getContext()));
        refreshAndload = new RefreshAndload(busHomeFragment.getContext(), bhv.bus_home_main_lv, bhv.bus_home_main_srl);
        refreshAndload.setOnRefreshListener(new RefreshAndload.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                currentPage = 1;
                initData();
            }
        });
        refreshAndload.setOnLoadListener(new RefreshAndload.OnLoadListener() {
            @Override
            public void onLoad() {
                if (totalPage > 0 && currentPage < totalPage) {
                    currentPage++;
                    initData();
                } else {
                    refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
                }
            }
        });
        busAdapter = new BusHomeAdapter(busHomeFragment.getContext());
        bhv.bus_home_main_lv.setAdapter(busAdapter);
        refreshAndload.immediatelyRefresh();
    }
}

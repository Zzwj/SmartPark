package com.xcoder.smartpark.service.bus;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.adapter.bus.BusWrokAdapter;
import com.xcoder.smartpark.fragment.bus.BusWrokFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusWrokMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.bus.BusWrokView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/14 0014.
 */

public class BusWrokService {
    private BusWrokFragment busWrokFragment;
    @Injection
    BusWrokView bwv;
    private List<BusWrokMoudel> listData = new ArrayList<BusWrokMoudel>();
    public RefreshAndload refreshAndload;
    private BusWrokAdapter busAdapter;
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新


    public void init(BusWrokFragment busWrokFragment) {
        try {
            this.busWrokFragment = busWrokFragment;
            userInfo = ConfigSP.getUserInfo();
            initView();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 数据初始化
     */
    public void initData() {
        Rest rest = new Rest("/appbus/loadLine.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.addParam("LINE_TYPE","0");//0:上班 1：回家 2：临时
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<BusWrokMoudel> listBW = JSON.parseArray(jsonArray.toString(), BusWrokMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listBW);
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
    public void initView() throws  Exception{
        //添加头部必须在设置适配器之前
        bwv.bus_wrok_main_lv.addHeaderView(new View(busWrokFragment.getContext()));
        //刷新加载
        refreshAndload = new RefreshAndload(busWrokFragment.getContext(), bwv.bus_wrok_main_lv, bwv.bus_wrok_main_srl);
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
        busAdapter = new BusWrokAdapter(busWrokFragment.getContext());
        bwv.bus_wrok_main_lv.setAdapter(busAdapter);
        //进入页面就加载数据
        refreshAndload.immediatelyRefresh();
    }
}

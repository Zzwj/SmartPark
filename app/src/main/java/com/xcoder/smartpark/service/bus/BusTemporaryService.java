package com.xcoder.smartpark.service.bus;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.adapter.bus.BusTemporaryAdapter;
import com.xcoder.smartpark.fragment.bus.BusTemporaryFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusTemPoraryMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.bus.BusTemporaryView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 班车 临时
 */

public class BusTemporaryService {
    @Injection
    BusTemporaryView btv;
    private BusTemporaryFragment busTemporaryFragment;
    private List<BusTemPoraryMoudel> listData = new ArrayList<BusTemPoraryMoudel>();
    public RefreshAndload refreshAndload;
    private BusTemporaryAdapter busAdapter;
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    public void init(BusTemporaryFragment busTemporaryFragment) {
        this.busTemporaryFragment = busTemporaryFragment;
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
        rest.addParam("LINE_TYPE","2");//0:上班 1：回家 2：临时
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<BusTemPoraryMoudel> listBT = JSON.parseArray(jsonArray.toString(), BusTemPoraryMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listBT);
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

        //添加头部必须在设置适配器之前
        btv.bus_temporary_lv.addHeaderView(new View(busTemporaryFragment.getContext()));
        //刷新加载
        refreshAndload = new RefreshAndload(busTemporaryFragment.getContext(), btv.bus_temporary_lv, btv.bus_temporary_srl);
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
        busAdapter = new BusTemporaryAdapter(busTemporaryFragment.getContext());
        btv.bus_temporary_lv.setAdapter(busAdapter);
        refreshAndload.immediatelyRefresh();
    }

}

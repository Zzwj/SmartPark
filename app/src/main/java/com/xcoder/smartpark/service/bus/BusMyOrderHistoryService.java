package com.xcoder.smartpark.service.bus;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.adapter.bus.BusMyOrderHistoryAdapter;
import com.xcoder.smartpark.fragment.bus.BusMyOrderHistoryFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.bus.BusMyOrderHistoryView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 班车--我的预约---历史预约
 */
public class BusMyOrderHistoryService {
    private BusMyOrderHistoryFragment busMyOrderHistoryFragment;
    @Injection
    BusMyOrderHistoryView bmohv;
    public RefreshAndload refreshAndload;
    private BusMyOrderHistoryAdapter busMyOrderHistoryAdapter;
    private List<BusHistoryMoudle> listData = new ArrayList<BusHistoryMoudle>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    public void init(BusMyOrderHistoryFragment busMyOrderHistoryFragment) {
        this.busMyOrderHistoryFragment = busMyOrderHistoryFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();
    }


    public void initData() {
        Rest rest = new Rest("/appbus/reserveHistory.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("USERID", userInfo.getUSER_ID());
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("BusMyOrderHistoryFragment",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<BusHistoryMoudle> listMH = JSON.parseArray(jsonArray.toString(), BusHistoryMoudle.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listMH);
                    busMyOrderHistoryAdapter.setListData(listData);
                    refreshAndload.stopRefreshAndLoad();
                } catch (Exception exc) {
                    onError(exc);
                    exc.printStackTrace();
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
                ToastUtil.showToast("网络繁忙");
            }
        });
    }

    /**
     * 初始化view相关
     */
    public void initView() {
        refreshAndload = new RefreshAndload(busMyOrderHistoryFragment.getContext(), bmohv.order_history_lv, bmohv.order_history_srl);
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
                    LogUtils.d("---stopRefreshAndLoad");
                    refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
                }

            }
        });
        //
        busMyOrderHistoryAdapter = new BusMyOrderHistoryAdapter(busMyOrderHistoryFragment.getContext());
        bmohv.order_history_lv.setAdapter(busMyOrderHistoryAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
    }

}

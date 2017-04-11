package com.xcoder.smartpark.service.other;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.adapter.other.DriverSubscribeHistoryAdapter;
import com.xcoder.smartpark.fragment.other.BusDriverSubscribeHistoryFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.SubscribeHistoryMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.other.BusDriverSubscribeHistoryView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/31 0031.
 * 其他--公车---预约信息---历史预约
 */

public class BusDriverSubscribeHistoryService {

    @Injection
    BusDriverSubscribeHistoryView bdshv;
    public RefreshAndload refreshAndload;
    public DriverSubscribeHistoryAdapter driverSubscribeHistoryAdapter;
    private List<SubscribeHistoryMoudle> listData = new ArrayList<SubscribeHistoryMoudle>();

    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    private BusDriverSubscribeHistoryFragment bdshFragment;

    public void init(BusDriverSubscribeHistoryFragment bdshFragment) {
        this.bdshFragment = bdshFragment;
        userInfo= ConfigSP.getUserInfo();
        initView();
    }

    public void initData(){

        Rest rest = new Rest("/appcar/listDriverHistory.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("STATUS", "2");//0待审核 1未出行 2已出行 3 审核未通过
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("BusDriverSubscribeHistoryFragment",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<SubscribeHistoryMoudle> listMH = JSON.parseArray(jsonArray.toString(), SubscribeHistoryMoudle.class);

                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }

                    listData.addAll(listMH);

                    driverSubscribeHistoryAdapter.setListData(listData);
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


    public void initView(){
        refreshAndload = new RefreshAndload(bdshFragment.getContext(), bdshv.sub_his_lv, bdshv.sub_his_srl);
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
        //
        driverSubscribeHistoryAdapter = new DriverSubscribeHistoryAdapter(bdshFragment.getContext());
        bdshv.sub_his_lv.setAdapter(driverSubscribeHistoryAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
    }
}

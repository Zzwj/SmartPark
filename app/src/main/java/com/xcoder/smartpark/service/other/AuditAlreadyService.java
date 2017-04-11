package com.xcoder.smartpark.service.other;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.adapter.bus.BusMyOrderHistoryAdapter;
import com.xcoder.smartpark.adapter.other.AuditAlreadyAdapter;
import com.xcoder.smartpark.fragment.other.AuditAlreadyFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;
import com.xcoder.smartpark.moudel.OtherAuditAlreadyMoudle;
import com.xcoder.smartpark.moudel.OtherAuditWaitMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.other.AuditAlreadyView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcder_xz on 2016/12/30 0030.
 * 用车审核---已审核
 */

public class AuditAlreadyService {
    @Injection
    AuditAlreadyView aav;
    public RefreshAndload refreshAndload;
    private AuditAlreadyAdapter auditAlreadyAdapter;
    private List<OtherAuditAlreadyMoudle> listData = new ArrayList<OtherAuditAlreadyMoudle>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    private AuditAlreadyFragment aaFragment;

    public void init(AuditAlreadyFragment aaFragment) {
        this.aaFragment = aaFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();

    }

    public void initData() {
        Rest rest;
        if(ConfigSP.getIsLEADER()) {
            rest = new Rest("/appcar/listLeaderApprove.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        }else {
            rest = new Rest("/appcar/listApprove.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        }
        rest.addParam("DEPT_ID",userInfo.getDEPT_ID());
        rest.addParam("STATUS", "1");//这个状态没鸟用了，后台新提供的接口
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("AuditAlreadyFragment",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<OtherAuditAlreadyMoudle> listMH = JSON.parseArray(jsonArray.toString(), OtherAuditAlreadyMoudle.class);

                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }

                    listData.addAll(listMH);

                    auditAlreadyAdapter.setListData(listData);
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

    public void initView() {
        refreshAndload = new RefreshAndload(aaFragment.getContext(), aav.audit_already_main_lv, aav.audit_already_main_srl);
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
        auditAlreadyAdapter = new AuditAlreadyAdapter(aaFragment.getContext());
        aav.audit_already_main_lv.setAdapter(auditAlreadyAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
    }
}

package com.xcoder.smartpark.service.other;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.adapter.bus.BusMyOrderHistoryAdapter;
import com.xcoder.smartpark.adapter.other.AuditWaitAdapter;
import com.xcoder.smartpark.fragment.other.AuditWaitFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;
import com.xcoder.smartpark.moudel.OtherAuditWaitMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.other.AuditWaitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 用车审核--未审核
 */

public class AuditWaitService {
    @Injection
    AuditWaitView awv;
    private AuditWaitFragment awFragment;
    public RefreshAndload refreshAndload;
    private AuditWaitAdapter auditWaitAdapter;
    private List<OtherAuditWaitMoudle> listData = new ArrayList<OtherAuditWaitMoudle>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    public void init(AuditWaitFragment awFragment) {
        this.awFragment = awFragment;
        userInfo= ConfigSP.getUserInfo();
        initView();
    }

    public void initData(){
        Rest rest;
        if(ConfigSP.getIsLEADER()) {
            rest = new Rest("/appcar/listLeaderUnApprove.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        }else {
            rest = new Rest("/appcar/listUnApprove.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());

        }
        rest.addParam("DEPT_ID",userInfo.getDEPT_ID());
        rest.addParam("STATUS", "0");//0待审核 1未出行 2已出行 3 审核未通过
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("AuditWaitFragment",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<OtherAuditWaitMoudle> listMH = JSON.parseArray(jsonArray.toString(), OtherAuditWaitMoudle.class);

                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }

                    listData.addAll(listMH);

                    auditWaitAdapter.setListData(listData);
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
        refreshAndload = new RefreshAndload(awFragment.getContext(), awv.audit_wait_main_lv, awv.audit_wait_main_srl);
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
        auditWaitAdapter = new AuditWaitAdapter(awFragment.getContext(),this);
        awv.audit_wait_main_lv.setAdapter(auditWaitAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
    }


}

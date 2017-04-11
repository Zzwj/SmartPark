package com.xcoder.smartpark.service.use;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.UseMyOrderHistoryMoudel;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.adapter.use.UseMyOrderHistoryAdapter;
import com.xcoder.smartpark.fragment.use.UseMyOrderHistoryFragment;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.use.UseMyOrderHistoryView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/19 0019.
 * 用车--我的用车----历史预约
 */

public class UseMyOrderHistoryService {
    @Injection
    UseMyOrderHistoryView umohv;
    private UseMyOrderHistoryFragment useMyOrderHistoryFragment;
    public RefreshAndload refreshAndload;
    private UseMyOrderHistoryAdapter useMyOrderHistoryAdapter;
    private List<UseMyOrderHistoryMoudel> listData = new ArrayList<UseMyOrderHistoryMoudel>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    public void init(UseMyOrderHistoryFragment useMyOrderHistoryFragment) {
        this.useMyOrderHistoryFragment = useMyOrderHistoryFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();
    }

    public void initData() {
        Rest rest = new Rest("/appcar/listHistory.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("UseMyOrderHistoryFragment",new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<UseMyOrderHistoryMoudel> listUmofMoudel = JSON.parseArray(jsonArray.toString(), UseMyOrderHistoryMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listUmofMoudel);
                    useMyOrderHistoryAdapter.setListData(listData);
                    refreshAndload.stopRefreshAndLoad();
                } catch (Exception exc) {
                    onError(exc);
                    exc.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                ToastUtil.showToast("网络繁忙");
            }
        });
    }

    /**
     * 初始化view相关
     */
    public void initView() {
        refreshAndload = new RefreshAndload(useMyOrderHistoryFragment.getContext(),
                umohv.use_history_lv, umohv.use_history_srl);
        refreshAndload.setOnRefreshListener(new RefreshAndload.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                isRefresh = true;
                currentPage = 1;
                initData();
            }
        });
        refreshAndload.setOnLoadListener(new RefreshAndload.OnLoadListener() {
            @Override
            public void onLoad() {
                //加载
                if (totalPage > 0 && currentPage < totalPage) {
                    currentPage++;
                    initData();
                } else {
                    refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
                }
            }
        });
        //
        useMyOrderHistoryAdapter = new UseMyOrderHistoryAdapter(useMyOrderHistoryFragment.getContext());
        umohv.use_history_lv.setAdapter(useMyOrderHistoryAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
    }

}

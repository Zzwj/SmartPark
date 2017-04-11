package com.xcoder.smartpark.service.use;

import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.UseMyOrderFutureMoudel;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.adapter.use.UseMyOrderFutureAdapter;
import com.xcoder.smartpark.fragment.use.UseMyOrderFutureFragment;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.use.UseMyOrderFutureView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车---我的预约---预约未出行
 */

public class UseMyOrderFutureService {
    @Injection
    UseMyOrderFutureView umofv;

    private UseMyOrderFutureFragment useMyOrderFutureFragment;
    public RefreshAndload refreshAndload;
    private UseMyOrderFutureAdapter useMyOrderFutureAdapter;
    private List<UseMyOrderFutureMoudel> listData = new ArrayList<UseMyOrderFutureMoudel>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    public void init(UseMyOrderFutureFragment useMyOrderFutureFragment) {
        this.useMyOrderFutureFragment = useMyOrderFutureFragment;
        userInfo = ConfigSP.getUserInfo();
        initView();
    }

    public void initData() {
        Rest rest = new Rest("/appcar/listUngo.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("STATUS", "1");//0待审核 1未出行 2已出行 3 审核未通过
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("UseMyOrderFutureFragment", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<UseMyOrderFutureMoudel> listUmofMoudel = JSON.parseArray(jsonArray.toString(), UseMyOrderFutureMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listUmofMoudel);
                    useMyOrderFutureAdapter.setListData(listData);
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
                ToastUtil.noNet();
            }
        });
    }

    /**
     * 初始化view相关
     */
    public void initView() {
        refreshAndload = new RefreshAndload(useMyOrderFutureFragment.getContext(),
                umofv.use_future_lv, umofv.use_future_srl);
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
        useMyOrderFutureAdapter = new UseMyOrderFutureAdapter(useMyOrderFutureFragment.getContext());
        umofv.use_future_lv.setAdapter(useMyOrderFutureAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();


    }


}

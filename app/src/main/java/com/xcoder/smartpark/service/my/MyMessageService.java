package com.xcoder.smartpark.service.my;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.activity.my.MyMessageActivity;
import com.xcoder.smartpark.activity.my.MyMessageDetailActivity;
import com.xcoder.smartpark.adapter.my.MyMessageAdapter;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.MessageMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.my.MyMessageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的---我的消息
 */

public class MyMessageService {


    @Injection
    MyMessageView mmv;
    public RefreshAndload refreshAndload;
    private MyMessageAdapter myMessageAdapter;
    private List<MessageMoudel> listData = new ArrayList<MessageMoudel>();
    private int currentPage = 1;//当前页码
    private int totalPage = 0;//总页码
    private AppUser userInfo;//用户信息
    private Boolean isRefresh = false;//判断是否是刷新

    private MyMessageActivity mMActivity;

    public void init(MyMessageActivity myMessageActivity) {
        this.mMActivity = myMessageActivity;
        try {
            userInfo = ConfigSP.getUserInfo();
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initData() {
        Rest rest = new Rest("/appnotice/getAppNotice.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("showCount", "20");
        rest.addParam("currentPage", String.valueOf(currentPage));
        rest.post("MyMessageActivity", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<MessageMoudel> listUmofMoudel = JSON.parseArray(jsonArray.toString(), MessageMoudel.class);
                    if (isRefresh) {
                        listData.clear();
                        isRefresh = false;
                    }
                    listData.addAll(listUmofMoudel);
                    myMessageAdapter.setListData(listData);
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
        refreshAndload = new RefreshAndload(mMActivity,
                mmv.my_message_lv, mmv.my_message_srl);
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
        myMessageAdapter = new MyMessageAdapter(mMActivity);
        mmv.my_message_lv.setAdapter(myMessageAdapter);
        //进入就刷新一次
        refreshAndload.immediatelyRefresh();
        //点击事件
        mmv.my_message_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MessageMoudel item = (MessageMoudel) myMessageAdapter.getItem(position);

                Intent intent = new Intent(mMActivity, MyMessageDetailActivity.class);
                intent.putExtra("contentTitle", item.getTITLE());
                intent.putExtra("contentDetail", item.getCONTENT());
                mMActivity.startActivity(intent);

            }
        });


    }

}

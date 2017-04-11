package com.xcoder.smartpark.service.my;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.util.RefreshAndload;
import com.xcoder.smartpark.activity.my.MyMoneyActivity;
import com.xcoder.smartpark.adapter.my.MyMoneyAdapter;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.my.MyMoneyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16 0016.
 * 我的---我的钱包
 */

public class MyMoneyService {
    @Injection
    MyMoneyView mmv;

    private MyMoneyActivity myMoneyActivity;

    private List<String> listData = new ArrayList<String>();
    private MyMoneyAdapter myMoneyAdapter;
    public RefreshAndload refreshAndload;

    public void init(MyMoneyActivity myMoneyActivity) {
        this.myMoneyActivity = myMoneyActivity;
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    public void initView(){
        //适配器初始化
        myMoneyAdapter = new MyMoneyAdapter();
        mmv.my_money_lv.setAdapter(myMoneyAdapter);
        refreshAndload = new RefreshAndload(myMoneyActivity, mmv.my_money_lv, mmv.my_money_srl);
        refreshAndload.setOnRefreshListener(new RefreshAndload.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.showToast("刷新了");
                initData();
            }
        });

        refreshAndload.setOnLoadListener(new RefreshAndload.OnLoadListener() {
            @Override
            public void onLoad() {
                refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
            }
        });

        //进入应用后立即调用加载
        //refreshAndload.immediatelyRefresh();


    }

    /**
     * 获取数据
     */
    public void initData(){
        for(int i=0; i<10; i++){
            listData.add("我是item"+i);
        }

        myMoneyAdapter.setListData(listData);
        refreshAndload.stopRefreshAndLoad();
    }

}

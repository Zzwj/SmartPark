//package com.xcoder.smartpark.service.tou;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.AdapterView;
//
//import com.alibaba.fastjson.JSON;
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.smartpark.activity.tou.TouDetailActivity;
//import com.xcoder.smartpark.adapter.tou.TouMainAdapter;
//import com.xcoder.smartpark.fragment.TouFragment;
//import com.xcoder.smartpark.moudel.AppUser;
//import com.xcoder.smartpark.moudel.Visitor;
//import com.xcoder.smartpark.network.Callback;
//import com.xcoder.smartpark.network.Rest;
//import com.xcoder.smartpark.util.ConfigSP;
//import com.xcoder.smartpark.util.RefreshAndload;
//import com.xcoder.smartpark.util.ToastUtil;
//import com.xcoder.smartpark.view.tou.TouView;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by xcoder_xz on 2016/12/9 0009.
// * 访客
// */
//
//public class TouService {
//    private TouFragment touFragment;
//
//    private int currentPage = 1;//当前页码
//    private int totalPage = 0;//总页码
//    private Boolean isRefresh = false;//判断是否是刷新
//
//    @Injection
//    TouView tv;
//    private List<Visitor> listData = new ArrayList<Visitor>();
//    private TouMainAdapter touMainAdapter;
//    public RefreshAndload refreshAndload;
//
//
//    public void init(TouFragment touFragment) {
//        this.touFragment = touFragment;
//        initView();
//    }
//
//    /**
//     * 获取数据
//     */
//    public void initData() {
//        //获取缓存的用户信息
//        AppUser appUser = ConfigSP.getUserInfo();
//        Rest rest = new Rest("/appvisitor/list.nla", appUser.getUSER_ID(), appUser.getUSER_TOKEN());
//        rest.addParam("USER_ID", appUser.getUSER_ID());
//        rest.addParam("showCount", "20");
//        rest.addParam("currentPage", String.valueOf(currentPage));
//        rest.post(new Callback() {
//            @Override
//            public void onSuccess(JSONObject jsonObject, String state, String msg) {
//                try {
//                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
//                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
//                    List<Visitor> visitorList = JSON.parseArray(jsonArray.toString(), Visitor.class);
//                    if (isRefresh) {
//                        listData.clear();
//                        isRefresh = false;
//                    }
//                    listData.addAll(visitorList);
//                    touMainAdapter.setListData(listData);
//                    refreshAndload.stopRefreshAndLoad();
//                } catch (Exception exception) {
//                    onError(exception);
//                    exception.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
//                refreshAndload.stopRefreshAndLoad();
//                ToastUtil.showToast(msg);
//            }
//
//            @Override
//            public void onError(Exception exception) {
//                refreshAndload.stopRefreshAndLoad();
//                ToastUtil.noNet();
//            }
//        });
//    }
//
//    public void initView() {
//
//        //添加头部花边必须在设置适配器之前
//        //tv.tou_main_lv.addHeaderView(new View(touFragment.getContext()));
//
//        refreshAndload = new RefreshAndload(touFragment.getContext(), tv.tou_main_lv, tv.tou_main_srl);
//        refreshAndload.setOnRefreshListener(new RefreshAndload.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                isRefresh = true;
//                currentPage = 1;
//                initData();
//            }
//        });
//
//        refreshAndload.setOnLoadListener(new RefreshAndload.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                if (totalPage > 0 && currentPage < totalPage) {
//                    currentPage++;
//                    initData();
//                } else {
//                    refreshAndload.stopRefreshAndLoad(refreshAndload.LOADNO);
//                }
//
//            }
//        });
//        //适配器初始化
//       // touMainAdapter = new TouMainAdapter(touFragment.getContext(),touFragment);
//        tv.tou_main_lv.setAdapter(touMainAdapter);
//
//
//        //条目点击事件
//        tv.tou_main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(touFragment.getContext(), TouDetailActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("Visitor", listData.get(position));
//                intent.putExtras(bundle);
//                touFragment.startActivityForResult(intent, 1024);
//            }
//        });
//
//
//        //进来刷新的调用应放在最后
//        refreshAndload.immediatelyRefresh();
//    }
//
//
//    /**
//     * 同意或者拒绝后，刷新数据
//     */
//    public void refresh() {
//        refreshAndload.immediatelyRefresh();
//    }
//
//
//
//}

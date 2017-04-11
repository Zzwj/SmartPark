//package com.xcoder.smartpark.service.use;
//
//import com.alibaba.fastjson.JSON;
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.smartpark.moudel.AppUser;
//import com.xcoder.smartpark.moudel.BusWrokMoudel;
//import com.xcoder.smartpark.moudel.CarMoudel;
//import com.xcoder.smartpark.network.Callback;
//import com.xcoder.smartpark.network.Rest;
//import com.xcoder.smartpark.util.ConfigSP;
//import com.xcoder.smartpark.util.RefreshAndload;
//import com.xcoder.smartpark.adapter.use.UseMainAdapter;
//import com.xcoder.smartpark.fragment.UseFragment;
//import com.xcoder.smartpark.util.ToastUtil;
//import com.xcoder.smartpark.view.use.UseView;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by xcoder_xz on 2016/12/9 0009.
// * 用车
// */
//
//public class UseService {
//    private UseFragment useFragment;
//    @Injection
//    UseView uv;
//
//    private int currentPage = 1;//当前页码
//    private int totalPage = 0;//总页码
//    private Boolean isRefresh = false;//判断是否是刷新
//    private AppUser userInfo;//用户信息
//
//    private List<CarMoudel> listData = new ArrayList<CarMoudel>();
//    private UseMainAdapter useMainAdapter;
//    public RefreshAndload refreshAndload;
//
//
//    public void init(UseFragment useFragment) {
//        this.useFragment = useFragment;
//        userInfo = ConfigSP.getUserInfo();
//        initView();
//    }
//
//    /**
//     * 初始化界面
//     */
//    public void initView(){
//        //适配器初始化
//        useMainAdapter = new UseMainAdapter(useFragment.getContext());
//        uv.use_main_lv.setAdapter(useMainAdapter);
//        refreshAndload = new RefreshAndload(useFragment.getContext(), uv.use_main_lv, uv.use_main_srl);
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
//            }
//        });
//
//        //进来刷新的调用应放在最后
//        refreshAndload.immediatelyRefresh();
//    }
//
//    /**
//     * 获取数据
//     */
//    public void initData(){
//        Rest rest = new Rest("/appcar/list.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
//        rest.addParam("showCount", "20");
//        rest.addParam("currentPage", String.valueOf(currentPage));
//        rest.post(new Callback() {
//            @Override
//            public void onSuccess(JSONObject jsonObject, String state, String msg) {
//                try {
//                    totalPage = jsonObject.getJSONObject("dataset").getInt("totalPage");
//                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
//                    List<CarMoudel> listBW = JSON.parseArray(jsonArray.toString(), CarMoudel.class);
//                    if (isRefresh) {
//                        listData.clear();
//                        isRefresh = false;
//                    }
//                    listData.addAll(listBW);
//                    useMainAdapter.setListData(listData);
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
//
//    }
//
//
//
//
//}

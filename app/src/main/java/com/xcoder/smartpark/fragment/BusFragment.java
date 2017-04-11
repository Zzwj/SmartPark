//package com.xcoder.smartpark.fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.lib.annotation.event.OnClick;
//import com.xcoder.lib.injection.ViewUtils;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.activity.bus.BusMyOrderActivity;
//import com.xcoder.smartpark.activity.bus.BusTagActivity;
//import com.xcoder.smartpark.app.base.BaseFragment;
//import com.xcoder.smartpark.service.bus.BusService;
//
///**
// * Created by xcoder_xz on 2016/12/6 0006.
// * 班车的fragment
// */
//
//public class BusFragment extends BaseFragment {
//    private View view;
//    @Injection
//    BusService bs;
//
//    @Override
//    public View initLayout(LayoutInflater inflater) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.bus_list_activity, null);
//        }
//        ViewUtils.inject(this, view);//这一句相当于初始化view了
//        return view;
//    }
//
//    /**
//     * 开始数据操作
//     */
//    @Override
//    public void initData() {
//        super.initData();
//        try {
//            bs.init(this);
//            bs.selectFragment(bs.wrokTag);
//        }catch (Exception e){
//            e.printStackTrace();
//           // ToastUtil.showToast(e.getMessage());
//        }
//    }
//
//    @OnClick({R.id.bus_main_tag_tv, R.id.bus_main_home_bt, R.id.bus_main_my_tv, R.id.bus_main_wrok_bt, R.id.bus_main_temporary_bt})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bus_main_tag_tv:
//                //常住标注
//                openActivity(BusTagActivity.class);
//                break;
//            case R.id.bus_main_my_tv:
//                //我的预约
//                openActivity(BusMyOrderActivity.class);
//                break;
//            case R.id.bus_main_home_bt:
//                //回家
//                bs.selectFragment(bs.homeTag);
//                break;
//            case R.id.bus_main_wrok_bt:
//                //上班
//                bs.selectFragment(bs.wrokTag);
//                break;
//            case R.id.bus_main_temporary_bt:
//                //临时
//                bs.selectFragment(bs.temporaryTag);
//                break;
//        }
//    }
//
//    @Override
//    public void closeFragment() {
//
//    }
//}
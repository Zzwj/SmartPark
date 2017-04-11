//package com.xcoder.smartpark.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.lib.annotation.event.OnClick;
//import com.xcoder.lib.injection.ViewUtils;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.activity.HtmlActivity;
//import com.xcoder.smartpark.activity.use.UseMyOrderActivity;
//import com.xcoder.smartpark.app.base.BaseFragment;
//import com.xcoder.smartpark.network.AppNet;
//import com.xcoder.smartpark.service.use.UseService;
//import com.xcoder.smartpark.util.ToastUtil;
//
///**
// * Created by xcoder_xz on 2016/12/6 0006.
// * 用车
// */
//
//public class UseFragment extends BaseFragment {
//    private View view;
//    @Injection
//    UseService us;
//
//    @Override
//    public View initLayout(LayoutInflater inflater) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.use_list_activity, null);
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
//        us.init(this);
//    }
//
//    @OnClick({R.id.use_explain_tv,R.id.use_appointment_tv})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.use_explain_tv:
//                Bundle bundle=new Bundle();
//                bundle.putString("htmlTitle","预约说明");
//                bundle.putString("htmlUrl", AppNet.appNet+AppNet.appnotice);
//                openActivity(HtmlActivity.class,bundle);
//                break;
//            case R.id.use_appointment_tv:
//                openActivity(UseMyOrderActivity.class);
//                break;
//        }
//    }
//
//    @Override
//    public void closeFragment() {
//        //清除此对象
//        if(us.refreshAndload!=null){
//            us.refreshAndload.onDestroy();
//            us.refreshAndload=null;
//        }
//    }
//}

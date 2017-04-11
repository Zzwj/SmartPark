package com.xcoder.smartpark.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.HomeService;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @Injection
    HomeService hs;
    private View view;


    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.home_main_fragment, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了


        return view;
    }

//    @OnClick({R.id.home_ib1,R.id.home_ib2,R.id.home_ib3,R.id.home_ib4,R.id.home_ib5,R.id.home_ib6,R.id.home_ib7,
//            R.id.home_ib8,R.id.home_ib9,R.id.home_ib10,R.id.home_ib11,R.id.home_ib12,R.id.home_ib13,R.id.home_ib14,
//            R.id.home_ib15,R.id.home_ib16,R.id.home_ib17,R.id.home_ib18,R.id.home_ib19,R.id.home_ib20,R.id.home_ib21,
//            R.id.home_ib22,R.id.home_ib23,R.id.home_ib24})
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.home_ib1:
//                //班车
//                Bundle bundle=new Bundle();
//                bundle.putString(StaticString.ACTIVITYSTR,"班车");
//                openActivity(HomeSelectActivity.class,bundle);
//                break;
//            case R.id.home_ib2:
//                //访客
//                Bundle bundle2=new Bundle();
//                bundle2.putString(StaticString.ACTIVITYSTR,"访客");
//                openActivity(HomeSelectActivity.class,bundle2);
//                break;
//            case R.id.home_ib3:
//                //用车
//                Bundle bundle3=new Bundle();
//                bundle3.putString(StaticString.ACTIVITYSTR,"用车");
//                openActivity(HomeSelectActivity.class,bundle3);
//                break;
//            case R.id.home_ib4:
//                //通知公告
//                openActivity(MyMessageActivity.class);
//                break;
//            case R.id.home_ib5:
//                //餐饮服务
//                break;
//            case R.id.home_ib6:
//                //物业服务
//                break;
//            case R.id.home_ib7:
//                //会议室
//                break;
//            case R.id.home_ib8:
//                //跳骚市场
//                break;
//            case R.id.home_ib9:
//                //智慧生活
//                break;
//            case R.id.home_ib10:
//                //信息港园区介绍
//                break;
//            case R.id.home_ib11:
//                //暂无
//                break;
//            case R.id.home_ib12:
//                //暂无
//                break;
//            case R.id.home_ib13:
//                //资产管理
//                openActivity(BLEMainActivity.class);
//                break;
//            case R.id.home_ib14:
//                //巡更管理
//                openActivity(PatrolManActivity.class);
//                break;
//            case R.id.home_ib15:
//                //人员统计
//                Intent intent15 = new Intent(this.getActivity(), HtmlActivity.class);
//                intent15.putExtra("htmlTitle","人员统计");
//                intent15.putExtra("htmlUrl", AppNet.appNet+AppNet.apppatrol);
//                startActivity(intent15);
//                break;
//            case R.id.home_ib16:
//                //用车审批
//                openActivity(UseAuditActivity.class);
//                break;
//            case R.id.home_ib17:
//                //能源管理
//                break;
//            case R.id.home_ib18:
//                //视频监控
//                break;
//            case R.id.home_ib19:
//                //公车司机
//                openActivity(BusDriverActivity.class);
//                break;
//            case R.id.home_ib20:
//                //班车司机
//                Intent intent20 = new Intent(this.getActivity(), HtmlActivity.class);
//                intent20.putExtra("htmlTitle","班车信息");
//                intent20.putExtra("htmlUrl",AppNet.appNet+AppNet.appbus+"?USER_ID="+userInfo.getUSER_ID());
//                startActivity(intent20);
//                break;
//            case R.id.home_ib21:
//                //物业人员
//                break;
//            case R.id.home_ib22:
//                //餐饮人员
//                break;
//            case R.id.home_ib23:
//                //暂无
//                break;
//            case R.id.home_ib24:
//                //暂无
//                break;
//
//        }
//    }


    /**
     * 开始数据操作
     */
    @Override
    public void initData() {
        super.initData();
        hs.init(this);
    }

    @Override
    public void closeFragment() {

    }
}

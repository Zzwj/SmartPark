package com.xcoder.smartpark.service;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.activity.MainActivity;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.activity.other.PatrolManActivity;
import com.xcoder.smartpark.activity.other.UseAuditActivity;
import com.xcoder.smartpark.activity.other.demo.BLEMainActivity;
import com.xcoder.smartpark.fragment.HomeFragment;
import com.xcoder.smartpark.fragment.LinkmanFragment;
import com.xcoder.smartpark.fragment.MessageFragment;
import com.xcoder.smartpark.fragment.MyFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.view.MainView;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 * 这个类用于MainActivity的数据操作
 */

public class MainService implements RadioGroup.OnCheckedChangeListener {
    @Injection
    MainView mv;
    private MainActivity mainActivity;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
//    private TouFragment touFragment;
//    private UseFragment useFragment;
//    private BusFragment busFragment;
    private MyFragment myFragment;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private LinkmanFragment linkmanFragment;
    private AppUser userInfo;

    /**
     * 初始化一下context
     *
     * @param mainActivity
     */
    public void init(MainActivity mainActivity) {
        try {
            this.mainActivity = mainActivity;
            userInfo = ConfigSP.getUserInfo();

            mv.main_other.setOnTouchListener(onTouchImage);
            fragmentManager = mainActivity.getSupportFragmentManager();
            mv.main_rg.setOnCheckedChangeListener(this);
            mv.main_rg.check(R.id.main_rb1);
            initRoleView();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 初始化角色块
     *
     * @throws Exception
     */
    public void initRoleView() throws Exception {
        if (userInfo != null) {
            final String BIANMA = userInfo.getBIANMA();// 角色编码
            String RNAME = userInfo.getRNAME();//角色名
            //------------点击事件处理
            View.OnClickListener onclick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.other_patrol_man_ll://巡逻人员
                            Intent intent = new Intent(mainActivity, PatrolManActivity.class);
                            mainActivity.startActivity(intent);
                            break;
                        case R.id.other_bus_ll://班车司机
                            Intent intent2 = new Intent(mainActivity, HtmlActivity.class);
                            intent2.putExtra("htmlTitle","班车信息");
                            intent2.putExtra("htmlUrl",AppNet.appNet+AppNet.appbus+"?USER_ID="+userInfo.getUSER_ID());
                            mainActivity.startActivity(intent2);
                            break;
                        case R.id.other_driver_ll://公车司机
                            Intent intent4 = new Intent(mainActivity, BusDriverActivity.class);
                            mainActivity.startActivity(intent4);
                            break;
                        case R.id.other_user_ll://领导编码
                            Intent intent5 = new Intent(mainActivity, HtmlActivity.class);
                            intent5.putExtra("htmlTitle","人员统计");
                            intent5.putExtra("htmlUrl", AppNet.appNet+AppNet.apppatrol);
                            mainActivity.startActivity(intent5);
                            break;
                        case R.id.other_audit_ll://生产用车审批员
                            Intent intent3 = new Intent(mainActivity, UseAuditActivity.class);
                            mainActivity.startActivity(intent3);
                            break;
                        case R.id.other_money_ll://资产盘点员
                            Intent intent6 = new Intent(mainActivity, BLEMainActivity.class);
                            mainActivity.startActivity(intent6);
                            break;
                    }
                    //点击完，要隐藏起来
                    mv.main_other_bt.setVisibility(View.GONE);
                    mv.main_other.setImageResource(R.drawable.use_mian_imagebt);
                }
            };

            if (TextUtils.equals(BIANMA, "XGY")) {//巡逻人员
                mv.other_patrol_man_ll.setVisibility(View.VISIBLE);
                mv.other_patrol_man_ll.setOnClickListener(onclick);

            } else if (TextUtils.equals(BIANMA, "LSJ")) {//班车司机
                mv.other_bus_ll.setVisibility(View.VISIBLE);
                mv.other_bus_ll.setOnClickListener(onclick);

            } else if (TextUtils.equals(BIANMA, "GCSJ")) {//公车司机
                mv.other_driver_ll.setVisibility(View.VISIBLE);
                mv.other_driver_ll.setOnClickListener(onclick);

            } else if (TextUtils.equals(BIANMA, "LEADER")) {//领导编码
                mv.other_user_ll.setVisibility(View.VISIBLE);
                mv.other_user_ll.setOnClickListener(onclick);

                //如果角色是LEADER，显示用车审批按钮
                mv.other_audit_ll.setVisibility(View.VISIBLE);
                mv.other_audit_ll.setOnClickListener(onclick);
                ConfigSP.setIsLEADER(true);//默认是false，如果是领导角色，设置为true

                //领导角色显示用车审批功能
                mv.other_audit_ll.setVisibility(View.VISIBLE);
                mv.other_audit_ll.setOnClickListener(onclick);

            } else if (TextUtils.equals(BIANMA, "CSPY")) {//生产用车审批员
                mv.other_audit_ll.setVisibility(View.VISIBLE);
                mv.other_audit_ll.setOnClickListener(onclick);
                ConfigSP.setIsLEADER(false);//默认是false，如果是领导角色，设置为true

            } else if(TextUtils.equals(BIANMA, "ZW_INVENTORY")) {//资产盘点员
                mv.other_money_ll.setVisibility(View.VISIBLE);
                mv.other_money_ll.setOnClickListener(onclick);

            } else {//普通员工
                //如果是普通员工，角色按钮都不要显示

                //普通用户暂时显示人员位置
//                mv.main_other.setVisibility(View.GONE);
//                mv.main_other_bt.setVisibility(View.GONE);
                mv.other_user_ll.setVisibility(View.VISIBLE);
                mv.other_user_ll.setOnClickListener(onclick);
            }

            //暂时全部角色都显示人员定位
            mv.other_user_ll.setVisibility(View.VISIBLE);
            mv.other_user_ll.setOnClickListener(onclick);


            //正式的时候需要删除的代码
//            mv.other_patrol_man_ll.setVisibility(View.VISIBLE);
//            mv.other_patrol_man_ll.setOnClickListener(onclick);
//
//            mv.other_driver_ll.setVisibility(View.VISIBLE);
//            mv.other_driver_ll.setOnClickListener(onclick);
//
//            mv.other_audit_ll.setVisibility(View.VISIBLE);
//            mv.other_audit_ll.setOnClickListener(onclick);
//
//            mv.other_bus_ll.setVisibility(View.VISIBLE);
//            mv.other_bus_ll.setOnClickListener(onclick);
//
//            mv.other_user_ll.setVisibility(View.VISIBLE);
//            mv.other_user_ll.setOnClickListener(onclick);
        }
    }



    /**
     * 导航栏按钮选择
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);/* 想要显示一个fragment,先隐藏所有fragment，防止重叠 */
        switch (checkedId) {
            case R.id.main_rb1:
//                  /* 如果fragment1已经存在则将其显示出来 */
//                if (touFragment != null)
//                    fragmentTransaction.show(touFragment);
//            /* 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add */
//                else {
//                    touFragment = new TouFragment();
//                    fragmentTransaction.add(R.id.mian_fragment, touFragment);
//                }
                if(homeFragment!=null)
                    fragmentTransaction.show(homeFragment);
                else{
                    homeFragment=new HomeFragment();
                    fragmentTransaction.add(R.id.mian_fragment,homeFragment);
                }
                break;
            case R.id.main_rb2:
//                if (useFragment != null)
//                    fragmentTransaction.show(useFragment);
//                else {
//                    useFragment = new UseFragment();
//                    fragmentTransaction.add(R.id.mian_fragment, useFragment);
//                }

                if (messageFragment != null)
                    fragmentTransaction.show(messageFragment);
                else {
                    messageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.mian_fragment, messageFragment);
                }
                break;
            case R.id.main_rb3:
//                if (busFragment != null)
//                    fragmentTransaction.show(busFragment);
//                else {
//                    busFragment = new BusFragment();
//                    fragmentTransaction.add(R.id.mian_fragment, busFragment);
//                }

                if (linkmanFragment != null)
                    fragmentTransaction.show(linkmanFragment);
                else {
                    linkmanFragment = new LinkmanFragment();
                    fragmentTransaction.add(R.id.mian_fragment, linkmanFragment);
                }
                break;
            case R.id.main_rb4:
                if (myFragment != null)
                    fragmentTransaction.show(myFragment);
                else {
                    myFragment = new MyFragment();
                    fragmentTransaction.add(R.id.mian_fragment, myFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    /**
     * 隐藏fagment
     */
    public void hideFragment(FragmentTransaction ft) {
        if (homeFragment != null) {
            ft.hide(homeFragment);
        }
        if (messageFragment != null) {
            ft.hide(messageFragment);
        }
        if (linkmanFragment != null) {
            ft.hide(linkmanFragment);
        }
        if (myFragment != null) {
            ft.hide(myFragment);
        }
    }

    int lastX;//按钮当前位置的坐标，或者移动后停下来的坐标
    int lastY;
    //是否执行点击事件
    Boolean isclick = false;
    //当前父布局的宽高
    int screenWidth;
    int screenHeight;
    int marLeft = -1;
    //给父布局记录 按钮所在的位置，避免，移动后，点击其他地方，位置被还原
    RelativeLayout.LayoutParams rLayout;
    //记录这一次移动位置，判断，移动的大小，如果小于10，就是点击事件，优化用户体验
    int moveX;
    int moveY;
    //用来做松开后，回到原点的操作
    int t;

    /**
     * 悬浮按钮移动，
     */
    View.OnTouchListener onTouchImage = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int ea = event.getAction();
            switch (ea) {
                case MotionEvent.ACTION_DOWN:
                    if (rLayout == null) {
                        int dpN = Utils.dpToPx(mainActivity, 66);
                        rLayout = new RelativeLayout.LayoutParams(dpN, dpN);
                    }
                    isclick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解
                    moveX = (lastX = (int) event.getRawX());
                    moveY = (lastY = (int) event.getRawY());//按钮初始的横纵坐标
                    marLeft = Utils.dpToPx(mainActivity, 37);
                    screenWidth = mv.main_layout.getWidth();
                    screenHeight = mv.main_layout.getHeight();
                    break;
                case MotionEvent.ACTION_MOVE:
                    isclick = true;//当按钮被移动的时候设置isclick为true
                    int dx = (int) event.getRawX() - lastX;
                    int dy = (int) event.getRawY() - lastY;//按钮被移动的距离
                    int l = v.getLeft() + dx;
                    int b = v.getBottom() + dy;
                    int r = v.getRight() + dx;
                    t = v.getTop() + dy;
                    if (l < 0) {//处理按钮被移动到上下左右四个边缘时的情况，决定着按钮不会被移动到屏幕外边去
                        l = 0;
                        r = l + v.getWidth();
                    }
                    if (t < 0) {
                        t = 0;
                        b = t + v.getHeight();
                    }
                    if (r > screenWidth) {
                        r = screenWidth;
                        l = r - v.getWidth();
                    }
                    if (b > screenHeight) {
                        b = screenHeight;
                        t = b - v.getHeight();
                    }
                    // v.layout(l, t, r, b);
                    rLayout.setMargins(l, t, 0, 0);
                    v.setLayoutParams(rLayout);
                    //
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                    // mv.mian_other.setXY(true, l, t, r, b);
                    v.postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    int moX = (int) event.getRawX() - moveX;
                    int moY = (int) event.getRawY() - moveY;//按钮被移动的距离
                    if (moX < 10 && moY < 10 && moX > -10 && moY > -10) {
                        isclick = false;
                    } else {
                        isclick = true;
                    }
                    if (moX != 0 || moY != 0) {
                        //松开后，使点回到左边
                        rLayout.setMargins(marLeft > 0 ? marLeft : 50, t, 0, 0);
                        v.setLayoutParams(rLayout);
                    }
                    //把这一次的坐标赋值
                    moveX = lastX;
                    moveY = lastY;
                    break;
                default:
                    //isclick = false;
                    break;
            }
            return isclick;
        }
    };

}

package com.xcoder.smartpark.service.my;

import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.my.MyInfoActivity;
import com.xcoder.smartpark.fragment.MyFragment;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.view.my.MyView;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class MyService {
    private MyFragment myFragment;
    @Injection
    MyView mv;

    public void init(MyFragment myFragment) {
        this.myFragment = myFragment;
        initView();
    }


    /**
     * 初始化页面信息
     */
    public void initView() {

        AppUser appUser = ConfigSP.getUserInfo();

        //注意：如果使用了其他第三方的imageview，得使用下面这种方式加载图片

        Glide.with(myFragment)
                .load(AppNet.API_IMAGE + appUser.getHEADIMGURL())
                .placeholder(R.drawable.my_defaulthead_ico)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mv.my_main_head_cir.setImageDrawable(resource);
                    }
                });

        mv.my_main_name_tv.setText(appUser.getUSERNAME());

    }


    /**
     * 刷新页面信息
     */
    public void refresh() {
        AppUser appUser = ConfigSP.getUserInfo();
        //注意：如果使用了其他第三方的imageview，得使用下面这种方式加载图片
        Glide.with(myFragment)
                .load(AppNet.API_IMAGE + appUser.getHEADIMGURL())
                .placeholder(R.drawable.my_defaulthead_ico)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        mv.my_main_head_cir.setImageDrawable(resource);
                    }
                });

        mv.my_main_name_tv.setText(appUser.getUSERNAME());
    }


}

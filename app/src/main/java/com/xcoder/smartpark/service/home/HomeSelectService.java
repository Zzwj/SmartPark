package com.xcoder.smartpark.service.home;

import android.text.TextUtils;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.activity.home.HomeSelectActivity;
import com.xcoder.smartpark.util.StaticString;
import com.xcoder.smartpark.view.home.HomeSelectView;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * * 班车，访客，用车的选择
 */

public class HomeSelectService {
    private HomeSelectActivity hsactivity;

    @Injection
    HomeSelectView hsv;

    public void init(HomeSelectActivity hsactivity) {
        this.hsactivity = hsactivity;
        initView();
    }

    public void initView(){
        if(hsactivity.getIntent()!=null){
            String actStr=hsactivity.getIntent().getStringExtra(StaticString.ACTIVITYSTR);
            if(!TextUtils.isEmpty(actStr)){
                if(TextUtils.equals("班车",actStr)){
                    hsv.select_bc_ll.setVisibility(View.VISIBLE);
                }else if(TextUtils.equals("访客",actStr)){
                    hsv.select_fk_ll.setVisibility(View.VISIBLE);
                }else if(TextUtils.equals("用车",actStr)){
                    hsv.select_yc_ll.setVisibility(View.VISIBLE);
                }
                hsv.home_select_headtv.setText(actStr);
            }
        }

    }
}

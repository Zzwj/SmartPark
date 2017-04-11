package com.xcoder.smartpark.adapter.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xcoder.lib.adapter.ListBaseAdapter;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.fragment.HomeFragment;
import com.xcoder.smartpark.moudel.HomeGvEntity;

/**
 * Created by xcoder_xz on 2017/4/1 0001.
 * 首页图标
 */

public class HomeGvAdapter extends ListBaseAdapter<HomeGvEntity> {
    private HomeFragment homeFragment;

    public HomeGvAdapter(HomeFragment homeFragment){
        this.homeFragment=homeFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(SmartParkApplication.getAppContext(), R.layout.home_item_gv);
        }
        ImageView home_item_iv = ListViewHolder.get(convertView, R.id.home_item_iv);

        Glide.with(homeFragment).load(listData.get(position).getTypeDrawable()).placeholder(R.drawable.home_bg_img).
                error(R.drawable.home_bg_img).fitCenter()
                .into(home_item_iv);

        return convertView;
    }
}

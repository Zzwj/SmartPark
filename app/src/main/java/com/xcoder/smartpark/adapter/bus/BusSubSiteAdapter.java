package com.xcoder.smartpark.adapter.bus;

import android.widget.TextView;

import com.xcoder.lib.adapter.recyclerview.RvDataAdapter;
import com.xcoder.lib.adapter.recyclerview.RvViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.moudel.BusSubSiteMoudel;
import com.xcoder.smartpark.widget.view.CircleImageView;

/**
 * Created by xcoder_cz on 2016/12/22 0022.
 * 班车--预约---站点
 */

public class BusSubSiteAdapter extends RvDataAdapter<BusSubSiteMoudel> {
    public int indexPos = -1;

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.bus_sub_site_rv_item;
    }




    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        CircleImageView site_rv_item_bg = holder.getView(R.id.site_rv_item_bg);
        TextView site_rv_item_number = holder.getView(R.id.site_rv_item_number);
        TextView site_rv_item_name = holder.getView(R.id.site_rv_item_name);
        site_rv_item_name.setText(mdata.get(position).getSITE_NAME());
        site_rv_item_number.setText(mdata.get(position).getCOUNT() + "人");
        if (position == indexPos) {
            site_rv_item_bg.setImageResource(R.drawable.app_green_circle_ico);
        } else {
            site_rv_item_bg.setImageResource(R.drawable.app_blue_circle_ico);
        }
    }
}

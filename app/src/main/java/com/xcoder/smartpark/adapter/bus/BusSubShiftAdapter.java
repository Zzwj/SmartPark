package com.xcoder.smartpark.adapter.bus;

import android.widget.TextView;

import com.xcoder.lib.adapter.recyclerview.RvDataAdapter;
import com.xcoder.lib.adapter.recyclerview.RvViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.moudel.BusArriveTimes;
import com.xcoder.smartpark.moudel.BusMessageMoudle;

/**
 * Created by xcoder_xz on 2016/12/22 0022.
 * 班车---预约---时间段
 */

public class BusSubShiftAdapter extends RvDataAdapter<BusMessageMoudle> {
    public int indexPos = -1;

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.bus_sub_shift_rv_item;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        BusMessageMoudle ap = mdata.get(position);
        TextView shift_rv_item_time = holder.getView(R.id.shift_rv_item_time);
        shift_rv_item_time.setText(mdata.get(position).getSEND_TIME());
        if (position == indexPos) {
            shift_rv_item_time.setTextColor(shift_rv_item_time.getContext().getResources().getColor(R.color.app_red_color));
        } else {
            shift_rv_item_time.setTextColor(shift_rv_item_time.getContext().getResources().getColor(R.color.app_txt_gray));
        }

    }
}

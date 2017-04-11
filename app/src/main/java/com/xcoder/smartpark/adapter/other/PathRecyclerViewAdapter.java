package com.xcoder.smartpark.adapter.other;

import android.widget.TextView;

import com.xcoder.lib.adapter.recyclerview.RvDataAdapter;
import com.xcoder.lib.adapter.recyclerview.RvViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.moudel.BusMessageMoudle;
import com.xcoder.smartpark.moudel.OtherPatrolManLineMoudle;

import static com.xcoder.smartpark.R.id.shift_rv_item_time;

/**
 * Created by xcoder_jk on 2016/12/22 0022.
 * 巡更->横向滑动路线
 */

public class PathRecyclerViewAdapter extends RvDataAdapter<OtherPatrolManLineMoudle> {
    public int indexPos = -1;

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.other_patroman_rv_item;
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, int position) {
        OtherPatrolManLineMoudle ap = mdata.get(position);
        TextView other_patrolman_line_rv = holder.getView(R.id.other_patrolman_line_rv);
        other_patrolman_line_rv.setText(mdata.get(position).getPATROLLINE_NAME());
        if (position == indexPos) {
            other_patrolman_line_rv.setTextColor(other_patrolman_line_rv.getContext().getResources().getColor(R.color.app_green_color));
        } else {
            other_patrolman_line_rv.setTextColor(other_patrolman_line_rv.getContext().getResources().getColor(R.color.app_txt_gray));
        }

    }
}

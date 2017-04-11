package com.xcoder.smartpark.adapter.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.OtherPatrolManSituationMoudle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangkun on 16/12/30.
 */

public class PatrolManAdapter extends BaseAdapter{

    private Context context;

    public PatrolManAdapter(Context context) {
        this.context = context;
    }

    public List<OtherPatrolManSituationMoudle> listData = new ArrayList<OtherPatrolManSituationMoudle>();

    public void setListData(List<OtherPatrolManSituationMoudle> listData) {
        if(listData != null) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.other_patrolman_list_item, null);
        }

        ImageView other_patrolman_idot_iv = ListViewHolder.get(convertView, R.id.other_patrolman_idot_iv);
        TextView other_patrolman_station_tv = ListViewHolder.get(convertView, R.id.other_patrolman_station_tv);
        TextView other_patrolman_status_iv = ListViewHolder.get(convertView, R.id.other_patrolman_status_iv);
        TextView other_patrolman_time_tv = ListViewHolder.get(convertView, R.id.other_patrolman_time_tv);

        if(listData.get(position).getIS_PATROL().equals("0")) {
            other_patrolman_idot_iv.setImageResource(R.drawable.app_orange_circle_ico);
            other_patrolman_status_iv.setText("未巡更");
            other_patrolman_time_tv.setText("");
        }else if(listData.get(position).getIS_PATROL().equals("1")) {
            other_patrolman_idot_iv.setImageResource(R.drawable.app_blue_circle_ico);
            other_patrolman_status_iv.setText("已巡更");
            other_patrolman_time_tv.setText(listData.get(position).getUPDATE_TIME());
        }
        other_patrolman_station_tv.setText(""+listData.get(position).getRADDRESS());
        return convertView;
    }
}

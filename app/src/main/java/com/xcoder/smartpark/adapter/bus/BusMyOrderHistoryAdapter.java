package com.xcoder.smartpark.adapter.bus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 我的预约  历史预约
 */

public class BusMyOrderHistoryAdapter extends BaseAdapter {
    private List<BusHistoryMoudle> listData = new ArrayList<BusHistoryMoudle>();
    private Context context;


    public void setListData(List<BusHistoryMoudle> listData) {
        if (listData != null ) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    public BusMyOrderHistoryAdapter() {

    }

    public BusMyOrderHistoryAdapter(Context context) {
        this.context = context;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext())
                    .inflate(R.layout.bus_my_order_history_list_item, null);
        }

        ImageView have_lv_item_long = ListViewHolder.get(convertView, R.id.have_lv_item_long);
        TextView have_lv_item_name = ListViewHolder.get(convertView, R.id.have_lv_item_name);
        TextView have_lv_item_car = ListViewHolder.get(convertView, R.id.have_lv_item_car);
        TextView have_lv_item_car_det = ListViewHolder.get(convertView, R.id.have_lv_item_car_det);
        TextView have_lv_item_time = ListViewHolder.get(convertView, R.id.have_lv_item_time);
        TextView have_lv_item_people = ListViewHolder.get(convertView, R.id.have_lv_item_people);
        TextView have_lv_item_phone = ListViewHolder.get(convertView, R.id.have_lv_item_phone);
        TextView have_lv_item_site = ListViewHolder.get(convertView, R.id.have_lv_item_site);

        have_lv_item_name.setText(listData.get(position).getLINE_NAME());
        have_lv_item_car.setText("");//线路起始站目前没有
        have_lv_item_site.setText("上车地点:"+listData.get(position).getSITE_NAME());
        have_lv_item_car_det.setText(listData.get(position).getMODEL() + " " + listData.get(position).getCOLOR());
        //have_lv_item_time.setText("预约时间："+ listData.get(position).getRESERVE_TIME());
        have_lv_item_people.setText("司机："+listData.get(position).getDRIVER_NAME());
        have_lv_item_phone.setText(listData.get(position).getDRIVER_TEL());

        if(listData.get(position).getTYPE().equals("1")) {//0预约当天 1长期预约
            have_lv_item_time.setVisibility(View.GONE);
            have_lv_item_long.setVisibility(View.VISIBLE);
        }else {
            have_lv_item_time.setText("预约时间：" + listData.get(position).getRESERVE_TIME());
            have_lv_item_long.setVisibility(View.GONE);
        }

        return convertView;
    }

}

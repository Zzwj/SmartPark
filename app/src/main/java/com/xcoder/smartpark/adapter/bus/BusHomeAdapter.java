package com.xcoder.smartpark.adapter.bus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusSubscribeActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.BusArriveTimes;
import com.xcoder.smartpark.moudel.BusHomeMoudel;
import com.xcoder.smartpark.moudel.BusMessageMoudle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/21 0021.
 * 班车 --- 回家的适配器
 */

public class BusHomeAdapter extends BaseAdapter {
    private Context context;
    private List<BusHomeMoudel> listData = new ArrayList<BusHomeMoudel>();


    public void setListData(List<BusHomeMoudel> listData) {
        if (listData != null) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    public BusHomeAdapter() {

    }

    public BusHomeAdapter(Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.bus_home_list_item, null);
        }
        ImageView bus_lv_item_iv = ListViewHolder.get(convertView, R.id.bus_lv_item_iv);//班车图片
        TextView bus_lv_item_bus = ListViewHolder.get(convertView, R.id.bus_lv_item_bus);//班车名字
        TextView bus_lv_shift = ListViewHolder.get(convertView, R.id.bus_lv_shift);//班车车次
        TextView bus_lv_item_time = ListViewHolder.get(convertView, R.id.bus_lv_item_time);//班车时间段
        TextView bus_lv_item_order = ListViewHolder.get(convertView, R.id.bus_lv_item_order);//预约
        TextView bus_lv_item_longorder = ListViewHolder.get(convertView, R.id.bus_lv_item_longorder);//长期预约

         BusHomeMoudel bhm = listData.get(position);
        bus_lv_item_bus.setText(bhm.getLINE_NAME());
        bus_lv_shift.setText(bhm.getSTART_STATION() + "-" + bhm.getEND_STATION());
        if (bhm.getRESULT().size()> 0) {
            StringBuffer sb = new StringBuffer();
            for (BusMessageMoudle time : bhm.getRESULT()) {
                sb.append(time.getSEND_TIME()+" ");
            }
            bus_lv_item_time.setText(sb);
        }else{
            bus_lv_item_time.setText("暂无时间点");
        }

        //预约的点击
        bus_lv_item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusSubscribeActivity.class);
                BusHomeMoudel busHomeMoudel = listData.get(position);
                intent.putExtra("BusHomeMoudel", busHomeMoudel);
                intent.putExtra("TYPE","0");//当天预约
                context.startActivity(intent);
            }
        });
        //长期预约的点击
        bus_lv_item_longorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusSubscribeActivity.class);
                BusHomeMoudel busHomeMoudel = listData.get(position);
                intent.putExtra("BusHomeMoudel", busHomeMoudel);
                intent.putExtra("TYPE","1");//长期预约
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

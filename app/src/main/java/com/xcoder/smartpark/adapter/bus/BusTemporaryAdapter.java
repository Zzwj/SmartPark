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
import com.xcoder.smartpark.moudel.BusTemPoraryMoudel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/21 0021.
 * 班车---临时
 */

public class BusTemporaryAdapter extends BaseAdapter {
    private Context context;
    private List<BusTemPoraryMoudel> listData = new ArrayList<BusTemPoraryMoudel>();


    public void setListData(List<BusTemPoraryMoudel> listData) {
        if (listData != null ) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }


    public BusTemporaryAdapter() {

    }

    public BusTemporaryAdapter(Context context) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.bus_temporary_list_item, null);
        }
        ImageView bus_lv_item_iv = ListViewHolder.get(convertView, R.id.bus_lv_item_iv);//班车图片
        TextView bus_lv_item_bus = ListViewHolder.get(convertView, R.id.bus_lv_item_bus);//班车名字
        TextView bus_lv_shift = ListViewHolder.get(convertView, R.id.bus_lv_shift);//班车车次
        TextView bus_lv_item_time = ListViewHolder.get(convertView, R.id.bus_lv_item_time);//班车时间段
        TextView bus_lv_item_order = ListViewHolder.get(convertView, R.id.bus_lv_item_order);//预约
        TextView bus_lv_item_longorder = ListViewHolder.get(convertView, R.id.bus_lv_item_longorder);//长期预约

//         BusTemPoraryMoudel btm = listData.get(position);
//        bus_lv_item_bus.setText(btm.getBUS_NAME());
//        bus_lv_shift.setText(btm.getLINE_NAME());
//        if (btm.getArriveTimes().size()> 0) {
//            StringBuffer sb = new StringBuffer();
//            for (BusArriveTimes time : btm.getArriveTimes()) {
//                sb.append(time.getARRIVAL_TIME()+" ");
//            }
//            bus_lv_item_time.setText(sb);
//        }else{
//            bus_lv_item_time.setText("暂无时间点");
//        }
//
//        //预约的点击
//        bus_lv_item_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BusSubscribeActivity.class);
//                BusTemPoraryMoudel busTemPoraryMoudel = listData.get(position);
//                intent.putExtra("BusTemPoraryMoudel", busTemPoraryMoudel);
//                context.startActivity(intent);
//            }
//        });
//        //长期预约的点击
//        bus_lv_item_longorder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, BusLongSubscribeActivity.class);
//                BusTemPoraryMoudel busTemPoraryMoudel = listData.get(position);
//                intent.putExtra("BusTemPoraryMoudel", busTemPoraryMoudel);
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }
}

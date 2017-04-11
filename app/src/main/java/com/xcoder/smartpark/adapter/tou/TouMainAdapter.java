package com.xcoder.smartpark.adapter.tou;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.tou.TouDetailActivity;
import com.xcoder.smartpark.activity.tou.TouListActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.Visitor;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/13 0013.
 * 访客主界面的listviewAdapter
 */

public class TouMainAdapter extends BaseAdapter {
    private TouListActivity touListActivity;


    public TouMainAdapter(TouListActivity touListActivity) {
        this.touListActivity = touListActivity;
    }


    public List<Visitor> listData = new ArrayList<Visitor>();

    public void setListData(List<Visitor> listData) {
        if (listData != null) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.tou_main_list_item, null);
        }
      /*  TextView tv1 = ListViewHolder.get(convertView, R.id.tv1);
        tv1.setText(listData.get(position));*/

        TextView tou_lv_item_name = ListViewHolder.get(convertView, R.id.tou_lv_item_name);
        TextView tou_lv_item_status = ListViewHolder.get(convertView, R.id.tou_lv_item_status);
        TextView tou_lv_item_status_tv = ListViewHolder.get(convertView, R.id.tou_lv_item_status_tv);
        TextView tou_lv_item_phone_tv = ListViewHolder.get(convertView, R.id.tou_lv_item_phone_tv);
        TextView tou_lv_item_time_tv = ListViewHolder.get(convertView, R.id.tou_lv_item_time_tv);

        tou_lv_item_name.setText(listData.get(position).getNAME());

        if (listData.get(position).getAUDIT_STATUS().equals("0")) {
            tou_lv_item_status.setText("待审核");
            tou_lv_item_status.setTextColor(touListActivity.getResources().getColor(R.color.app_txt_gray));

            tou_lv_item_status_tv.setBackgroundResource(R.drawable.app_lv_item_red_selector);
            tou_lv_item_status_tv.setText("详细信息");
        } else if (listData.get(position).getAUDIT_STATUS().equals("1")) {
            tou_lv_item_status.setText("已同意");
            tou_lv_item_status.setTextColor(touListActivity.getResources().getColor(R.color.app_green_color));

            tou_lv_item_status_tv.setBackgroundResource(R.drawable.app_lv_item_green_selector);
            tou_lv_item_status_tv.setText("分享通行证");
        } else if (listData.get(position).getAUDIT_STATUS().equals("2")) {
            tou_lv_item_status.setText("已拒绝");
            tou_lv_item_status.setTextColor(touListActivity.getResources().getColor(R.color.red));

            tou_lv_item_status_tv.setBackgroundResource(R.drawable.app_lv_item_red_selector);
            tou_lv_item_status_tv.setText("详细信息");
        }


        tou_lv_item_phone_tv.setText(listData.get(position).getPHONE());

        tou_lv_item_time_tv.setText(listData.get(position).getVISITOR_TIME());



        tou_lv_item_status_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listData.get(position).getAUDIT_STATUS().equals("1")) {
                    EventBus.getDefault().post(listData.get(position));
                } else {
                    Intent intent = new Intent(touListActivity, TouDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Visitor", listData.get(position));
                    intent.putExtras(bundle);
                    touListActivity.startActivityForResult(intent, 1024);
                }
            }
        });

        return convertView;
    }
}

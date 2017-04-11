package com.xcoder.smartpark.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.UserIdentity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-jk on 2016/12/14
 * 用户选择个人信息listviewAdapter
 */

public class MyInfoChooseAdapter extends BaseAdapter {
    private Context context;

    public MyInfoChooseAdapter(){

    }

    public MyInfoChooseAdapter(Context context){
        this.context=context;
    }


    public List<UserIdentity> listData = new ArrayList<UserIdentity>();

    public void setListData(List<UserIdentity> listData) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.my_myinfo_choose_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.my_myinfo_item_tv);
        tv.setText(listData.get(position).getNAME());
        return convertView;
    }
}

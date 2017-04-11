package com.xcoder.smartpark.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-jk on 2016/12/14
 * 用车界面的listviewAdapter
 */

public class MyMoneyAdapter extends BaseAdapter {
    private Context context;

    public MyMoneyAdapter(){

    }

    public MyMoneyAdapter(Context context){
        this.context=context;
    }


    public List<String> listData = new ArrayList<String>();

    public void setListData(List<String> listData) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.my_mymoney_list_item, null);
        }
        return convertView;
    }
}

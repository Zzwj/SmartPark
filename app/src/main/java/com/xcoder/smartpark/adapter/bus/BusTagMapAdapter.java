package com.xcoder.smartpark.adapter.bus;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/23 0023.
 * 班车---常驻点标注数据
 */

public class BusTagMapAdapter extends BaseAdapter {
    private List<Tip> listData = new ArrayList<Tip>();

    public void setListData(List<Tip> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    private Context context;

    public BusTagMapAdapter() {

    }

    public BusTagMapAdapter(Context context) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.bus_tag_map_lv_item, null);
        }
        TextView bus_tag_item_title = ListViewHolder.get(convertView, R.id.bus_tag_item_title);
        TextView wrap_content = ListViewHolder.get(convertView, R.id.bus_tag_item_content);

        Tip tip = listData.get(position);
        //表示该提示词是一个真实存在的 POI
//        a:PoiID
//        b:LatLonPoint
//        c:Name
//        d:District
//        e:Adcode
//        f:Address
        if (tip.getPoiID() != null) {
            bus_tag_item_title.setText(tip.getName());
            wrap_content.setText(TextUtils.isEmpty(tip.getDistrict())? "":tip.getDistrict());
        }
        return convertView;
    }


}

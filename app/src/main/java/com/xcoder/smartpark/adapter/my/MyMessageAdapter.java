package com.xcoder.smartpark.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListBaseAdapter;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.MessageMoudel;

/**
 * Created by xcoder_xz on 2017/1/7 0007.
 * 我得公告列表
 */

public class MyMessageAdapter extends ListBaseAdapter<MessageMoudel> {
    private Context context;

    public MyMessageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).
                    inflate(R.layout.my_message_lv_item, null);
        }
        TextView message_title = ListViewHolder.get(convertView, R.id.message_title);
        TextView message_time = ListViewHolder.get(convertView, R.id.message_time);
        TextView message_context = ListViewHolder.get(convertView, R.id.message_context);

        message_title.setText(listData.get(position).getTITLE());
        message_time.setText(listData.get(position).getSENDTIME());
        message_context.setText(listData.get(position).getCONTENT());
        return convertView;
    }
}

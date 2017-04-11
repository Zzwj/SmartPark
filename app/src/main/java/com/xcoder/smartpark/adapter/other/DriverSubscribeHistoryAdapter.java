package com.xcoder.smartpark.adapter.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListBaseAdapter;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.SubscribeHistoryMoudle;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 * * 其他--公车---预约信息---预约中的适配器
 */

public class DriverSubscribeHistoryAdapter extends ListBaseAdapter<SubscribeHistoryMoudle> {
    private Context context;

    public DriverSubscribeHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).
                    inflate(R.layout.other_driver_subscribe_history_lv_item, null);
        }

        TextView history_time_begin = ListViewHolder.get(convertView, R.id.history_time_begin);//开始时间
        TextView history_time_end = ListViewHolder.get(convertView, R.id.history_time_end);//结束时间
        TextView history_people = ListViewHolder.get(convertView, R.id.history_people);//预约人
        TextView history_phone = ListViewHolder.get(convertView, R.id.history_phone);//联系电话
        TextView history_status = ListViewHolder.get(convertView, R.id.history_status);//状态

        history_time_begin.setText("开始时间:" + listData.get(position).getRESERVE_START_TIME());
        history_time_end.setText("结束时间:"+ listData.get(position).getRESERVE_END_TIME());
        history_people.setText(listData.get(position).getUSER_NAME());
        history_phone.setText(listData.get(position).getPHONE());

        //0待审核 1未出行 2已出行 3 审核未通过
        if(listData.get(position).getSTATUS().equals("0")) {
            history_status.setText("待审核");
        }else if(listData.get(position).getSTATUS().equals("1")) {
            history_status.setText("未出行");
        }else if(listData.get(position).getSTATUS().equals("2")) {
            history_status.setText("已出行");
        }else if(listData.get(position).getSTATUS().equals("3")) {
            history_status.setText("未通过");
        }else if(listData.get(position).getSTATUS().equals("4")) {
            history_status.setText("已上车");
        } else {
            history_status.setText("状态"+listData.get(position).getSTATUS());
        }


        try {
            TextView going_cartype_tv = ListViewHolder.get(convertView,R.id.going_cartype_tv);
            TextView going_carnum_tv = ListViewHolder.get(convertView,R.id.going_carnum_tv);
            TextView going_laji_tv = ListViewHolder.get(convertView, R.id.going_laji_tv);

            if(listData.get(position).getCAR_TYPE().equals("0")) {
                going_cartype_tv.setText("公车");
            }else if(listData.get(position).getCAR_TYPE().equals("1")) {
                going_cartype_tv.setText("专车");
            }
            going_carnum_tv.setText(listData.get(position).getNUMBER() + "  " + listData.get(position).getCAR_BOX());
            going_laji_tv.setText(listData.get(position).getBRAND() + "  " + listData.get(position).getMODEL() + " "+listData.get(position).getCOLOR());//品牌+ 型号
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}

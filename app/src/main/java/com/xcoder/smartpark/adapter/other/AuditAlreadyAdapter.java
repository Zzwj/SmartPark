package com.xcoder.smartpark.adapter.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.OtherAuditAlreadyMoudle;
import com.xcoder.smartpark.moudel.OtherAuditWaitMoudle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/30 0030.
 * 用车审核----未审核
 */

public class AuditAlreadyAdapter extends BaseAdapter{
    private List<OtherAuditAlreadyMoudle> listData = new ArrayList<OtherAuditAlreadyMoudle>();

    public void setListData(List<OtherAuditAlreadyMoudle> listData) {
        if (listData != null) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    private Context context;

    public AuditAlreadyAdapter(Context context) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).
                    inflate(R.layout.other_audit_already_lv_item, null);
        }

        TextView already_item_name = ListViewHolder.get(convertView, R.id.already_item_name);
        TextView already_item_car = ListViewHolder.get(convertView, R.id.already_item_car);
        TextView already_item_car_det = ListViewHolder.get(convertView, R.id.already_item_car_det);
        TextView already_item_time = ListViewHolder.get(convertView, R.id.already_item_time);
        TextView already_item_end_time = ListViewHolder.get(convertView, R.id.already_item_end_time);
        TextView already_item_people = ListViewHolder.get(convertView, R.id.already_item_people);
        TextView already_item_phone = ListViewHolder.get(convertView, R.id.already_item_phone);
        TextView already_item_sure = ListViewHolder.get(convertView, R.id.already_item_sure);

        if(listData.get(position).getCAR_TYPE().equals("0")) { //(0公务用车/1 接客专用车)
            already_item_name.setText("公务用车");
        }else if(listData.get(position).getCAR_TYPE().equals("1")) {
            already_item_name.setText("接客用车");
        }else {
            already_item_name.setText("-");
            LogUtils.e("listData.get(position).getCAR_TYPE()="+listData.get(position).getCAR_TYPE());
        }

        already_item_car.setText(listData.get(position).getNUMBER() +" "+listData.get(position).getCAR_BOX());
        already_item_car_det.setText(listData.get(position).getBRAND() + " " + listData.get(position).getMODEL() + " "+listData.get(position).getCOLOR());
        already_item_time.setText("开始时间:"+listData.get(position).getRESERVE_START_TIME());
        already_item_end_time.setText("结束时间:"+listData.get(position).getRESERVE_END_TIME());
        already_item_people.setText("预约人:"+listData.get(position).getUSER_NAME());
        already_item_phone.setText(listData.get(position).getPHONE());//预约人电话

        //0待审核 1未出行 2已出行 3 审核未通过
        if(listData.get(position).getSTATUS().equals("0")) {
            already_item_sure.setText("待审核");
        }else if(listData.get(position).getSTATUS().equals("1")) {
            already_item_sure.setText("已通过");
        }else if(listData.get(position).getSTATUS().equals("2")) {
            already_item_sure.setText("已出行");
        }else if(listData.get(position).getSTATUS().equals("3")) {
            already_item_sure.setText("未通过");
        }
        return convertView;
    }
}

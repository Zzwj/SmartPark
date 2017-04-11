package com.xcoder.smartpark.adapter.use;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.use.UseCarLocationActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.UseMyOrderHistoryMoudel;
import com.xcoder.smartpark.widget.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车---我的预约--历史预约的适配器
 */

public class UseMyOrderHistoryAdapter extends BaseAdapter {
    private List<UseMyOrderHistoryMoudel> listData = new ArrayList<UseMyOrderHistoryMoudel>();
    private Context context;

    public void setListData(List<UseMyOrderHistoryMoudel> listData) {
        if(listData!=null&&listData.size()>0) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    public UseMyOrderHistoryAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
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
                    inflate(R.layout.use_my_order_history_list_item, null);
        }
        CircleImageView use_lv_item_head= ListViewHolder.get(convertView,R.id.use_lv_item_head);//头像
        TextView future_item_name=ListViewHolder.get(convertView,R.id.future_item_name);//接客.公务
        TextView future_item_car=ListViewHolder.get(convertView,R.id.future_item_car);//车的资料
        TextView future_item_car_det=ListViewHolder.get(convertView,R.id.future_item_car_det);//车的详细资料
        TextView future_item_time=ListViewHolder.get(convertView,R.id.future_item_time);//开始时间
        TextView future_item_end_time = ListViewHolder.get(convertView, R.id.future_item_end_time);//结束时间
        TextView future_item_people=ListViewHolder.get(convertView,R.id.future_item_people);//司机
        TextView future_item_phone=ListViewHolder.get(convertView,R.id.future_item_phone);//司机电话
        LinearLayout future_item_lookloc=ListViewHolder.get(convertView, R.id.future_item_lookloc);//
        TextView future_item_status = ListViewHolder.get(convertView, R.id.future_item_status);//状态显示
        TextView future_item_cancel=ListViewHolder.get(convertView,R.id.future_item_cancel);//状态

        UseMyOrderHistoryMoudel umoh=listData.get(position);

        if(umoh.getCAR_TYPE().equals("0")) {//公务用车
            use_lv_item_head.setImageResource(R.drawable.use_official_ico);
            future_item_name.setText("公务用车");

        }else if(umoh.getCAR_TYPE().equals("1")) {//接客用车
            use_lv_item_head.setImageResource(R.drawable.use_guests_ico);
            future_item_name.setText("接客专用车");
        }

//        if(umoh.getSTATUS().equals("0")) {//0待审核 1未出行 2已出行 3 审核未通过
//            future_item_status.setText("待审核");
//        }else if(umoh.getSTATUS().equals("1")) {
//            future_item_status.setText("未出行");
//        }else if(umoh.getSTATUS().equals("2")) {
//            future_item_status.setText("已出行");
//        }else if(umoh.getSTATUS().equals("3")) {
//            future_item_status.setText("未通过");
//            future_item_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.app_lv_item_red_selector));
//        }else {
//            LogUtils.d("umoh.getSTATUS()==="+umoh.getSTATUS());
//        }

        future_item_car.setText(umoh.getNUMBER() + " " + umoh.getCOLOR());
        future_item_car_det.setText(umoh.getBRAND() + " " + umoh.getMODEL() + " " +umoh.getCAR_BOX());
        future_item_time.setText("开始时间:" + umoh.getRESERVE_START_TIME());
        future_item_end_time.setText("结束时间:" + umoh.getRESERVE_END_TIME());
        future_item_people.setText("司机:"+umoh.getDRIVER_NAME());
        future_item_phone.setText(""+umoh.getDRIVER_TEL());

        //0待审核 1未出行 2已出行 3 审核未通过
        if(umoh.getSTATUS().equals("0")) {
            future_item_cancel.setText("待审核");
        }else if(umoh.getSTATUS().equals("1")) {
            future_item_cancel.setText("未出行");
        }else if(umoh.getSTATUS().equals("2")) {
            future_item_cancel.setText("完成出行");
        }else if(umoh.getSTATUS().equals("3")) {
            future_item_cancel.setText("未通过");
            future_item_cancel.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.app_lv_item_red_selector));
        }else if(umoh.getSTATUS().equals("4")) {
            future_item_cancel.setText("已上车");
        }


        else {
            future_item_cancel.setText("-");
        }
        return convertView;
    }
}

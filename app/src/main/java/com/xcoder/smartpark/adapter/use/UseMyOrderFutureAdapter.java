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
import com.xcoder.smartpark.moudel.UseMyOrderFutureMoudel;
import com.xcoder.smartpark.widget.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/19 0019.
 * 用车---我的预约--历史预约的适配器
 */

public class UseMyOrderFutureAdapter extends BaseAdapter {
    private List<UseMyOrderFutureMoudel> listData = new ArrayList<UseMyOrderFutureMoudel>();
    private Context context;

    public void setListData(List<UseMyOrderFutureMoudel> listData) {
        if (listData != null && listData.size() > 0) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }


    public UseMyOrderFutureAdapter(Context context) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).
                    inflate(R.layout.use_my_order_future_list_item, null);
        }
        CircleImageView use_lv_item_head = ListViewHolder.get(convertView, R.id.use_lv_item_head);//头像
        TextView future_item_name = ListViewHolder.get(convertView, R.id.future_item_name);//车名
        TextView future_item_car = ListViewHolder.get(convertView, R.id.future_item_car);//车辆信息
        TextView future_item_car_det = ListViewHolder.get(convertView, R.id.future_item_car_det);//车辆更详细的信息
        TextView future_item_time_start = ListViewHolder.get(convertView, R.id.future_item_time_start);//开始时间
        TextView future_item_time_end = ListViewHolder.get(convertView, R.id.future_item_time_end);//结束时间
        TextView future_item_people = ListViewHolder.get(convertView, R.id.future_item_people);//司机
        TextView future_item_phone = ListViewHolder.get(convertView, R.id.future_item_phone);//电话号码
        TextView future_item_cancel = ListViewHolder.get(convertView, R.id.future_item_cancel);//查看车辆位置的信息
        TextView future_item_status = ListViewHolder.get(convertView, R.id.future_item_status);//状态显示
        LinearLayout future_item_lookloc = ListViewHolder.get(convertView, R.id.future_item_lookloc);//查看车辆位置模块的点击区域


        UseMyOrderFutureMoudel umoh = listData.get(position);

        if (umoh.getCAR_TYPE().equals("0")) {//公务用车
            use_lv_item_head.setImageResource(R.drawable.use_official_ico);
            future_item_name.setText("公务用车");

        } else if (umoh.getCAR_TYPE().equals("1")) {//接客用车
            use_lv_item_head.setImageResource(R.drawable.use_guests_ico);
            future_item_name.setText("接客专用车");
        }

        if(umoh.getSTATUS().equals("0")) {//0待审核 1未出行 2已出行 3 审核未通过
            future_item_status.setText("待审核");
        }else if(umoh.getSTATUS().equals("1")) {
            future_item_status.setText("未出行");
        }else if(umoh.getSTATUS().equals("2")) {
            future_item_status.setText("已出行");
        }else if(umoh.getSTATUS().equals("3")) {
            future_item_status.setText("未通过");
            future_item_status.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.app_lv_item_red_selector));
        }else if(umoh.getSTATUS().equals("4")) {
            future_item_status.setText("已上车");
        }
        else {
            LogUtils.d("umoh.getSTATUS()==="+umoh.getSTATUS());
        }


        future_item_car.setText(umoh.getNUMBER() + " " + umoh.getCOLOR());
        future_item_car_det.setText(umoh.getBRAND() + " " + umoh.getMODEL() + " " + umoh.getCAR_BOX());
        future_item_time_start.setText("开始时间:" + umoh.getRESERVE_START_TIME());
        future_item_time_end.setText("结束时间:" + umoh.getRESERVE_END_TIME());
        future_item_people.setText("司机:" + umoh.getDRIVER_NAME());
        future_item_phone.setText("" + umoh.getDRIVER_TEL());

        future_item_lookloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UseCarLocationActivity.class);
                intent.putExtra("CAR_ID", listData.get(position).getCAR_ID());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}

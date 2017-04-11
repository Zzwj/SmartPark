package com.xcoder.smartpark.adapter.use;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.use.UseSubscribeActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.CarMoudel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder-jk on 2016/12/14
 * 用车界面的listviewAdapter
 */

public class UseMainAdapter extends BaseAdapter {
    private Context context;

    public UseMainAdapter(){

    }

    public UseMainAdapter(Context context){
        this.context=context;
    }


    public List<CarMoudel> listData = new ArrayList<CarMoudel>();

    public void setListData(List<CarMoudel> listData) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.use_main_list_item, null);
        }
        LinearLayout use_lv_item_sub_ll=ListViewHolder.get(convertView,R.id.use_lv_item_sub_ll);
        use_lv_item_sub_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, UseSubscribeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CarMoudel",listData.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        ImageView use_lv_item_head = ListViewHolder.get(convertView, R.id.use_lv_item_head);

        TextView use_lv_item_car_tv = ListViewHolder.get(convertView, R.id.use_lv_item_car_tv);
        TextView use_lv_item_plate_tv = ListViewHolder.get(convertView,R.id.use_lv_item_plate_tv);
        TextView use_lv_item_brand_tv = ListViewHolder.get(convertView,R.id.use_lv_item_brand_tv);
        TextView use_lv_item_vehicle_tv = ListViewHolder.get(convertView, R.id.use_lv_item_vehicle_tv);
        TextView use_lv_item_explain_tv = ListViewHolder.get(convertView, R.id.use_lv_item_explain_tv);
        TextView use_lv_item_color_tv = ListViewHolder.get(convertView, R.id.use_lv_item_color_tv);
        //公车与专车使用不同图像
        if(listData.get(position).getCAR_TYPE().equals("0")) {//公车
            use_lv_item_head.setImageResource(R.drawable.use_official_ico);
            use_lv_item_car_tv.setText("公务用车");
        }else if(listData.get(position).getCAR_TYPE().equals("1")){//专车
            use_lv_item_head.setImageResource(R.drawable.use_guests_ico);
            use_lv_item_car_tv.setText("接客专用车");
        }else {
            LogUtils.e("listData.get(position).getPROVINCE()="+listData.get(position).getPROVINCE());
            use_lv_item_car_tv.setText("-");
        }

        //配置数据

        use_lv_item_plate_tv.setText(listData.get(position).getNUMBER() + " "+listData.get(position).getCAR_BOX());
        use_lv_item_brand_tv.setText(listData.get(position).getBRAND());
        use_lv_item_vehicle_tv.setText(listData.get(position).getMODEL());
        use_lv_item_color_tv.setText(listData.get(position).getCOLOR());
        use_lv_item_explain_tv.setText(listData.get(position).getNOTE());

        return convertView;
    }
}

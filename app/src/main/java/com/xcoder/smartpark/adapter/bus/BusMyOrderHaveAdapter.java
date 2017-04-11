package com.xcoder.smartpark.adapter.bus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.bus.BusMyOrderLocationActivity;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.xcoder.smartpark.widget.dialog.BusCancelSubDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/18 0018.
 * 班车，我的预约    -已预约
 */

public class BusMyOrderHaveAdapter extends BaseAdapter {
    private List<BusHistoryMoudle> listData = new ArrayList<BusHistoryMoudle>();
    private Context context;
    private AppUser userInfo;//用户信息

    public void setListData(List<BusHistoryMoudle> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    public BusMyOrderHaveAdapter() {

    }

    public BusMyOrderHaveAdapter(Context context) {
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
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).inflate(R.layout.bus_my_order_have_list_item, null);
        }

        //获取控件
        ImageView have_lv_item_long = ListViewHolder.get(convertView, R.id.have_lv_item_long);
        TextView have_lv_item_name = ListViewHolder.get(convertView, R.id.have_lv_item_name);
        TextView have_lv_item_car = ListViewHolder.get(convertView, R.id.have_lv_item_car);
        TextView have_lv_item_site = ListViewHolder.get(convertView, R.id.have_lv_item_site);
        TextView have_lv_item_car_det = ListViewHolder.get(convertView, R.id.have_lv_item_car_det);
        TextView have_lv_item_time = ListViewHolder.get(convertView, R.id.have_lv_item_time);
        TextView have_lv_item_people = ListViewHolder.get(convertView, R.id.have_lv_item_people);
        TextView have_lv_item_phone = ListViewHolder.get(convertView, R.id.have_lv_item_phone);
        TextView have_lv_item_location = ListViewHolder.get(convertView, R.id.have_lv_item_location);
        TextView have_lv_item_cancel = ListViewHolder.get(convertView, R.id.have_lv_item_cancel);

        //展示数据
        have_lv_item_name.setText(""+listData.get(position).getLINE_NAME());
        have_lv_item_car.setText("");//线路起始站目前没有
        have_lv_item_site.setText("上车地点:"+listData.get(position).getSITE_NAME());
        have_lv_item_car_det.setText(listData.get(position).getMODEL() + " " + listData.get(position).getCOLOR());

        if(listData.get(position).getTYPE().equals("1")) {//0预约当天 1长期预约
            have_lv_item_time.setVisibility(View.GONE);
            have_lv_item_long.setVisibility(View.VISIBLE);
        }else {
            have_lv_item_time.setText("预约时间：" + listData.get(position).getRESERVE_TIME());
            have_lv_item_long.setVisibility(View.GONE);
        }

        have_lv_item_people.setText("司机：" + listData.get(position).getDRIVER_NAME());
        have_lv_item_phone.setText(listData.get(position).getDRIVER_TEL());

        //点击实时位置
        have_lv_item_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BusMyOrderLocationActivity.class);
                BusHistoryMoudle busHistoryMoudle = listData.get(position);
                intent.putExtra("BUS_ID", busHistoryMoudle.getBUS_ID());
                context.startActivity(intent);
            }
        });

        //点击取消预约
        have_lv_item_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusHistoryMoudle busHistoryMoudle = listData.get(position);
                BusCancelSubDialog busCancelSubDialog = new BusCancelSubDialog(context) {
                    @Override
                    public void doSubmit(View view) {
                        userInfo = ConfigSP.getUserInfo();
                        AppProgressDialog.showProgress(context);
                        //这里写网络请求
                        Rest rest = new Rest("/appbus/cancleReserve.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
                        rest.addParam("BUS_RESERVE_ID", listData.get(position).getBUS_RESERVE_ID());
                        rest.post(new Callback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                                ToastUtil.showToast("取消成功");
                                AppProgressDialog.dismiss(context);
                                EventBus.getDefault().post("取消预约后刷新列表");
                            }

                            @Override
                            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                                ToastUtil.showToast(msg);
                                AppProgressDialog.dismiss(context);
                            }

                            @Override
                            public void onError(Exception exception) {
                                ToastUtil.showToast("网络繁忙");
                                AppProgressDialog.dismiss(context);
                            }
                        });

                        dismiss();
                    }
                };
                busCancelSubDialog.name = "" + listData.get(position).getLINE_NAME();
                busCancelSubDialog.shift = "";
                busCancelSubDialog.address = busHistoryMoudle.getSITE_NAME();
                busCancelSubDialog.time = busHistoryMoudle.getRESERVE_TIME();
                busCancelSubDialog.show();
            }
        });

        return convertView;
    }
}

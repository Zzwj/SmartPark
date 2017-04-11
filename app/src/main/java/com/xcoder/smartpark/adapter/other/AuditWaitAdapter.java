package com.xcoder.smartpark.adapter.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.OtherAuditWaitMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.service.other.AuditWaitService;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xoder_xz on 2016/12/30 0030.
 * 用车审核---未审核
 */

public class AuditWaitAdapter extends BaseAdapter {
    private List<OtherAuditWaitMoudle> listData = new ArrayList<OtherAuditWaitMoudle>();
    private AppUser appUser;

    public void setListData(List<OtherAuditWaitMoudle> listData) {
        if (listData != null) {
            this.listData = listData;
            notifyDataSetChanged();
        }
    }

    private Context context;
    private AuditWaitService aWService;

    public AuditWaitAdapter(Context context,AuditWaitService auditWaitService) {
        this.context = context;
        appUser = ConfigSP.getUserInfo();
        this.aWService=auditWaitService;
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
                    inflate(R.layout.other_audit_wait_lv_item, null);
        }

        TextView wait_item_name = ListViewHolder.get(convertView, R.id.wait_item_name);
        TextView wait_item_car = ListViewHolder.get(convertView,R.id.wait_item_car);
        TextView wait_item_car_det = ListViewHolder.get(convertView, R.id.wait_item_car_det);
        TextView wait_item_time = ListViewHolder.get(convertView, R.id.wait_item_time);
        TextView wait_item_time_stop = ListViewHolder.get(convertView, R.id.wait_item_time_stop);
        TextView wait_item_people = ListViewHolder.get(convertView, R.id.wait_item_people);
        TextView wait_item_phone = ListViewHolder.get(convertView, R.id.wait_item_phone);
        LinearLayout wait_item_surell = ListViewHolder.get(convertView, R.id.wait_item_surell);
        LinearLayout wait_item_refusell = ListViewHolder.get(convertView, R.id.wait_item_refusell);


        if(listData.get(position).getCAR_TYPE().equals("0")) { //(0公务用车/1 接客专用车)
            wait_item_name.setText("公务用车");
        }else if(listData.get(position).getCAR_TYPE().equals("1")) {
            wait_item_name.setText("接客用车");
        }else {
            wait_item_name.setText("-");
            LogUtils.e("listData.get(position).getCAR_TYPE()="+listData.get(position).getCAR_TYPE());
        }

        wait_item_car.setText(listData.get(position).getNUMBER() +" "+listData.get(position).getCAR_BOX());
        wait_item_car_det.setText(listData.get(position).getBRAND() + " " + listData.get(position).getMODEL() + " "+listData.get(position).getCOLOR());
        wait_item_time.setText("开始时间:"+listData.get(position).getRESERVE_START_TIME());
        wait_item_time_stop.setText("结束时间:"+listData.get(position).getRESERVE_END_TIME());
        wait_item_people.setText("预约人:"+listData.get(position).getUSER_NAME());
        wait_item_phone.setText(listData.get(position).getPHONE());//预约人电话

        wait_item_surell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //通过
                    AppProgressDialog.showProgress(context);
                    Rest rest=new Rest("/appcar/edit.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
                    rest.addParam("CAR_RESERVE_ID",listData.get(position).getCAR_RESERVE_ID());
                    if(ConfigSP.getIsLEADER()) {
                        rest.addParam("STATUS","5");//0待审核 1未出行 2已出行 3 审核未通过 5处长审批通过
                    }else {
                        rest.addParam("STATUS","1");//0待审核 1未出行 2已出行 3 审核未通过 5处长审批通过
                    }

                    rest.addParam("RESERVE_TIME",listData.get(position).getRESERVE_START_TIME());
                    rest.addParam("CAR_ID",listData.get(position).getCAR_ID());
                    rest.post(new Callback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject, String state, String msg) {
                            AppProgressDialog.dismiss(context);
                            ToastUtil.showToast("您已通过该申请");
                            if(aWService!=null){
                                aWService.refreshAndload.immediatelyRefresh();
                                //数据变化后通知已审批fregment
                                EventBus.getDefault().post("请已审批刷新");
                            }
                        }

                        @Override
                        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                            AppProgressDialog.dismiss(context);
                            ToastUtil.showToast(msg);
                        }

                        @Override
                        public void onError(Exception exception) {
                            AppProgressDialog.dismiss(context);
                            LogUtils.e("exception:"+exception.getMessage());
                            ToastUtil.showToast("网络繁忙，请稍后再试");
                        }
                    });
                } catch (Exception e) {
                    AppProgressDialog.dismiss(context);
                    e.printStackTrace();
                    LogUtils.e("Exception:"+e.getMessage());
                }
            }
        });

        wait_item_refusell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //拒绝
                    AppProgressDialog.showProgress(context);
                    Rest rest=new Rest("/appcar/edit.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
                    rest.addParam("CAR_RESERVE_ID",listData.get(position).getCAR_RESERVE_ID());
                    rest.addParam("STATUS","3");//0待审核 1未出行 2已出行 3 审核未通过
                    rest.addParam("RESERVE_TIME",listData.get(position).getRESERVE_START_TIME());
                    rest.addParam("CAR_ID",listData.get(position).getCAR_ID());
                    rest.post(new Callback() {
                        @Override
                        public void onSuccess(JSONObject jsonObject, String state, String msg) {
                            AppProgressDialog.dismiss(context);
                            if(aWService!=null){
                                aWService.refreshAndload.immediatelyRefresh();
                            }
                            ToastUtil.showToast("您已拒绝该申请");
                        }

                        @Override
                        public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                            AppProgressDialog.dismiss(context);
                            ToastUtil.showToast(msg);
                        }

                        @Override
                        public void onError(Exception exception) {
                            AppProgressDialog.dismiss(context);
                            LogUtils.e("exception:"+exception.getMessage());
                            ToastUtil.showToast("网络繁忙，请稍后再试");
                        }
                    });
                } catch (Exception e) {
                    AppProgressDialog.dismiss(context);
                    e.printStackTrace();
                    LogUtils.e("Exception:"+e.getMessage());
                }

            }
        });

        return convertView;
    }
}

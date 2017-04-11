package com.xcoder.smartpark.adapter.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcoder.lib.adapter.ListBaseAdapter;
import com.xcoder.lib.adapter.ListViewHolder;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.SubscribeGoingMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.service.other.BusDriverSubscribeGoingService;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.xcoder.smartpark.widget.dialog.OtherDriverSubscribeGoingDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Created by xcoder_xz on 2017/1/1 0001.
 * * 其他--公车---预约信息---预约中的适配器
 */

public class DriverSubscribeGoingAdapter extends ListBaseAdapter<SubscribeGoingMoudle> {
    private Context context;

    private BusDriverSubscribeGoingService bdsgService;
    private AppUser userInfo;//用户信息

    public DriverSubscribeGoingAdapter(Context context, BusDriverSubscribeGoingService bdsgService) {
        this.context = context;
        this.bdsgService = bdsgService;
        userInfo= ConfigSP.getUserInfo();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(SmartParkApplication.getAppContext()).
                    inflate(R.layout.other_driver_subscribe_going_lv_item, null);
        }

        TextView going_time_begin = ListViewHolder.get(convertView, R.id.going_time_begin);//开始时间
        TextView going_time_end = ListViewHolder.get(convertView, R.id.going_time_end);//结束时间
        TextView going_people = ListViewHolder.get(convertView, R.id.going_people);//预约人
        TextView going_phone = ListViewHolder.get(convertView, R.id.going_phone);//联系电话
        LinearLayout going_ll = ListViewHolder.get(convertView, R.id.going_ll);//设置状态
        final TextView going_action_status = ListViewHolder.get(convertView,R.id.going_action_status);//状态显示


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
            going_laji_tv.setText(listData.get(position).getBRAND() + "  " + listData.get(position).getMODEL()+ "  " + listData.get(position).getCOLOR());//品牌+ 型号


            //0待审核（处长审批） 1未出行(审批通过）  2已出行 3 审核未通过 4 上车 5 用车管理员审批
            if(listData.get(position).getSTATUS().equals("4")) {
                going_action_status.setText("已上车");
            }

            if(listData.get(position).getSTATUS().equals("1")) {
                going_action_status.setText("未出行");
            }

            if(listData.get(position).getSTATUS().equals("2")) {
                going_action_status.setText("完成出行");
            }
            if(listData.get(position).getSTATUS().equals("0")) {
                going_action_status.setText("待审核");
            }
            if(listData.get(position).getSTATUS().equals("3")) {
                going_action_status.setText("未通过");
            }
            if(listData.get(position).getSTATUS().equals("5")) {
                going_action_status.setText("待管理员审批");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        going_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //0待审核 1未出行 2已出行 3 审核未通过
                if("0".equals(listData.get(position).getSTATUS())) {
                    ToastUtil.showToast("审核员正在审核中，暂时不能修改状态");
                    return;
                }

                if("2".equals(listData.get(position).getSTATUS())) {
                    ToastUtil.showToast("该状态为已出行，暂时不能修改状态");
                    return;
                }

                if("3".equals(listData.get(position).getSTATUS())) {
                    ToastUtil.showToast("审核未通过，暂时不能修改状态");
                    return;
                }

                if("5".equals(listData.get(position).getSTATUS())) {
                    ToastUtil.showToast("待管理员审批中，暂时不能修改状态");
                    return;
                }


                OtherDriverSubscribeGoingDialog dialog=new OtherDriverSubscribeGoingDialog(context) {
                    @Override
                    public void doSubmit(View view) {
                        AppProgressDialog.showProgress(context);
                        Rest rest = new Rest("/appcar/setStatus.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
                        rest.addParam("CAR_ID",listData.get(position).getCAR_ID());
                        rest.addParam("CAR_RESERVE_ID",listData.get(position).getCAR_RESERVE_ID());
                        rest.addParam("STATUS","2");
                        rest.post(new Callback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast("设置成功");
                                EventBus.getDefault().post("设置状态成功，刷新预约中列表");
                            }

                            @Override
                            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast(msg);
                            }

                            @Override
                            public void onError(Exception exception) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast(exception.toString());

                            }
                        });
                    }

                    @Override
                    public void doUpCar(View view) {
                        AppProgressDialog.showProgress(context);
                        Rest rest = new Rest("/appcar/getOnStatus.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
                        rest.addParam("CAR_ID",listData.get(position).getCAR_ID());
                        rest.addParam("CAR_RESERVE_ID",listData.get(position).getCAR_RESERVE_ID());
                        rest.post(new Callback() {
                            @Override
                            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast("设置成功");
                                EventBus.getDefault().post("设置状态成功，刷新预约中列表");
                            }

                            @Override
                            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast(msg);
                            }

                            @Override
                            public void onError(Exception exception) {
                                AppProgressDialog.dismiss(context);
                                ToastUtil.showToast(exception.toString());

                            }
                        });
                    }
                };
                //设置参数值
                dialog.time_begin = listData.get(position).getRESERVE_START_TIME();
                dialog.time_end = listData.get(position).getRESERVE_END_TIME();
                dialog.dialog_people = listData.get(position).getUSER_NAME();
                dialog.dialog_phone = listData.get(position).getPHONE();

                LogUtils.d("listData.get(position).getSTATUS()======="+listData.get(position).getSTATUS());

                //0待审核（处长审批） 1未出行(审批通过）  2已出行 3 审核未通过 4 上车 5 用车管理员审批
                if(listData.get(position).getSTATUS().equals("4")) {//如果是已上车状态，那就不显示上车按钮
                    dialog.flag = "0";
                }

                if(listData.get(position).getSTATUS().equals("1")) {//如果是审核通过状态，不显示完成出行，只显示已上车
                    dialog.flag_1 = "0";
                }

                dialog.show();
            }
        });
        going_time_begin.setText("开始时间:"+listData.get(position).getRESERVE_START_TIME());
        going_time_end.setText("结束时间:"+listData.get(position).getRESERVE_END_TIME());
        going_people.setText(listData.get(position).getUSER_NAME());
        going_phone.setText(listData.get(position).getPHONE());
        return convertView;
    }
}

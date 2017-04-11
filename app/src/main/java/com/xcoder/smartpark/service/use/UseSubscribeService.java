package com.xcoder.smartpark.service.use;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.lib.utils.RegularUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.use.UseMyOrderActivity;
import com.xcoder.smartpark.activity.use.UseSubscribeActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.BusHistoryMoudle;
import com.xcoder.smartpark.moudel.CarMoudel;
import com.xcoder.smartpark.moudel.UseMyOrderHistoryMoudel;
import com.xcoder.smartpark.moudel.UseSubCarMoudel;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.outside.pickerview.TimePicker;
import com.xcoder.smartpark.outside.pickerview.TimePickerView;
import com.xcoder.smartpark.outside.pickerview.view.WheelTime;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.use.UseSubscribeView;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xcoder-xz on 2016/12/22 0022.
 * 用车--预约
 */

public class UseSubscribeService {
    @Injection
    UseSubscribeView usv;
    private UseSubscribeActivity usActivity;
    private WheelTime wheelTime;

    private CarMoudel carMoudel;
    private AppUser userInfo;

    public TimePicker timePicker;

    public String startTime = "";//选择预约时间
    public String endTime = "";

    public String START_ADDRESS = "";//开始时间
    public String END_ADDRESS = "";//结束时间
    public String NOTE = "";//用车事由

    public ArrayList<String> unabletimearray = new ArrayList<String>();


    public void init(UseSubscribeActivity usActivity) {
        this.usActivity = usActivity;
        Intent intent = usActivity.getIntent();
        carMoudel = (CarMoudel) intent.getSerializableExtra("CarMoudel");
        userInfo = ConfigSP.getUserInfo();
        //initTimeView();
        initview();
        GetData();
    }

    public void initview() {
        //公车与专车使用不同图像
        if ("0".equals(carMoudel.getCAR_TYPE())) {//公车
            usv.use_sub_head.setImageResource(R.drawable.use_official_ico);
        } else if("1".equals(carMoudel.getCAR_TYPE())){//专车
            usv.use_sub_head.setImageResource(R.drawable.use_guests_ico);
        }else {
            ToastUtil.showToast("车辆类型值有误:"+carMoudel.getCAR_TYPE());
        }

        usv.use_sub_det.setText(carMoudel.getCAR_NAME() + " " + carMoudel.getNUMBER() + " " + carMoudel.getCAR_BOX());
        usv.use_sub_car.setText(carMoudel.getBRAND() + " " + carMoudel.getMODEL() + " " + carMoudel.getCOLOR());
        usv.use_sub_content.setText(carMoudel.getNOTE());
        usv.use_sub_driver.setText("司机：" + carMoudel.getDRIVER_NAME());
        usv.use_sub_phone.setText(carMoudel.getPHONE());

    }

    /**
     * 获取不可预约时间段，显示在界面上，起到提示用户的作用
     * 本接口支持分页，但是要在界面上滑动显示，因此在第一页全部取出
     */
    public void GetData() {
        AppProgressDialog.showProgress(usActivity);
        Rest rest = new Rest("/appcar/listReserveCar.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("CAR_ID",carMoudel.getCAR_ID());
        rest.addParam("showCount", "1000");
        rest.addParam("currentPage", "1");
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(usActivity);

                try {
                    JSONArray jsonArray = jsonObject.getJSONObject("dataset").getJSONArray("totalResult");
                    List<UseMyOrderHistoryMoudel> listUmofMoudel = JSON.parseArray(jsonArray.toString(), UseMyOrderHistoryMoudel.class);
                    if(null != listUmofMoudel && listUmofMoudel.size() > 0) {
                        for(int i=0; i<listUmofMoudel.size(); i++) {
                            String s = listUmofMoudel.get(i).getRESERVE_START_TIME() + " - " + listUmofMoudel.get(i).getRESERVE_END_TIME();
                            unabletimearray.add(s);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(usActivity);

            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(usActivity);

            }
        });

    }



    /*提交
     */
    public void doSubmit() {
        try {
//            String subTime = "";
//            Date dateNow = new Date();
//            Date parseDate = WheelTime.dateFormat.parse(wheelTime.getTime());
//            if (parseDate.getTime() + 60000 < dateNow.getTime()) {
//                //用户选择的时间小于当前时间，则提醒，不能设置
//                ToastUtil.showToast("您选择的时间不能小于当前时间");
//                return;
//            } else {//相等
//                //用户选择的时间等于当前时间，则可以设置
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
//                //  bsv.bus_sub_time.setText(sf.format(date));
//                subTime = sf.format(parseDate);
//            }

            if(startTime.equals("")) {
                ToastUtil.showToast("请选择开始时间");
                return;
            }

            if(endTime.equals("")) {
                ToastUtil.showToast("请选择结束时间");
                return;
            }

            START_ADDRESS =  usv.use_subscribe_startspace_tiet.getText().toString().trim();
            if(START_ADDRESS.equals("")) {
                ToastUtil.showToast("请输入开始地点");
                return;
            }

            END_ADDRESS =  usv.use_subscribe_endspace_tiet.getText().toString().trim();
            if(END_ADDRESS.equals("")) {
                ToastUtil.showToast("请输入结束地点");
                return;
            }

            NOTE =  usv.use_subscribe_reason_tiet.getText().toString().trim();
            if(NOTE.equals("")) {
                ToastUtil.showToast("请输入用车事由");
                return;
            }


            //判断结束时间大于开始时间
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
            Date startdate = sdf.parse(startTime);
            Date enddata = sdf.parse(endTime);
            if(enddata.getTime() < startdate.getTime()) {
                ToastUtil.showToast("结束时间不能早于开始时间");
                return;
            }

            if (!TextUtils.isEmpty(carMoudel.getCAR_ID())) {
                //判断时间是否有错误和汽车id是否存在
                Rest rest = new Rest("/appcar/save.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
                rest.addParam("USER_ID", userInfo.getUSER_ID());
                rest.addParam("DEPT_ID", userInfo.getDEPT_ID());
                rest.addParam("RESERVE_START_TIME", startTime);
                rest.addParam("RESERVE_END_TIME",endTime);
                rest.addParam("CAR_ID", carMoudel.getCAR_ID());
                rest.addParam("DRIVER_ID",carMoudel.getDRIVER_ID());
                rest.addParam("START_ADDRESS",START_ADDRESS);
                rest.addParam("END_ADDRESS",END_ADDRESS);
                rest.addParam("NOTE",NOTE);

                rest.post("UseSubscribeActivity", new Callback() {
                    @Override
                    public void onSuccess(JSONObject jsonObject, final String state, String msg) {
                        try {
                            if (TextUtils.equals(msg, "成功")) {
//                                new AppDialog(usActivity, "预约成功", "取消", "去看看") {
//                                    @Override
//                                    public void buttonRight() {
//                                        super.buttonRight();
//                                        Intent intent = new Intent(usActivity, UseMyOrderActivity.class);
//                                        usActivity.startActivity(intent);
//                                        usActivity.finish();
//                                    }
//
//                                    @Override
//                                    public void buttonLeft() {
//                                        super.buttonLeft();
//                                        usActivity.finish();
//                                    }
//                                };

                                new AppDialog(usActivity,"请等待审批员审批","确定") {
                                    @Override
                                    public void buttonRight() {
                                        super.buttonRight();
                                        usActivity.finish();
                                    }
                                };
                            }
                             UseSubCarMoudel useSubCarMoudel = JSON.parseObject(jsonObject.getString("dataset"), UseSubCarMoudel.class);
                        } catch (Exception ex) {
                            onError(ex);
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                        ToastUtil.showToast(msg);
                    }

                    @Override
                    public void onError(Exception exception) {
                        ToastUtil.noNet();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("e:",e);
        }
    }

    public void doResult() {
        //设置时间控件参数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 展示不能选择的时间段
     */
    public void showunabletime() {
        if(unabletimearray == null || unabletimearray.size() == 0) {
            ToastUtil.showToast("暂无不可预约时间段");
            return;
        }
        String[] arr = (String[])unabletimearray.toArray(new String[unabletimearray.size()]);
        final AlertDialog.Builder builder = new AlertDialog.Builder(usActivity);
        builder.setTitle("不可预约时间段如下:");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        Dialog dialog = builder.show();
    }


    /**
     * 跳转拨号界面
     */
    public void toPhone() {
        final String phone = usv.use_sub_phone.getText().toString();
        if (RegularUtils.isMobileLength11(phone) && RegularUtils.isNumeric(phone)) {
            //需要询问用户
            new AppDialog(usActivity, "是否拨打：" + phone, "取消", "确定") {
                @Override
                public void buttonRight() {
                    super.buttonRight();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    usActivity.startActivity(intent);
                }
            };
        }else {
            ToastUtil.showToast("手机号码不正确");
        }
    }

    /**
     * 选择时间
     */
    public void selectTime(final String flag) {
        //时间选择器
        timePicker = new TimePicker(usActivity, TimePickerView.Type.ALL);
        timePicker.setCyclic(false);
        timePicker.setCancelable(true);
        // 时间选择后回调
        timePicker.setOnTimeSelectListener(new TimePicker.OnTimeSelectListener() {


            @Override
            public void onTimeSelect(Date date) {
                Date dateNow = new Date();
                if (date.getTime() + 60000 < dateNow.getTime()) {
                    //用户选择的时间小于当前时间，则提醒，不能设置
                    ToastUtil.showToast("您选择的时间不能小于当前时间");
                } else if((long)((date.getTime() - dateNow.getTime()) / (1000 * 60 * 60 *24) + 0.5) > 7){
                    ToastUtil.showToast("只能预约七天内的车，请重新选择"+"");
                } else {//相等
                    //用户选择的时间大于当前
                    // 时间，则可以设置
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(flag.equals("start")) {
                        startTime = sf.format(date);
                        usv.use_time_start_show_tv.setText(startTime);
                    }else if(flag.equals("end")) {
                        endTime = sf.format(date);
                        usv.use_time_end_show_tv.setText(endTime);
                    }
                }
            }
        });
        timePicker.show();
    }


    /**
     * 初始化时间选择器
     */
    /*public void initTimeView() {
        //对选择器进行触摸监听，防止和scrollview起冲突
        View.OnTouchListener onTouchWhile = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    usv.use_sub_scroll.requestDisallowInterceptTouchEvent(true);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    usv.use_sub_scroll.requestDisallowInterceptTouchEvent(true);
                } else {
                    usv.use_sub_scroll.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        };

        usv.timepicker.setOnTouchListener(onTouchWhile);
        usv.year.setOnTouchListener(onTouchWhile);
        usv.month.setOnTouchListener(onTouchWhile);
        usv.day.setOnTouchListener(onTouchWhile);
        usv.hour.setOnTouchListener(onTouchWhile);
        usv.min.setOnTouchListener(onTouchWhile);

        wheelTime = new WheelTime(usv.timepicker, TimePickerView.Type.ALL);
        //设置时间控件参数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }*/

}

package com.xcoder.smartpark.service.tou;

import android.content.Intent;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.lib.utils.RegularUtils;
import com.xcoder.smartpark.activity.tou.TouListActivity;
import com.xcoder.smartpark.activity.tou.TouRegisterActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.outside.pickerview.TimePicker;
import com.xcoder.smartpark.outside.pickerview.TimePickerView;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.tou.TouRegisterView;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xcoder on 2016/12/16 0016.
 * 访客---预约登记
 */

public class TouRegisterService {
    @Injection
    TouRegisterView trv;
    private TouRegisterActivity touRegisterActivity;
    private TimePickerView pvTime;
    public TimePicker timePicker;

    public void init(TouRegisterActivity touRegisterActivity) {
        this.touRegisterActivity = touRegisterActivity;
        trv.tou_register_phone_til.setHint("联系方式");
    }


    /**
     * 选择时间
     */
    public void selectTime() {
        //时间选择器
        timePicker = new TimePicker(touRegisterActivity, TimePickerView.Type.ALL);
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
                } else {//相等
                    //用户选择的时间大于当前时间，则可以设置
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    trv.tou_register_time.setText(sf.format(date));
                }

            }
        });
        timePicker.show();
    }

    /**
     * 提交用户信息
     */
    public void submit() {
        try {
            //获取缓存的用户信息
            AppUser appUser = ConfigSP.getUserInfo();
            //组织数据
            String NAME = trv.tou_register_name_tiet.getText().toString().trim();
            String PHONE = trv.tou_register_phone_tiet.getText().toString().trim();
            String VISITOR_TIME = trv.tou_register_time.getText().toString().trim();
            String MATTER = trv.tou_register_thing_tiet.getText().toString().trim();
            String ID_CARD = trv.tou_register_card_tiet.getText().toString().trim();
            String OWNED_COMPANY = trv.tou_register_wrok_tiet.getText().toString().trim();
            String CAR_NUMBER = trv.tou_register_car_cared_tiet.getText().toString().trim();
            String VISITOR_NUM = trv.tou_register_number_tiet.getText().toString().trim();
            String NOTES = trv.tou_register_remarks_tiet.getText().toString().trim();
            String USER_ID = appUser.getUSER_ID();
            String USERNAME = appUser.getUSERNAME();
            String USER_TEL = appUser.getPHONE();

            if(NAME.equals("")) {
                ToastUtil.showToast("请输入访客名称");
                return;
            }

            if(PHONE.equals("")) {
                ToastUtil.showToast("请输入访客电话");
                return;
            }

            if(!RegularUtils.isMobileLength11(PHONE)) {
                ToastUtil.showToast("请输入正确的手机号");
                return;
            }

            if(!RegularUtils.isNumeric(PHONE)) {
                ToastUtil.showToast("请输入正确的手机号");
                return;
            }

            if(VISITOR_TIME.equals("")) {
                ToastUtil.showToast("请输入预约时间");
                return;
            }

            if(MATTER.equals("")) {
                ToastUtil.showToast("请输入拜访事项");
                return;
            }

            if(ID_CARD.equals("")){
                ToastUtil.showToast("请输入身份证号码");
                return;
            }

            if(!RegularUtils.isIDCard(ID_CARD)) {
                ToastUtil.showToast("身份证号码不正确");
                return;
            }

            if(USER_ID.equals("")) {
                LogUtils.e("USER_ID为空");
                return;
            }

            AppProgressDialog.showProgress(touRegisterActivity);
            Rest rest=new Rest("/appvisitor/save.nla",appUser.getUSER_ID(),appUser.getUSER_TOKEN());
            rest.addParam("NAME",NAME);
            rest.addParam("PHONE",PHONE);
            rest.addParam("VISITOR_TIME",VISITOR_TIME);
            rest.addParam("MATTER",MATTER);
            rest.addParam("ID_CARD",ID_CARD);
            rest.addParam("OWNED_COMPANY",OWNED_COMPANY);
            rest.addParam("CAR_NUMBER",CAR_NUMBER);
            rest.addParam("VISITOR_NUM",VISITOR_NUM);
            rest.addParam("USER_ID",USER_ID);
            rest.addParam("USERNAME",USERNAME);
            rest.addParam("USER_TEL",USER_TEL);
            rest.addParam("NOTES",NOTES);
            rest.post(new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    AppProgressDialog.dismiss(touRegisterActivity);
                    ToastUtil.showToast("提交成功");
                    Intent intent = new Intent(touRegisterActivity, TouListActivity.class);
                    touRegisterActivity.setResult(9001);
                    touRegisterActivity.finish();
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                    AppProgressDialog.dismiss(touRegisterActivity);
                    ToastUtil.showToast(msg);
                }

                @Override
                public void onError(Exception exception) {
                    AppProgressDialog.dismiss(touRegisterActivity);
                    LogUtils.e("exception:"+exception.getMessage());
                    ToastUtil.showToast("网络繁忙，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Exception:"+e.getMessage());
        }
    }
}

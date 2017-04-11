package com.xcoder.smartpark.service.other;

import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.BusDriverActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.OtherAuditAlreadyMoudle;
import com.xcoder.smartpark.moudel.OtherCarMessageMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.other.BusDriverView;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by xcoder_xz on 2016/12/30 0030.
 * 公车司机
 */

public class BusDriverServcice {

    @Injection
    BusDriverView busDriverView;
    private BusDriverActivity bdActivity;

    private AppUser userInfo;//用户信息

    public OtherCarMessageMoudle carmsg;

    public OtherCarMessageMoudle carmsg2;

    public void init(BusDriverActivity bdActivity) {
        this.bdActivity = bdActivity;
        userInfo = ConfigSP.getUserInfo();
        initData();

    }

    /**
     * 获取车数据
     */
    public void initData() {
        AppProgressDialog.showProgress(bdActivity);
        Rest rest = new Rest("/appcar/getCarsByDriver.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.post("BusDriverActivity", new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(bdActivity);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("dataset_line");
                    List<OtherCarMessageMoudle> listcarmsg = JSON.parseArray(jsonArray.toString(), OtherCarMessageMoudle.class);
                    if(listcarmsg != null && listcarmsg.size() > 0) {

                        if(listcarmsg.size() == 1) {
                            carmsg = listcarmsg.get(0);
                            initview(carmsg);
                        }else if(listcarmsg.size() == 2) {
                            busDriverView.bus_driver_msglv2.setVisibility(View.VISIBLE);
                            carmsg2 = listcarmsg.get(1);
                            initview2(carmsg2);
                        }



                    }else {
                        ToastUtil.showToast("未获取到车辆信息");
                        busDriverView.bus_driver_head.setVisibility(View.GONE);
                        busDriverView.bus_driver_det.setVisibility(View.GONE);
                        busDriverView.bus_driver_car.setVisibility(View.GONE);
                        busDriverView.bus_driver_content.setVisibility(View.GONE);
                        busDriverView.bus_driver_last_time.setVisibility(View.GONE);
                        busDriverView.bus_driver_location.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtil.showToast("未获取到车辆信息，暂时不能上传位置");
                            }
                        });
                    }
                } catch (Exception e) {
                    onError(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(bdActivity);
                ToastUtil.showToast(msg);
            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(bdActivity);
                ToastUtil.noNet();
            }
        });
    }

    /**
     * 界面展示数据
     */
    public void initview(OtherCarMessageMoudle otherCarMessageMoudle) {

        if(otherCarMessageMoudle.getCAR_TYPE().equals("0")) {
            busDriverView.bus_driver_head.setImageResource(R.drawable.use_official_ico);
            busDriverView.bus_driver_det.setText("公务用车" + " " + otherCarMessageMoudle.getNUMBER() + " " + otherCarMessageMoudle.getCOLOR());
        }else if(otherCarMessageMoudle.getCAR_TYPE().equals("1")) {
            busDriverView.bus_driver_head.setImageResource(R.drawable.use_guests_ico);
            busDriverView.bus_driver_det.setText("接客专用车" + " " + otherCarMessageMoudle.getNUMBER() + " " + otherCarMessageMoudle.getCOLOR());
        }else {
            ToastUtil.showToast("获取车辆类型有误："+otherCarMessageMoudle.getCAR_TYPE());
            busDriverView.bus_driver_det.setText("-");
        }

        busDriverView.bus_driver_car.setText(otherCarMessageMoudle.getBRAND() + " " + otherCarMessageMoudle.getMODEL() + " " + otherCarMessageMoudle.getCAR_BOX());
        busDriverView.bus_driver_content.setText(otherCarMessageMoudle.getNOTE());

        if(null == otherCarMessageMoudle.getLAST_TIME()) {
            busDriverView.bus_driver_last_time.setText("最后预约时间：暂无");
        }else {
            busDriverView.bus_driver_last_time.setText("最后预约时间："+ otherCarMessageMoudle.getLAST_TIME());
        }

        busDriverView.bus_driver_driver.setText("司机:"+userInfo.getUSERNAME());
        busDriverView.bus_driver_phone.setText(""+userInfo.getPHONE());


    }


    /**
     * 有可能一個司機有上班和下班兩個車
     * @param otherCarMessageMoudle
     */
    public void initview2(OtherCarMessageMoudle otherCarMessageMoudle) {

        if(otherCarMessageMoudle.getCAR_TYPE().equals("0")) {
            busDriverView.bus_driver_head2.setImageResource(R.drawable.use_official_ico);
            busDriverView.bus_driver_det2.setText("公务用车" + " " + otherCarMessageMoudle.getNUMBER() + " " + otherCarMessageMoudle.getCOLOR());
        }else if(otherCarMessageMoudle.getCAR_TYPE().equals("1")) {
            busDriverView.bus_driver_head2.setImageResource(R.drawable.use_guests_ico);
            busDriverView.bus_driver_det2.setText("接客专用车" + " " + otherCarMessageMoudle.getNUMBER() + " " + otherCarMessageMoudle.getCOLOR());
        }else {
            ToastUtil.showToast("获取车辆类型有误："+otherCarMessageMoudle.getCAR_TYPE());
            busDriverView.bus_driver_det2.setText("-");
        }

        busDriverView.bus_driver_car2.setText(otherCarMessageMoudle.getBRAND() + " " + otherCarMessageMoudle.getMODEL() + " " + otherCarMessageMoudle.getCAR_BOX());
        busDriverView.bus_driver_content2.setText(otherCarMessageMoudle.getNOTE());

        if(null == otherCarMessageMoudle.getLAST_TIME()) {
            busDriverView.bus_driver_last_time2.setText("最后预约时间：暂无");
        }else {
            busDriverView.bus_driver_last_time2.setText("最后预约时间："+ otherCarMessageMoudle.getLAST_TIME());
        }

        busDriverView.bus_driver_driver2.setText("司机:"+userInfo.getUSERNAME());
        busDriverView.bus_driver_phone2.setText(""+userInfo.getPHONE());


    }

}

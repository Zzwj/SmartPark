package com.xcoder.smartpark.service.tou;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.RegularUtils;
import com.xcoder.smartpark.activity.tou.TouDetailActivity;
import com.xcoder.smartpark.activity.tou.TouListActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.Visitor;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.view.tou.TouDetailView;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xcoder_xz on 2016/12/15 0015.
 */

public class TouDetailService {
    @Injection
    TouDetailView tdv;
    private TouDetailActivity touDetailActivity;

    //传递过来的访客信息
    public Visitor visitor;

    private AppUser userInfo;//用户信息

    public void init(TouDetailActivity touDetailActivity) {
        this.touDetailActivity = touDetailActivity;
        Intent intent = touDetailActivity.getIntent();
        visitor =(Visitor)intent.getSerializableExtra("Visitor");
        userInfo = ConfigSP.getUserInfo();
        initData();
    }

    /**
     * 数据初始化
     */
    public void initData() {
        tdv.tou_detail_name.setText(visitor.getNAME());
        tdv.tou_detail_phone.setText(visitor.getPHONE());
        tdv.tou_detail_card.setText(visitor.getID_CARD());
        tdv.tou_detail_wrok.setText(visitor.getOWNED_COMPANY());
        tdv.tou_detail_thing.setText(visitor.getMATTER());
        tdv.tou_detail_time.setText(visitor.getVISITOR_TIME());
        tdv.tou_detail_number.setText(visitor.getVISITOR_NUM());
        tdv.tou_detail_car_card.setText(visitor.getCAR_NUMBER());
        tdv.tou_detail_remarks.setText(visitor.getNOTES());

        if(visitor.getAUDIT_STATUS().equals("0")) {
            tdv.tou_detail_bottom_ll.setVisibility(View.VISIBLE);
        }else {
            tdv.tou_detail_bottom_ll.setVisibility(View.GONE);
        }
    }

    /**
     * 跳转拨号界面
     */
    public void toPhone() {
        final String phone = tdv.tou_detail_phone.getText().toString();
        if (RegularUtils.isMobileLength11(phone)) {
            //需要询问用户
            new AppDialog(touDetailActivity, "是否拨打：" + phone, "取消", "确定") {
                @Override
                public void buttonRight() {
                    super.buttonRight();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    touDetailActivity.startActivity(intent);
                }
            };
        }else {
            ToastUtil.showToast("手机号码不正确");
        }
    }

    /**
     * 拒绝访客
     */
    public void refuseTou() {
        AppProgressDialog.showProgress(touDetailActivity);
        Rest rest = new Rest("/appvisitor/edit.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("AUDITUSER_ID",  userInfo.getUSER_ID());
        rest.addParam("AUDIT_STATUS",  "2");//0：待审核 1：同意 2：拒绝
        rest.addParam("ID",  visitor.getID());
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast("您已拒绝该访客");
                Intent intent = new Intent(touDetailActivity, TouListActivity.class);
                touDetailActivity.setResult(1024, intent);
                touDetailActivity.finish();
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast(msg);
                touDetailActivity.finish();
            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast("网络繁忙，请稍后再试");
                touDetailActivity.finish();
            }
        });


    }

    /**
     * 接受访客
     */
    public void confirmTou() {
        AppProgressDialog.showProgress(touDetailActivity);
        Rest rest = new Rest("/appvisitor/edit.nla", userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
        rest.addParam("AUDITUSER_ID",  userInfo.getUSER_ID());
        rest.addParam("AUDIT_STATUS",  "1");//0：待审核 1：同意 2：拒绝
        rest.addParam("ID",  visitor.getID());
        rest.post(new Callback() {
            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast("您已同意该来访");
                Intent intent = new Intent(touDetailActivity, TouListActivity.class);
                touDetailActivity.setResult(1024, intent);
                touDetailActivity.finish();
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast(msg);
                touDetailActivity.finish();
            }

            @Override
            public void onError(Exception exception) {
                AppProgressDialog.dismiss(touDetailActivity);
                ToastUtil.showToast("网络繁忙，请稍后再试");
                touDetailActivity.finish();
            }
        });
    }


}

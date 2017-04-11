package com.xcoder.smartpark.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.activity.bus.BusListActivity;
import com.xcoder.smartpark.activity.bus.BusMyOrderActivity;
import com.xcoder.smartpark.activity.bus.BusTagActivity;
import com.xcoder.smartpark.activity.tou.TouListActivity;
import com.xcoder.smartpark.activity.tou.TouRegisterActivity;
import com.xcoder.smartpark.activity.use.UseListActivity;
import com.xcoder.smartpark.activity.use.UseMyOrderActivity;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.service.home.HomeSelectService;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import java.io.File;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 班车，访客，用车的选择
 */
@ContentView(R.layout.home_select_main)
public class HomeSelectActivity extends BaseActivity {

    @Injection
    HomeSelectService hss;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        hss.init(this);
    }

    @OnClick({R.id.select_bc_1, R.id.select_bc_2, R.id.select_bc_3,
            R.id.select_yc_1, R.id.select_yc_2, R.id.select_yc_3,
            R.id.select_fk_1, R.id.select_fk_2, R.id.select_fk_3, R.id.select_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_black:
                finish();
                break;
            case R.id.select_bc_1:
                //预约班车
                openActivity(BusListActivity.class);
                break;
            case R.id.select_bc_2:
                //我的预约
                openActivity(BusMyOrderActivity.class);
                break;
            case R.id.select_bc_3:
                //常住地
                openActivity(BusTagActivity.class);
                break;
            case R.id.select_fk_1:
                //预约登记
                Intent intent = new Intent(this, TouRegisterActivity.class);
                startActivityForResult(intent, 9001);
                break;
            case R.id.select_fk_2:


                //分享预约
                String url = AppNet.appNet + AppNet.appvisitor;
//                //  微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
//                new ShareAction(HomeSelectActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("智慧园区欢迎您")
//                        //.withTitle("通行证预约链接")
//                        // .withTargetUrl(url)
//                        //.withMedia(umImage)
//                        .setCallback(umShareListener)
//                        .share();

                //  UMImage thumb = new UMImage(this, thumb_img);
                UMWeb web = new UMWeb(url);
                // web.setThumb(thumb);
                web.setDescription("智慧园区欢迎您");
                web.setTitle("通行证预约链接");
                new ShareAction(this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).share();


                AppProgressDialog.showProgress(this, "连接微信中...");
                break;
            case R.id.select_fk_3:
                //我得访客
                openActivity(TouListActivity.class);
                break;
            case R.id.select_yc_1:
                //预约用车
                openActivity(UseListActivity.class);
                break;
            case R.id.select_yc_2:
                //我得预约
                openActivity(UseMyOrderActivity.class);
                break;
            case R.id.select_yc_3:
                //预约说明
                Bundle bundle = new Bundle();
                bundle.putString("htmlTitle", "预约说明");
                bundle.putString("htmlUrl", AppNet.appNet + AppNet.appcarnotice);
                openActivity(HtmlActivity.class, bundle);
                break;
        }

    }



    private File saveImageFile;//分享时用的图片文件，
    /**
     * 访客分享的回调
     */
    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            try {
                AppProgressDialog.dismiss(HomeSelectActivity.this);
                //  Utils.deleteFile(saveImageFile);
                ToastUtil.showToast(share_media + " 分享成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            try {
                AppProgressDialog.dismiss(HomeSelectActivity.this);
                //   Utils.deleteFile(saveImageFile);
                ToastUtil.showToast(share_media + " 分享失败");
                if (throwable != null) {
                    Log.d("throw", "throw:" + throwable.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            try {
                AppProgressDialog.dismiss(HomeSelectActivity.this);
                //  Utils.deleteFile(saveImageFile);
                ToastUtil.showToast(share_media + " 分享取消");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        try {
            //调用这个方法时，需要把加载框，清除掉，提高用户体验度
            AppProgressDialog.dismiss(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void closeActivity() {

    }
}

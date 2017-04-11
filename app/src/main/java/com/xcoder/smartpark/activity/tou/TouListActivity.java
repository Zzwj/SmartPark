package com.xcoder.smartpark.activity.tou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.moudel.Visitor;
import com.xcoder.smartpark.service.tou.TouListService;
import com.xcoder.smartpark.util.ShareImageUtils;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.util.zxing.EncodingHandler;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 访客列表
 */
@ContentView(R.layout.tou_list_activity)
public class TouListActivity extends BaseActivity {

    @Injection
    TouListService tls;
    private File saveImageFile;//分享时用的图片文件，

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        tls.init(this);
    }

    @OnClick({R.id.tou_list_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tou_list_black:
                finish();
                break;
        }
    }

    /**
     * 当用户退出当前登陆时所需要的做的操作
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Visitor visitor) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_min_ico);
        //得到二维码
        Bitmap bitmapRQ = EncodingHandler.createQRCode(visitor.getID(), 600, 600, bitmap);
        //微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
        saveImageFile = ShareImageUtils.save(bitmapRQ, false);
        UMImage umImage = new UMImage(this, saveImageFile);
        // umImage.setThumb(new UMImage(this, saveImageFile));生成缩略图
        new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
                .withText("智慧园区欢迎您")
                //.withTitle("智慧园区")
                // .withTargetUrl("http://www.baidu.com")
                .withMedia(umImage)
                .setCallback(umShareListener)
                .share();
        // ToastUtil.showToast("正在链接微信，请稍等");
        AppProgressDialog.showProgress(this, "连接微信中...");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1023) {
            //邮件分享回调,需要删掉图片
            Utils.deleteFile(saveImageFile);
        }
        if (requestCode == 1024) {
            if (resultCode == 1024) {
                tls.refresh();
            }
        }
        if (requestCode == 9001) {
            if (resultCode == 9001) {
                tls.refresh();
            }
        }
    }

    /**
     * 分享的回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            try {
                AppProgressDialog.dismiss(TouListActivity.this);
                Utils.deleteFile(saveImageFile);
                ToastUtil.showToast(share_media + " 分享成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            try {
                AppProgressDialog.dismiss(TouListActivity.this);
                Utils.deleteFile(saveImageFile);
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
                AppProgressDialog.dismiss(TouListActivity.this);
                Utils.deleteFile(saveImageFile);
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
    public void closeActivity() {
        try {
            EventBus.getDefault().unregister(this);
            //清楚此对象
            if (tls.refreshAndload != null) {
                tls.refreshAndload.onDestroy();
                tls.refreshAndload = null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}

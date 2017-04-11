//package com.xcoder.smartpark.fragment;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//import com.xcoder.lib.annotation.Injection;
//import com.xcoder.lib.annotation.event.OnClick;
//import com.xcoder.lib.injection.ViewUtils;
//import com.xcoder.lib.utils.LogUtils;
//import com.xcoder.lib.utils.Utils;
//import com.xcoder.smartpark.R;
//import com.xcoder.smartpark.activity.tou.TouRegisterActivity;
//import com.xcoder.smartpark.app.base.BaseFragment;
//import com.xcoder.smartpark.moudel.Visitor;
//import com.xcoder.smartpark.network.AppNet;
//import com.xcoder.smartpark.service.tou.TouService;
//import com.xcoder.smartpark.util.ShareImageUtils;
//import com.xcoder.smartpark.util.ToastUtil;
//import com.xcoder.smartpark.util.zxing.EncodingHandler;
//import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
//import com.xcoder.smartpark.widget.dialog.ShareSubDialog;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.File;
//
///**
// * Created by xcoder_xz on 2016/12/6 0006.
// * 访客
// */
//
//public class TouFragment extends BaseFragment {
//
//    private View view;
//    @Injection
//    TouService ts;
//    private ShareSubDialog subscribeDialog;
//    private File saveImageFile;//分享时用的图片文件，
//
//    @Override
//    public View initLayout(LayoutInflater inflater) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.tou_list_activity, null);
//        }
//        ViewUtils.inject(this, view);//这一句相当于初始化view了
//        return view;
//
//    }
//
//    /*访客*
//     * 开始数据操作
//     */
//    @Override
//    public void initData() {
//        super.initData();
//        EventBus.getDefault().register(this);
//        ts.init(this);
//    }
//
//    @OnClick({R.id.tou_share_tv, R.id.tou_register_tv})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tou_share_tv:
////                ShareSubUrlDialog subscribeDialog = new ShareSubUrlDialog(getContext());
////                subscribeDialog.show();
////                //短信分享
////                subscribeDialog.tou_subscribe_sms.setOnClickListener(shareUrlClick);
////                //邮件分享
////                subscribeDialog.tou_subscribe_email.setOnClickListener(shareUrlClick);
////                //微信分享
////                subscribeDialog.tou_subscribe_wechat.setOnClickListener(shareUrlClick);
//
//                String url = AppNet.appNet + AppNet.appvisitor;
//                //  微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
//                new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.WEIXIN)
//                        .withText("智慧园区欢迎您")
//                        .withTitle("通行证预约链接")
//                        .withTargetUrl(url)
//                        //.withMedia(umImage)
//                        .setCallback(umShareListener)
//                        .share();
//                // ToastUtil.showToast("正在链接微信，请稍等");
//                AppProgressDialog.showProgress(getContext(), "连接微信中...");
//
//                break;
//            case R.id.tou_register_tv:
//                //openActivity(TouRegisterActivity.class);
//                Intent intent = new Intent(getContext(), TouRegisterActivity.class);
//                startActivityForResult(intent, 9001);
//                break;
//
//        }
//    }
//
//    /**
//     * URl分享的点击
//     */
////    View.OnClickListener shareUrlClick = new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            try {
////                String url = AppNet.appNet + AppNet.appvisitor;
////                switch (v.getId()) {
////                    case R.id.tou_subscribe_url_sms:
////                        //短信分享
////                        //操蛋的友盟，非要把bitmap、转成文件，短信的才可以//有些手机还是不行
////                        new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.SMS)
////                                .withText("智慧园区欢迎您 请点击链接    " + url)
////                                .withTargetUrl(url)
////                                .setCallback(umShareListener)
////                                .share();
////                        break;
////                    case R.id.tou_subscribe_url_email:
//////                    //邮件分享
////                        Intent emailTent = new Intent(android.content.Intent.ACTION_SEND);
////                        emailTent.setType("plain/text");
////                        String emailBody = url;
////                        //邮件主题
////                        emailTent.putExtra(android.content.Intent.EXTRA_SUBJECT, "通行证预约链接");
////                        //邮件内容
////                        emailTent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
////                        emailTent.setType("message/rfc882");
////                        Intent.createChooser(emailTent, "请选择平台发送邮件");
////                        startActivity(emailTent);
////                        break;
////                    case R.id.tou_subscribe_url_wechat:
////                        //微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
////                        new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.WEIXIN)
////                                .withText("智慧园区欢迎您")
////                                .withTitle("通行证预约链接")
////                                .withTargetUrl(url)
////                                //.withMedia(umImage)
////                                .setCallback(umShareListener)
////                                .share();
////                        // ToastUtil.showToast("正在链接微信，请稍等");
////                        AppProgressDialog.showProgress(getContext(), "连接微信中...");
////                        break;
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    };
//
//    /**
//     * 当用户退出当前登陆时所需要的做的操作
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(Visitor visitor) {
//
//
//        LogUtils.d("visitor=" + visitor.toString());
//
////        subscribeDialog = new ShareSubDialog(getContext());
////        subscribeDialog.sendRQString = visitor.getID();
////        subscribeDialog.subName = "尊敬的:" + visitor.getNAME();//同行人
////        subscribeDialog.subCard = "通行码:" + visitor.getPASS_NUMBER();//同行码
////        subscribeDialog.subTime = "到访时间:" + visitor.getVISITOR_TIME();//同行时间
////        subscribeDialog.show();
////        //短信分享
////        subscribeDialog.tou_subscribe_sms.setOnClickListener(shareClick);
////        //邮件分享
////        subscribeDialog.tou_subscribe_email.setOnClickListener(shareClick);
////        //微信分享
////        subscribeDialog.tou_subscribe_wechat.setOnClickListener(shareClick);
////
//       Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_min_ico);
//        //得到二维码
//        Bitmap  bitmapRQ = EncodingHandler.createQRCode(visitor.getID(), 600, 600, bitmap);
//        //微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
//        saveImageFile = ShareImageUtils.save(bitmapRQ, false);
//        UMImage umImage = new UMImage(getContext(), saveImageFile);
//        // umImage.setThumb(new UMImage(getContext(), saveImageFile));生成缩略图
//        new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.WEIXIN)
//                .withText("智慧园区欢迎您")
//                //.withTitle("智慧园区")
//                // .withTargetUrl("http://www.baidu.com")
//                .withMedia(umImage)
//                .setCallback(umShareListener)
//                .share();
//        // ToastUtil.showToast("正在链接微信，请稍等");
//        AppProgressDialog.showProgress(getContext(), "连接微信中...");
//    }
//
//
//    /**
//     * 图片的分享的点击
//     */
////    private View.OnClickListener shareClick = new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            switch (v.getId()) {
////                case R.id.tou_subscribe_sms:
////                    //短信分享
////                    if (subscribeDialog.bitmapRQ != null) {
////                        //操蛋的友盟，非要把bitmap、转成文件，短信的才可以//有些手机还是不行
////                        saveImageFile = ShareImageUtils.save(subscribeDialog.bitmapRQ, false);
////                        new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.SMS)
////                                .withText("智慧园区欢迎您").withMedia(new UMImage(getContext(), saveImageFile))
////                                .setCallback(umShareListener)
////                                .share();
////                    }
////                    break;
////                case R.id.tou_subscribe_email:
////                    saveImageFile = ShareImageUtils.save(subscribeDialog.bitmapRQ, false);
////                    //邮件分享
////                    Intent emailTent = new Intent(android.content.Intent.ACTION_SEND);
////                    emailTent.setType("plain/text");
////                    String emailBody = "智慧园区欢迎你";
////                    //邮件主题
////                    emailTent.putExtra(android.content.Intent.EXTRA_SUBJECT, "智慧园区通行证二维码::进入园区时请出示此通行证给门岗");
////                    //邮件内容
////                    emailTent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
////                    //图片
////                    emailTent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + saveImageFile.getAbsolutePath()));
////                    emailTent.setType("image/*");
////                    emailTent.setType("message/rfc882");
////                    Intent.createChooser(emailTent, "请选择平台发送邮件");
////                    startActivityForResult(emailTent, 1023);
//////                     new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.EMAIL)
//////                   // 友盟发送邮件，有毛病，没时间，不深究，自己写了个
//////                            .withText("智慧园区欢迎您:进入园区时请出示此通行证给门岗")
//////                            .withMedia(new UMImage(getContext(), saveImageFile))
//////                            .setCallback(umShareListener)
//////                            .share();
////                    break;
////                case R.id.tou_subscribe_wechat:
////                    //微信分享,,如果分享链接，就不能设置图片分享，如果是图片，就不能使用链接
////                    saveImageFile = ShareImageUtils.save(subscribeDialog.bitmapRQ, false);
////                    UMImage umImage = new UMImage(getContext(), saveImageFile);
////                    // umImage.setThumb(new UMImage(getContext(), saveImageFile));生成缩略图
////                    new ShareAction((Activity) getContext()).setPlatform(SHARE_MEDIA.WEIXIN)
////                            .withText("智慧园区欢迎您")
////                            //.withTitle("智慧园区")
////                            // .withTargetUrl("http://www.baidu.com")
////                            .withMedia(umImage)
////                            .setCallback(umShareListener)
////                            .share();
////                    // ToastUtil.showToast("正在链接微信，请稍等");
////                    AppProgressDialog.showProgress(getContext(), "连接微信中...");
////                    break;
////            }
////        }
////    };
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1023) {
//            //邮件分享回调,需要删掉图片
//            Utils.deleteFile(saveImageFile);
//        }
//        if (requestCode == 1024) {
//            if (resultCode == 1024) {
//                ts.refresh();
//            }
//        }
//        if (requestCode == 9001) {
//            if (resultCode == 9001) {
//                ts.refresh();
//            }
//        }
//    }
//
//
//    /**
//     * 分享的回调
//     */
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA share_media) {
//            try {
//                AppProgressDialog.dismiss(getContext());
//                Utils.deleteFile(saveImageFile);
//                ToastUtil.showToast(share_media + " 分享成功");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        @Override
//        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//            try {
//                AppProgressDialog.dismiss(getContext());
//                Utils.deleteFile(saveImageFile);
//                ToastUtil.showToast(share_media + " 分享失败");
//                if (throwable != null) {
//                    Log.d("throw", "throw:" + throwable.getMessage());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA share_media) {
//            try {
//                AppProgressDialog.dismiss(getContext());
//                Utils.deleteFile(saveImageFile);
//                ToastUtil.showToast(share_media + " 分享取消");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        try {
//            //调用这个方法时，需要把加载框，清除掉，提高用户体验度
//            AppProgressDialog.dismiss(getContext());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void closeFragment() {
//        try {
//            EventBus.getDefault().unregister(this);
//            //清楚此对象
//            if (ts.refreshAndload != null) {
//                ts.refreshAndload.onDestroy();
//                ts.refreshAndload = null;
//            }
//        } catch (Exception exc) {
//            exc.printStackTrace();
//        }
//    }
//}

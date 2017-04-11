package com.xcoder.smartpark.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xcoder.lib.app.AppPathManager;
import com.xcoder.lib.utils.Utils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.SmartParkApplication;
import com.xcoder.smartpark.moudel.AppUpdateMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.FileCallback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by xcocder_xz on 2017/1/2 0002.
 * app更新
 */

public class AppUpdate {
    private String saveFilePath = ""; // 存储路径
    private Context context;
    private int version_code = 0;//版本号
    private String fileName="";//下载后需要保存的apk的文件名
    private String apkUrl="";


    public AppUpdate(Context context) {
        this.context = context;
        File fileDir = new File(AppPathManager.getSaveApkPath());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        //判断apk文件的目录是否存在，
        saveFilePath = AppPathManager.getSaveApkPath();
    }

    /**
     * @param type 0-默认判断是否可以更新，1-用户手动点击判断是否可以更新
     */
    public void getVersion(final int type) {
        if (type == 1) {
            AppProgressDialog.showProgress(context, "检查中...");
        }
        Rest rest = new Rest("/appversion/getAndroidV.nla");
        rest.post("AppUpdate", new Callback() {


            @Override
            public void onSuccess(JSONObject jsonObject, String state, String msg) {
                try {
                    if (type == 1) {
                        AppProgressDialog.dismiss(context);
                    }
                    JSONObject dataset = jsonObject.getJSONObject("dataset");
                    AppUpdateMoudle appUpdateMoudle = JSON.parseObject(dataset.toString(), AppUpdateMoudle.class);
                    //因为破服务器，可能会给null或者“”，直接转double，会出错，先判断
                    if(TextUtils.isEmpty(appUpdateMoudle.getVERSION_CODE())){
                        return;
                    }
                    if (Integer.valueOf(appUpdateMoudle.getVERSION_CODE()) > getCode()) {
                        version_code = Integer.valueOf(appUpdateMoudle.getVERSION_CODE());
                        fileName="SmarkPart_"+version_code;
                        apkUrl=appUpdateMoudle.getFILEPATH();
                            //判断是否强制更新
                        if (TextUtils.equals(appUpdateMoudle.getMANDATORYUPGRADE(), "0")) {
                            new AppDialog(context, "版本更新", appUpdateMoudle.getUPDATE_DESCRIPTION(), "取消","更新") {
                                @Override
                                public void buttonRight() {
                                    super.buttonRight();
                                    createLoadingDialog();
                                }
                            };
                        } else {
                            new AppDialog(context, "版本更新", appUpdateMoudle.getUPDATE_DESCRIPTION(), "更新", true) {
                                @Override
                                public void buttonRight() {
                                    super.buttonRight();
                                    createLoadingDialog();
                                }
                            };
                        }
                    } else {
                        if (type == 1) {
                            ToastUtil.showToast("已经是最新版本了");
                        }
                    }
                } catch (Exception e) {
                    onError(e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                if (type == 1) {
                    AppProgressDialog.dismiss(context);
                    ToastUtil.showToast(msg);
                }
            }

            @Override
            public void onError(Exception exception) {
                if (type == 1) {
                    AppProgressDialog.dismiss(context);
                    ToastUtil.noNet();
                }
            }
        });
    }

    private AlertDialog dialog;

    /**
     *
     */
    public void createLoadingDialog() {
        LayoutInflater flat = LayoutInflater.from(context);
        View v = flat.inflate(R.layout.app_update_dialog, null);
        final ProgressBar app_update_pb = (ProgressBar) v.findViewById(R.id.app_update_pb);
        final TextView app_update_tv = (TextView) v.findViewById(R.id.app_update_tv);
        // 获取"取消"按钮
        final Button app_update_cancel = (Button) v.findViewById(R.id.app_update_cancel);
        app_update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dialog != null) {
                        OkHttpUtils.getInstance().cancelTag("AppUpdate");
                        ToastUtil.showToast("停止下载");
                        dialog.dismiss();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // 创建对话
        dialog = new AlertDialog.Builder(context).create();
        // 设置返回键失
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // dialog.getWindow().setType(
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // 显示对话
        dialog.show();
        // 必须放到显示对话框下面，否则显示不出效果
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Utils.getScreenWidth(context) * 4 / 5;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        // 加载布局组件
        dialog.getWindow().setContentView(v);
        Rest rest = new Rest("");
        rest.getFile("AppUpdate", apkUrl, saveFilePath, fileName, new FileCallback() {
            @Override
            public void onSuccess(File response, String state, String msg) {
                app_update_tv.setText("下载完毕，请稍后");
                app_update_cancel.setText("安装");
                dialog.dismiss();
                installApk(response);
            }

            @Override
            public void inProgress(float progress, long total) {
                app_update_pb.setProgress((int) (100 * progress));
                app_update_tv.setText("正在下载:" + ((int) (100 * progress)) + "/100");
            }

            @Override
            public void onFailure(Exception error, String state, String msg) {
                if (msg.equals("文件已存在")) {
                    dialog.dismiss();
                    new AppDialog(context, "版本更新", "文件已存在", "安装", "重新下载") {
                        @Override
                        public void buttonLeft() {
                            super.buttonLeft();
                            File file = new File(saveFilePath + fileName);
                            installApk(file);
                        }

                        @Override
                        public void buttonRight() {
                            super.buttonRight();
                            //删除旧文件，并重新下载
                            delApk(new File(saveFilePath + fileName));
                            createLoadingDialog();
                        }
                    };

                } else {
                    app_update_tv.setText(msg);
                }
            }

            @Override
            public void onError() {
                app_update_tv.setText("请检查网络");
            }
        });
    }


    /**
     * 安装APK文件
     */
    public void installApk(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + file.toString()),
                "application/vnd.android.package-archive");
        context.startActivity(i);
    }


    public int getCode() {
        int code = 0;
        try {
            PackageManager manager = SmartParkApplication.context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(SmartParkApplication.context.getPackageName(), 0);
            code = info.versionCode;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return code;
    }


    /**
     * 重新下载文件之前 需要删除已存在的apk 否则出现安装包解析错误
     */
    private void delApk(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        file.delete();
    }

}

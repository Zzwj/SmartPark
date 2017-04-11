package com.xcoder.smartpark.app;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.xcoder.smartpark.widget.dialog.AppDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * app动态权限管理
 * Created by xz on 2016/10/19 0019.
 */

public abstract class AppRuntimePermission {

    /**
     * group:android.permission-group.CONTACTS//联系人
     * permission:android.permission.WRITE_CONTACTS//允许一个应用程序编写用户的联系人数据。
     * permission:android.permission.GET_ACCOUNTS//允许访问的帐户的帐户列表服务。
     * permission:android.permission.READ_CONTACTS//允许应用程序读取用户的联系人数据。
     * <p>
     * group:android.permission-group.PHONE//通话记录
     * permission:android.permission.READ_CALL_LOG//允许应用程序读取用户的通话记录。
     * permission:android.permission.READ_PHONE_STATE//允许只读访问手机状态,包括设备的电话号码、当前的蜂窝网络信息,任何正在进行的调用的状态,任何PhoneAccounts注册设备的列表。
     * permission:android.permission.CALL_PHONE//允许应用程序启动一个电话不经过用户确认的拨号器的用户界面。
     * permission:android.permission.WRITE_CALL_LOG//允许一个应用程序编写(但不是读)用户的通话记录数据。
     * permission:android.permission.USE_SIP//允许一个应用程序使用SIP服务。
     * permission:android.permission.PROCESS_OUTGOING_CALLS//允许应用程序看到的数量被打在一个外向与选择的电话呼叫重定向到一个完全不同的号码或终止调用。
     * permission:com.android.voicemail.permission.ADD_VOICEMAIL//允许一个应用程序添加到系统的语音邮件。
     * <p>
     * group:android.permission-group.CALENDAR//日历
     * permission:android.permission.READ_CALENDAR//允许应用程序读取用户的日历数据。
     * permission:android.permission.WRITE_CALENDAR//允许一个应用程序编写用户的日历数据。
     * <p>
     * group:android.permission-group.CAMERA
     * permission:android.permission.CAMERA//需要能够访问摄像头设备。
     * <p>
     * group:android.permission-group.SENSORS
     * permission:android.permission.BODY_SENSORS//允许应用程序访问数据从用户使用的传感器,用于测量发生了什么他/她的体内,如心率。
     * <p>
     * group:android.permission-group.LOCATION//位置
     * permission:android.permission.ACCESS_FINE_LOCATION//允许应用程序访问的精确位置。
     * permission:android.permission.ACCESS_COARSE_LOCATION//允许应用程序访问近似位置。
     * <p>
     * group:android.permission-group.STORAGE//sd卡
     * permission:android.permission.READ_EXTERNAL_STORAGE//允许一个应用程序从外部存储器读取数据。
     * permission:android.permission.WRITE_EXTERNAL_STORAGE//允许一个应用程序编写外部存储器。
     * <p>
     * group:android.permission-group.MICROPHONE
     * permission:android.permission.RECORD_AUDIO//允许一个应用程序来记录音频。
     * <p>
     * group:android.permission-group.SMS//短信息块
     * permission:android.permission.READ_SMS//允许应用程序读取短信。
     * permission:android.permission.RECEIVE_WAP_PUSH//允许一个应用程序接收WAP推送消息。
     * permission:android.permission.RECEIVE_MMS//允许应用程序监控传入的MMS消息。
     * permission:android.permission.RECEIVE_SMS//允许一个应用程序接收短信。
     * permission:android.permission.SEND_SMS//允许一个应用程序发送SMS消息。
     * permission:android.permission.READ_CELL_BROADCASTS ???
     **/
    private Activity activity;
    public final String CONTACTS = Manifest.permission.WRITE_CONTACTS;//联系人权限
    public final String PHONE = Manifest.permission.READ_CALL_LOG;//通话记录权限
    public final String CALENDAR = Manifest.permission.READ_CALENDAR;//日历
    public final String CAMERA = Manifest.permission.CAMERA;//相机权限
    public final String SENSORS = Manifest.permission.BODY_SENSORS;//传感器
    public final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//位置权限
    public final String STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;//sd卡权限
    public final String MICROPHONE = Manifest.permission.RECORD_AUDIO;//音频权限
    public final String SMS = Manifest.permission.READ_SMS;//短信息权限
    private static final int MY_PERMISSIONS = 1213;//code码
    private String[] permission;

    public AppRuntimePermission(Activity activity) {
        this.activity = activity;
        setPermissionStatus();
    }

    /**
     * 判断需要授权，需要提醒的权限
     */
    public void setPermissionStatus() {
        if (Build.VERSION.SDK_INT < 23) {
            perTrue();
            return;
        }

        // 得到app需要申请授权的权限
        permission = new String[]{CAMERA, LOCATION, STORAGE,SMS,CONTACTS};
        List<String> listPermission = new ArrayList<String>();//得到用户没有授权的权限
        for (String per : permission) {
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                //得到需要进行授权的
                listPermission.add(per);
            }
        }
        //先提示用户需要授权的权限
        String[] goPermission = new String[listPermission.size()];
        listPermission.toArray(goPermission);
        if (goPermission.length > 0) {
            //展出提示框，授权
            ActivityCompat.requestPermissions(activity, goPermission, MY_PERMISSIONS);
        } else {
            perTrue();
        }
    }

    /**
     * @param requestCode:code
     * @param permissions：权限
     * @param grantResults:权限验证结果(-1,禁止（无论用户是否点击不再提示）)
     */
    public void MyRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS: {
                //如果请求被取消了,结果数组是空的。
                if (permissions.length > 0 && grantResults.length > 0) {
                    //循环判断，用户禁止了的权限
                    for (int i : grantResults) {
                        if (i == -1) {
                            getNoPermission();
                            return;
                        }
                    }
                    perTrue();
                } else {
                    perTrue();
                }
                return;
            }
        }
    }

    public void getNoPermission() {

        String strToast = "智慧园区需要";
        for (String per : permission) {
            //CAMERA, LOCATION, STORAGE,SMS,CONTACTS,PHONE
            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
                if (per.equals(CAMERA))
                    strToast += "相机、";
                else if (per.equals(LOCATION))
                    strToast += "位置、";
                else if (per.equals(STORAGE))
                    strToast += "文件存储、";
                else if (per.equals(SMS))
                    strToast += "短信、";
                else if(per.endsWith(CONTACTS))
                    strToast += "联系人、";

            }

        }
        strToast = strToast.substring(0, strToast.length() - 1) + "权限,是否去设置";
        AppDialog appDialog = new AppDialog(activity,  strToast, "取消", "确定") {
            @Override
            public void buttonRight() {
                super.buttonRight();
                Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                String pkg = "com.android.settings";
                String cls = "com.android.settings.applications.InstalledAppDetails";
                i.setComponent(new ComponentName(pkg, cls));
                i.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(i);
                activity.finish();
            }

            @Override
            public void buttonLeft() {
                super.buttonLeft();
                activity.finish();
            }
        };
    }
    public abstract void perTrue();


//    /** http://blog.csdn.net/lmj623565791/article/details/50709663
//     * 检查权限，
//     */
//    public void requestPermission() {
//        // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    Manifest.permission.READ_CONTACTS)) {
//                // 这个API主要用于给用户一个申请权限的解释，
//                // 该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，
//                // 你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。
//                Toast.makeText(activity, "用户", Toast.LENGTH_SHORT).show();
//            } else {
//
//                // No explanation needed, we can request the permission.
//  ActivityCompat.requestPermissions(thisActivity,
//    new String[]{Manifest.permission.READ_CONTACTS},
//    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//        } else {
//            int i = 0;
//        }
//    }

//    /**
//     * 判断需要授权，需要提醒的权限
//     */
//    public void getPermissionStatus(String[] permission) {
//        /**
//         * 得到app需要申请授权的权限
//         */
//        List<String> listPermission = new ArrayList<String>();//得到用户没有授权的权限
//        for (String per : permission) {
//            // 检查权限，主要用于检测某个权限是否已经被授予，为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。
//            if (ContextCompat.checkSelfPermission(activity, per) != PackageManager.PERMISSION_GRANTED) {
//                //得到需要进行授权的
//                listPermission.add(per);
//            }
//        }
//        /**
//         * 判断用户是否禁掉此权限，如果禁掉，展出提示框，提示用户为什么需要打开这个权限
//         */
//        //用户禁止的权限,等用户确认完提示授权框后，再判断用户一共禁止了多少权限
//        listNoPer = new ArrayList<String>();
//        List<String> listGoPer = new ArrayList<String>();//去用户授权
//        for (String per : listPermission) {
//            // 这个API主要用于给用户一个申请权限的解释，
//            // 该方法只有在用户在上一次已经拒绝过你的这个权限申请。也就是说，用户已经拒绝一次了，
//            // 你又弹个授权框，你需要给用户一个解释，为什么要授权，则使用该方法。
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    per)) {
//                listNoPer.add(per);
//            } else {
//                listGoPer.add(per);
//            }
//        }
//        //先提示用户需要授权的权限
//        String[] goPer = new String[listGoPer.size()];
//        listGoPer.toArray(goPer);
//        //展出提示框，授权
//        ActivityCompat.requestPermissions(activity, goPer, MY_PERMISSIONS);
//    }
}

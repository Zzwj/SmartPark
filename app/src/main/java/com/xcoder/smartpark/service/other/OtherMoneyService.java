package com.xcoder.smartpark.service.other;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.activity.other.OtherMoneyActivity;

import com.xcoder.smartpark.view.other.OtherMoneyView;

/**
 * Created by jiangkun on 17/2/14.
 */

public class OtherMoneyService {

    @Injection
    private OtherMoneyView otherMoneyView;

    private OtherMoneyActivity otherMoneyActivity;

    private BluetoothAdapter mBluetoothAdapter;
    private TextView mTvBLEScan;

    public void init(OtherMoneyActivity otherMoneyActivity){
        this.otherMoneyActivity = otherMoneyActivity;
    }

    /**
     * 判断是否支持蓝牙低功耗广播(4.3+)<br>
     *
     * 如果返回true,说明支持iBeacon消息的接收
     * @return
     */
    public boolean checkBleObserverSupport() {
        return otherMoneyActivity.getPackageManager().hasSystemFeature(
                "android.hardware.bluetooth_le");
    }

    /**
     * 判断是否支持LE<br>
     *
     * 如果返回ture,说明支持iBeacon 的发送
     * @return
     */
    @SuppressLint("NewApi")
    public boolean checkBleBroadcasterSupport() {
        if(this.mBluetoothAdapter == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            // .isMultipleAdvertisementSupported() 这里报错,需要用5.0以上编译
            if (this.mBluetoothAdapter.isMultipleAdvertisementSupported()) {
                LogUtils.d("support peripheral mode, api support");
                return true;
            } else if (null != mBluetoothAdapter.getBluetoothLeAdvertiser()) {
                LogUtils.d("support peripheral mode, BluetoothLeAdvertiser is not null");
                return true;
            }
        }
        LogUtils.d("Build.VERSION.SDK_INT = "+Build.VERSION.SDK_INT);
        LogUtils.d("this device not support peripheral mode");
        return false;
    }

}

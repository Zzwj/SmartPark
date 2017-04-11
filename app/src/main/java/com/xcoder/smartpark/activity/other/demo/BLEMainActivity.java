package com.xcoder.smartpark.activity.other.demo;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.minew.beacon.BeaconValueIndex;
import com.minew.beacon.BluetoothState;
import com.minew.beacon.MinewBeacon;
import com.minew.beacon.MinewBeaconManager;
import com.minew.beacon.MinewBeaconManagerListener;
import com.xcoder.lib.utils.LogUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.other.BLEActivity;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.moudel.AppUser;
import com.xcoder.smartpark.moudel.SubscribeHistoryMoudle;
import com.xcoder.smartpark.network.Callback;
import com.xcoder.smartpark.network.Rest;
import com.xcoder.smartpark.util.ConfigSP;
import com.xcoder.smartpark.util.ToastUtil;
import com.xcoder.smartpark.widget.dialog.AppDialog;
import com.xcoder.smartpark.widget.dialog.AppProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class BLEMainActivity extends BaseActivity {

    private MinewBeaconManager mMinewBeaconManager;
    private RecyclerView mRecycle;
    private BeaconListAdapter mAdapter;
    private static final int REQUEST_ENABLE_BT = 2;
    private boolean isScanning;

    private LinearLayout ble_back_iv;

    private AppUser userInfo;//用户信息

    UserRssi comp = new UserRssi();
    private TextView mStart_scan;

    private String room = "assets_ibeancon.room_prefix";
    private String goods = "assets_ibeancon.goods_prefix";

    Map kkmap = new HashMap();

    List<MinewBeacon> minewBeaconList = new ArrayList();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            //ToastUtil.showToast("收到消息------");

            if (isScanning) {
                isScanning = false;
                mStart_scan.setText("开始扫描");
                if (mMinewBeaconManager != null) {
                    mMinewBeaconManager.stopScan();
                }
            }

            if(timer != null) {
                timer.cancel();
            }

            int roomcount = 0, goodscount = 0;
            for(int i=0; i<minewBeaconList.size(); i++) {
                MinewBeacon minewBeacon = minewBeaconList.get(i);
                if(minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue().substring(0,11).replaceAll(":","").equals(kkmap.get(room).toString())) {
                    //房间标签
                    roomcount++;
                }else {
                    //设备标签
                    goodscount++;
                }
            }

            if(roomcount == 0) {
                //没有扫描到房间标签
                ToastUtil.showToast("没有扫描到位置标签，请重新扫描");
                return;
            }

            if(goodscount == 0) {
                ToastUtil.showToast("没有扫描到设备标签，请重新扫描");
                return;
            }

            if(minewBeaconList != null && minewBeaconList.size() > 0) {
                LogUtils.d("扫描完毕，共发现"+roomcount+"个房间，"+goodscount +"个设备");
//                new AppDialog(BLEMainActivity.this, "扫描完毕，共发现"+goodscount +"个设备，请提交资产信息", "确定") {
//                    @Override
//                    public void buttonRight() {
//                        super.buttonRight();
//                        submit();
//                    }
//                };

                submit();
            }else {
                ToastUtil.showToast("没有扫描到资产信息");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate---");
    }

    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }

    private void initView() {
        mStart_scan = (TextView) findViewById(R.id.start_scan);

        mRecycle = (RecyclerView) findViewById(R.id.recyeler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(layoutManager);
        mAdapter = new BeaconListAdapter();
        mRecycle.setAdapter(mAdapter);
        mRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));

        ble_back_iv = (LinearLayout) findViewById(R.id.ble_back_iv);
        ble_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userInfo = ConfigSP.getUserInfo();
        getMacMsg();
    }

    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }


    private void initListener() {
        mStart_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isScanning) {
                    //扫描中暂时不做处理
//                    isScanning = false;
//                    mStart_scan.setText("开始扫描");
//                    if (mMinewBeaconManager != null) {
//                        mMinewBeaconManager.stopScan();
//                    }
                } else {
                    isScanning = true;
                    mStart_scan.setText("扫描中...");
                    minewBeaconList.clear();
                    mAdapter.setData(minewBeaconList,kkmap);

                    try {
                        mMinewBeaconManager.startScan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mMinewBeaconManager.setDeviceManagerDelegateListener(new MinewBeaconManagerListener() {
            /**
             *   if the manager find some new beacon, it will call back this method.
             *
             *  @param minewBeacons  new beacons the manager scanned
             */
            @Override
            public void onAppearBeacons(List<MinewBeacon> minewBeacons) {
                //Toast.makeText(getApplicationContext(),"onAppearBeacons",Toast.LENGTH_SHORT).show();
                LogUtils.d("onAppearBeacons-----");
            }
            /**
             *  if a beacon didn't update data in 10 seconds, we think this beacon is out of rang, the manager will call back this method.
             *
             *  @param minewBeacons beacons out of range
             */
            @Override
            public void onDisappearBeacons(List<MinewBeacon> minewBeacons) {
                /*for (MinewBeacon minewBeacon : minewBeacons) {
                    String deviceName = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_Name).getStringValue();
                    Toast.makeText(getApplicationContext(), deviceName + "  out range", Toast.LENGTH_SHORT).show();
                }*/
                LogUtils.d("onDisappearBeacons------");
            }
            /**
             *  the manager calls back this method every 1 seconds, you can get all scanned beacons.
             *
             *  @param minewBeacons all scanned beacons
             */
            @Override
            public void onRangeBeacons(List<MinewBeacon> minewBeacons) {
                try {
                    if(minewBeacons != null && minewBeacons.size() > 0) {
                        LogUtils.d("size="+minewBeacons.size());
                        Collections.sort(minewBeacons, comp);

                        HashSet tmpmacset = new HashSet();

                        for(int j=0; j<minewBeaconList.size(); j++) {
                            //TmpmacList.add(minewBeaconList.get(i).getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue());
                            tmpmacset.add(minewBeaconList.get(j).getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue());
                        }

                        for(int i=0; i<minewBeacons.size(); i++) {
                            String  beaconmac =  minewBeacons.get(i).getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue();
                            if(tmpmacset.add(beaconmac)) {
                                minewBeaconList.add(minewBeacons.get(i));
                                if(timer != null) {
                                    timer.cancel();
                                }
                                startTimer();
                            }
                        }

                        mAdapter.setData(minewBeaconList,kkmap);
                        LogUtils.d("onRangeBeacons------");
                    }else {
                        LogUtils.d("minewBeacons == null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.d("e======:",e);
                }

            }
            /**
             *  the manager calls back this method when BluetoothStateChanged.
             *
             *  @param state BluetoothState
             */
            @Override
            public void onUpdateState(BluetoothState state) {
                switch (state) {
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOn", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "BluetoothStatePowerOff", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stop scan
        if (isScanning) {
            mMinewBeaconManager.stopScan();
        }

        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        initView();
        initManager();
        checkBluetooth();
        initListener();
    }

    @Override
    public void closeActivity() {
        if (isScanning) {
            mMinewBeaconManager.stopScan();
        }

        if(timer != null) {
            timer.cancel();
        }
    }

    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                break;
        }
    }

    Timer timer;
    public void startTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                // 需要做的事
                LogUtils.d("15秒没扫描到新的ibeacon");
                handler.sendEmptyMessage(0);
            }
        };

        timer.schedule(task,20000);
    }
    /**
     * 获取mac地址对应关系
     */
    public void getMacMsg() {
        try {
            Rest rest = new Rest("/appassetsgoods/queryIbeaconPreFix.nla",userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
            rest.post("BLEMainActivity", new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    LogUtils.d("接口返回："+jsonObject.toString());
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("dataset_line");
                        JSONObject obj0 = jsonArray.getJSONObject(0);
                        JSONObject obj1 = jsonArray.getJSONObject(1);

                        kkmap.put(obj0.get("P_KEY"), obj0.get("P_VALUE"));
                        kkmap.put(obj1.get("P_KEY"), obj1.get("P_VALUE"));

                        LogUtils.d("map======="+kkmap.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.d("e:",e);
                    }
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                }

                @Override
                public void onError(Exception exception) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Exception:",e);
        }
    }

    //提交资产信息
    public void submit(){
        try {
            List ASSETS = new ArrayList();
            Map ASSETSLIST = new HashMap();
            String roommac = "";
            String goodsmac = "";
            for(int i=0; i<minewBeaconList.size(); i++) {
                MinewBeacon minewBeacon = minewBeaconList.get(i);
                if(minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue().substring(0,11).replaceAll(":","").equals(kkmap.get(room).toString())) {
                    roommac = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue();
                    roommac = roommac.replaceAll(":","");
                }else {
                    goodsmac = minewBeacon.getBeaconValue(BeaconValueIndex.MinewBeaconValueIndex_MAC).getStringValue();
                    goodsmac = goodsmac.replaceAll(":","");
                    Map tmpmap = new HashMap();
                    tmpmap.put("MAC",goodsmac);
                    ASSETS.add(tmpmap);
                }

            }
            ASSETSLIST.put("MAC",roommac);
            ASSETSLIST.put("ASSETS",ASSETS);
            //测试用的假数据
//            ASSETS.clear();
//            Map tmpmap = new HashMap();
//            tmpmap.put("MAC","AC233FB0024F");
//            ASSETS.add(tmpmap);
//
//            ASSETSLIST.put("MAC","AC243FB0024F");
//            ASSETSLIST.put("ASSETS",ASSETS);

            String jsonasset =  JSON.toJSONString(ASSETSLIST);



            AppProgressDialog.showProgress(BLEMainActivity.this);
            Rest rest = new Rest("/appassetsgoods/uploadAssetsLocation.nla",userInfo.getUSER_ID(), userInfo.getUSER_TOKEN());
            rest.addParam("USERID",userInfo.getUSER_ID());
            rest.addParam("ASSETSLIST",jsonasset);
            rest.post("BLEMainActivity", new Callback() {
                @Override
                public void onSuccess(JSONObject jsonObject, String state, String msg) {
                    AppProgressDialog.dismiss(BLEMainActivity.this);
                    LogUtils.d("接口返回："+jsonObject.toString());
                    try {
                        ToastUtil.showToast("提交成功");
                        JSONArray jsonArray = jsonObject.getJSONArray("dataset_line");
                        List<BLEEntity>  BLEEntityList= JSON.parseArray(jsonArray.toString(), BLEEntity.class);

                        if(minewBeaconList != null && minewBeaconList.size() > 0) {
                            minewBeaconList.clear();
                            mAdapter.setData(minewBeaconList,kkmap);
                        }

                        if(BLEEntityList != null && BLEEntityList.size() > 0) {
                            LogUtils.d("BLEEntityList.size()="+BLEEntityList.size());

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("BLEEntityList",(Serializable)BLEEntityList);
                            openActivity(BLEListActivity.class, bundle);
                        }else {
                            LogUtils.d("BLEEntityList.size()=null");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.d("e:",e);
                    }
                }

                @Override
                public void onFailure(JSONObject rawJsonObj, String state, String msg) {
                    AppProgressDialog.dismiss(BLEMainActivity.this);
                    ToastUtil.showToast(msg);
                }

                @Override
                public void onError(Exception exception) {
                    AppProgressDialog.dismiss(BLEMainActivity.this);
                    LogUtils.e("exception:",exception);
                    ToastUtil.showToast("提交失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("Exception:",e);
        }
    }

}

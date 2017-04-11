package com.xcoder.smartpark.activity;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.lib.app.AppActivityManager;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragmentActivity;
import com.xcoder.smartpark.moudel.eventbus.CloseBus;
import com.xcoder.smartpark.service.MainService;
import com.xcoder.smartpark.util.AppUpdate;
import com.xcoder.smartpark.view.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by xcoder_xz on 2016/12/5 0005.
 * 主界面
 */
@ContentView(R.layout.main_activity)
public class MainActivity extends BaseFragmentActivity {

    @Injection
    MainService ms;//注入MainService对象

    @Injection
    MainView mv;

    @Override
    public void createActivity() {
        EventBus.getDefault().register(this);
        ms.init(this);
        //检查app是否需要更新
        new AppUpdate(this).getVersion(0);

//        String s=null;
//        Double.valueOf(s);
    }



    @OnClick({R.id.main_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_other:
                //弹出角色框
                if(mv.main_other_bt.getVisibility()==View.GONE){
                    mv.main_other_bt.setVisibility(View.VISIBLE);
                    mv.main_other.setImageResource(R.drawable.use_mian_imagebt_no);
                }else{
                    mv.main_other_bt.setVisibility(View.GONE);
                    mv.main_other.setImageResource(R.drawable.use_mian_imagebt);
                }
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    /**
     * 两次按下，才退出app
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppActivityManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
            finish();
            System.exit(0);
        }
    }

    /**
     * 当用户退出当前登陆时所需要的做的操作
     *
     * @param close
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CloseBus close) {
        if (close.getClose()) {
            this.finishBase();
            this.finish();
        }
    }

    ;

    /**
     * 关闭activity要做的
     */
    @Override
    public void closeActivity() {
        EventBus.getDefault().unregister(this);
    }
}

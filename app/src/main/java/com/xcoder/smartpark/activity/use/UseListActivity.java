package com.xcoder.smartpark.activity.use;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.activity.HtmlActivity;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.network.AppNet;
import com.xcoder.smartpark.service.use.UseListService;
import com.xcoder.smartpark.view.use.UseListView;

/**
 * Created by xcoder-xz on 2017/3/22 0022.
 * 用车
 */

@ContentView(R.layout.use_list_activity)
public class UseListActivity extends BaseActivity {

    @Injection
    UseListService uls;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        uls.init(this);
    }

    @OnClick({R.id.use_list_black})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.use_list_black:
                finish();
                break;
        }
    }

    @Override
    public void closeActivity() {
        //清除此对象
        if (uls.refreshAndload != null) {
            uls.refreshAndload.onDestroy();
            uls.refreshAndload = null;
        }
    }
}

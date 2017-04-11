package com.xcoder.smartpark.activity.other;

import android.os.Bundle;
import android.view.View;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.annotation.event.OnClick;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.other.PatrolManService;

/**
 * Created by jiangkun on 16/12/30.
 * 巡更员
 */
@ContentView(R.layout.other_patrolman_main)
public class PatrolManActivity extends BaseActivity {

    @Injection
    private PatrolManService patrolManService;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        patrolManService.init(this);
    }

    @Override
    public void closeActivity() {

    }


    @OnClick({R.id.other_patrolman_black})
    public void Onclick(View view) {
        switch (view.getId()) {
            case R.id.other_patrolman_black :
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        patrolManService.startLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        patrolManService.stopLoad();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        patrolManService.stopLoad();
    }
}

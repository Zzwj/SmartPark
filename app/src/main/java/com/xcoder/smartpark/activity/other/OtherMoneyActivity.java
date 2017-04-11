package com.xcoder.smartpark.activity.other;

import android.os.Bundle;

import com.xcoder.lib.annotation.ContentView;
import com.xcoder.lib.annotation.Injection;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseActivity;
import com.xcoder.smartpark.service.other.OtherMoneyService;

/**
 * Created by jiangkun on 17/2/14.
 * 资产盘点
 */

@ContentView(R.layout.other_other_money_main)
public class OtherMoneyActivity extends BaseActivity {

    @Injection
    private OtherMoneyService otherMoneyService;

    @Override
    public void onXCoderCreate(Bundle savedInstanceState) {
        otherMoneyService.init(this);
    }

    @Override
    public void closeActivity() {

    }
}

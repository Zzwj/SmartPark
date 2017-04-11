package com.xcoder.smartpark.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.xcoder.lib.annotation.Injection;
import com.xcoder.lib.injection.ViewUtils;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.app.base.BaseFragment;
import com.xcoder.smartpark.service.LinkmanService;

/**
 * Created by xcoder_xz on 2017/3/22 0022.
 * 联系人
 */

public class LinkmanFragment extends BaseFragment{
    @Injection
    LinkmanService ls;
    private View view;

    @Override
    public View initLayout(LayoutInflater inflater) {
        if (view == null) {
            view = inflater.inflate(R.layout.linkman_main_fragment, null);
        }
        ViewUtils.inject(this, view);//这一句相当于初始化view了
        return view;
    }

    @Override
    public void closeFragment() {

    }
}

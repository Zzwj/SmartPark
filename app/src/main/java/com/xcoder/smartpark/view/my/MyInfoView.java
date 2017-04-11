package com.xcoder.smartpark.view.my;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcoder.lib.annotation.ViewInject;
import com.xcoder.smartpark.R;
import com.xcoder.smartpark.widget.view.CircleImageView;

/**
 * Created by jiangkun on 16/12/16.
 */

public class MyInfoView {

    @ViewInject(R.id.my_myinfo_submit_rl)
    public RelativeLayout my_myinfo_submit_rl;

    @ViewInject(R.id.my_myinfo_gender_rb)
    public RadioGroup my_myinfo_gender_rb;

    @ViewInject(R.id.my_myinfo_header_iv)
    public CircleImageView my_myinfo_header_iv;

    @ViewInject(R.id.my_myinfo_company_rl)
    public RelativeLayout my_myinfo_company_rl;

    @ViewInject(R.id.my_myinfo_department_rl)
    public RelativeLayout my_myinfo_department_rl;

    @ViewInject(R.id.my_myinfo_job_rl)
    public RelativeLayout my_myinfo_job_rl;

    @ViewInject(R.id.my_myinfo_company_tv)
    public TextView my_myinfo_company_tv;

    @ViewInject(R.id.my_myinfo_department_tv)
    public TextView my_myinfo_department_tv;

    @ViewInject(R.id.my_myinfo_job_tv)
    public TextView my_myinfo_job_tv;

    @ViewInject(R.id.my_myinfo_choose_lv)
    public ListView my_myinfo_choose_lv;

    @ViewInject(R.id.my_myinfo_number_et)
    public EditText my_myinfo_number_et;

    @ViewInject(R.id.my_myinfo_name_et)
    public EditText my_myinfo_name_et;

    @ViewInject(R.id.my_myinfo_rb_male)
    public RadioButton my_myinfo_rb_male;

    @ViewInject(R.id.my_myinfo_rb_female)
    public RadioButton my_myinfo_rb_female;

    @ViewInject(R.id.my_myinfo_showcompany_tv)
    public TextView my_myinfo_showcompany_tv;

    @ViewInject(R.id.my_myinfo_showdepartment_tv)
    public TextView my_myinfo_showdepartment_tv;

    @ViewInject(R.id.my_myinfo_showjob_tv)
    public TextView my_myinfo_showjob_tv;

    @ViewInject(R.id.my_myinfo_id_rl)
    public RelativeLayout my_myinfo_id_rl;

}

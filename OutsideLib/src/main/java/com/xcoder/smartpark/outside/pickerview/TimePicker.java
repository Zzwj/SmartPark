package com.xcoder.smartpark.outside.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xcoder.smartpark.outside.R;
import com.xcoder.smartpark.outside.pickerview.view.BasePickerView;
import com.xcoder.smartpark.outside.pickerview.view.WheelTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xcoder_xz on 2016/12/17 0017.
 * 时间选择控件
 */

public class TimePicker extends BasePickerView implements
        View.OnClickListener {


    WheelTime wheelTime;
    private TextView tvCancel;//取消
    private TextView tvConfirm;//确定
    private OnTimeSelectListener timeSelectListener;
    private View timepickerview;

    /**
     * @param context
     * @param type    TimePickerView.Type.ALL
     */
    public TimePicker(Context context, TimePickerView.Type type) {
        super(context);
        try {
            LayoutInflater.from(context).inflate(R.layout.time_picker,
                    contentContainer);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        tvCancel = (TextView) findViewById(R.id.time_picker_cancel);
        tvConfirm = (TextView) findViewById(R.id.time_picker_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        //
        timepickerview = findViewById(R.id.timepicker);
        wheelTime = new WheelTime(timepickerview, type);
        //设置时间控件参数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * @param cyclic
     * @see设置是否循环滚动
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    /**
     * @param startYear
     * @param endYear
     * @see设置开始年份，和结束年份
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.time_picker_cancel) {
            dismiss();
        } else if (v.getId() == R.id.time_picker_confirm) {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
        }
    }

    public interface OnTimeSelectListener {
        public void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

}

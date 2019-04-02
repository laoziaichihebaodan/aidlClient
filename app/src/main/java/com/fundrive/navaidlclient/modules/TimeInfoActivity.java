package com.fundrive.navaidlclient.modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeInfoActivity extends BaseActivity implements OnDateSetListener {

    @BindView(R.id.sp_set_mode)
    Spinner spSetMode;
    @BindView(R.id.sp_time_mode)
    Spinner spTimeMode;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    TimePickerDialog mDialogAll;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private int[] timeModes = {1, 2};
    private int[] timeTypes = {1, 2, 3};

    private int timeType = 0;
    private int timeMode = 0;

    private String message;
    private Dialog sendDialog;

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Calendar instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_info);
        ButterKnife.bind(this);
        tvTitle.setText("时间信息");
        spSetMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeType = timeTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spTimeMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeMode = timeModes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("时间选择")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + 10L * 365 * 24 * 60 * 60 * 1000)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sendDialog != null && sendDialog.isShowing()){
            sendDialog.cancel();
        }
    }

    @OnClick({R.id.btn_select, R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.btn_commit:
                makeJson(timeType, timeMode, year, month, day, hour, minute, second);
                showSendDialog();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void showSendDialog(){
        sendDialog = new AlertDialog.Builder(this).create();
        sendDialog.show();
        sendDialog.setContentView(R.layout.send_dialog_bg);
        TextView tv_send = sendDialog.findViewById(R.id.tv_send);
        tv_send.setText(message);
        tv_send.setTextIsSelectable(true);
    }

    private void makeJson(int timeType, int timeMode, int year, int month, int day, int hour, int minute, int second) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject timeJson = new JSONObject();
        try {
            timeJson.put("year", year);
            timeJson.put("month", month);
            timeJson.put("date", day);
            timeJson.put("hour", hour);
            timeJson.put("minute", minute);
            timeJson.put("second", second);

            jsonObject.put("timeType", timeType);
            jsonObject.put("timeMode", timeMode);
            jsonObject.put("timeValue", timeJson);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_TIME_INFOMATION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        instance = Calendar.getInstance();
        instance.setTime(new Date(millseconds));
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH) + 1;
        day = instance.get(Calendar.DAY_OF_MONTH);
        hour = instance.get(Calendar.HOUR_OF_DAY);
        minute = instance.get(Calendar.MINUTE);
        second = instance.get(Calendar.SECOND);

        tvTime.setText("" + year + "-" + month + "-" + day + " " + hour + ":"
                + minute + ":" + second);

    }


}

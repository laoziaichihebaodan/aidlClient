package com.fundrive.navaidlclient.modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MutimediaInformationActivity extends BaseActivity {

    @BindView(R.id.sp_source_)
    Spinner spSource;
    @BindView(R.id.et_misc)
    EditText etMisc;
    @BindView(R.id.sp_radio_band)
    Spinner spRadioBand;
    @BindView(R.id.np_int)
    NumberPicker npInt;
    @BindView(R.id.np_dec)
    NumberPicker npDec;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int sourceType;
    private int rand;
    private String miscName;

    private String message;
    private Dialog sendDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutimedia_information);
        ButterKnife.bind(this);
        tvTitle.setText("多媒体信息");
        npInt.setMaxValue(1200);
        npInt.setMinValue(70);
        npDec.setMinValue(0);
        npDec.setMaxValue(9);
        npInt.setValue(70);
        npDec.setValue(0);

        spSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spRadioBand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rand = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sendDialog != null && sendDialog.isShowing()){
            sendDialog.cancel();
        }
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.btn_commit:
                miscName = etMisc.getText().toString().trim();
                makeJson();
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

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject audioJson = new JSONObject();
        JSONObject radioJson = new JSONObject();
        JSONObject frequenJson = new JSONObject();
        try {
            frequenJson.put("integerBit", npInt.getValue());
            frequenJson.put("decimalBit", npDec.getValue());

            radioJson.put("rand", rand);
            radioJson.put("frequency", frequenJson);

            audioJson.put("sourceType", sourceType);
            audioJson.put("trafficRadio", radioJson);
            audioJson.put("musicName", miscName);

            jsonObject.put("iaAudio", audioJson);
            jsonObject.put("iaVideo", "");

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

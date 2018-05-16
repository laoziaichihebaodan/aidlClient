package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControlMutimediaActivity extends BaseActivity {

    @BindView(R.id.sp_op_type)
    Spinner spOpType;
    @BindView(R.id.sp_app_type)
    Spinner spAppType;
    @BindView(R.id.sp_play_mode)
    Spinner spPlayMode;
    @BindView(R.id.sp_audio_src)
    Spinner spAudioSrc;
    @BindView(R.id.et_frequenc_int)
    EditText etFrequencInt;
    @BindView(R.id.et_frequenc_dec)
    EditText etFrequencDec;
    @BindView(R.id.et_singer)
    EditText etSinger;
    @BindView(R.id.et_misc)
    EditText etMisc;
    @BindView(R.id.et_tag)
    EditText etTag;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private int opType = 1;//操作类型 上一曲
    private int appType = 1;//多媒体应用类型 收音机
    private int playMode = 2;//播放模式 顺序播放
    private int audioSrc = 2;//USB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_mutimedia);
        ButterKnife.bind(this);
        spOpType.setSelection(1);
        spOpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAppType.setSelection(1);
        spAppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spPlayMode.setSelection(2);
        spPlayMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spAudioSrc.setSelection(2);
        spAudioSrc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                audioSrc = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        makeJson();
    }


    //组装json
    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject innerJson = new JSONObject();
        try {
            jsonObject.put("iaMultimediaOpType", opType);
            switch (opType) {
                case 4:
                case 5:
                    jsonObject.put("iaMultiMediaApp", appType);
                    break;
                case 6:
                case 7:
                    String strInteger = etFrequencInt.getText().toString().trim();
                    String strDecimal = etFrequencDec.getText().toString().trim();

                    int integer = Integer.decode(strInteger);
                    int decimal = Integer.decode(strDecimal);

                    innerJson.put("integer", integer);
                    innerJson.put("decimal", decimal);
                    jsonObject.put("iaFrequency", innerJson);
                    break;
                case 9:
                case 10:
                    String tag = etTag.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    innerJson.put("tag", tag);
                    innerJson.put("number", phone);
                    jsonObject.put("iaContactionInfo", innerJson);
                    break;
                case 11:
                case 12:
                    jsonObject.put("iaAudioSrc", audioSrc);
                    String singer = etSinger.getText().toString().trim();
                    String music = etMisc.getText().toString().trim();
                    innerJson.put("singerName", singer);
                    innerJson.put("musicName", music);
                    jsonObject.put("iaMusicInfo", innerJson);
                    break;
                case 13:
                    jsonObject.put("iaPlayMode", playMode);
                    break;
            }
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA);
            cmdJson.put(Constant.JSON_KEY, jsonObject);
            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

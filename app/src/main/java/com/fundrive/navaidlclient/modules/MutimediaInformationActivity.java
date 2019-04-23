package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.PageInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MutimediaInformationActivity extends BaseActivity {

    @BindView(R.id.sp_source_)
    Spinner spSource;
    private int spSource_index;
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

    private PageInfoBean.Lists protocolData;
    private int lists_index;
    private JSONObject obj_sendJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutimedia_information);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");
        lists_index = intent.getIntExtra("lists_index",0);

        tvTitle.setText("多媒体信息");
        npInt.setMaxValue(1200);
        npInt.setMinValue(70);
        npDec.setMinValue(0);
        npDec.setMaxValue(9);
//        npInt.setValue(70);
//        npDec.setValue(0);

        if (protocolData.getSendJson()!=null && !protocolData.getSendJson().isEmpty()){
            try {
                obj_sendJson = new JSONObject(protocolData.getSendJson());

                JSONObject obj_iaAudio = obj_sendJson.getJSONObject("iaAudio");
                spSource_index = obj_iaAudio.getInt("sourceType");
                spSource.setSelection(spSource_index);
                etMisc.setText(obj_iaAudio.getString("musicName"));
                etMisc.setSelection(obj_iaAudio.getString("musicName").length());

                JSONObject obj_trafficRadio = obj_iaAudio.getJSONObject("trafficRadio");
                spRadioBand.setSelection(obj_trafficRadio.getInt("rand"));

                JSONObject obj_frequency = obj_trafficRadio.getJSONObject("frequency");
                npInt.setValue(obj_frequency.getInt("integerBit"));
                npDec.setValue(obj_frequency.getInt("decimalBit"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        etMisc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (obj_sendJson != null){
                        obj_sendJson.getJSONObject("iaAudio").put("musicName",s.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        spSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceType = position;
                if (obj_sendJson != null){
                    try {
                        obj_sendJson.getJSONObject("iaAudio").put("iaAudio",sourceType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spRadioBand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rand = position;
                if (obj_sendJson != null){
                    try {
                        obj_sendJson.getJSONObject("iaAudio").getJSONObject("trafficRadio").put("rand",rand);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (obj_sendJson != null) {
                obj_sendJson.getJSONObject("iaAudio").getJSONObject("trafficRadio").getJSONObject("frequency").put("integerBit",npInt.getValue());
                obj_sendJson.getJSONObject("iaAudio").getJSONObject("trafficRadio").getJSONObject("frequency").put("decimalBit",npDec.getValue());
                obj_sendJson.put("iaVideo","");
                protocolData.setSendJson(obj_sendJson.toString());
            } else {
                JSONObject obj_sendJson_m = new JSONObject();
                JSONObject audioJson_m = new JSONObject();
                JSONObject radioJson_m = new JSONObject();
                JSONObject frequenJson_m = new JSONObject();

                frequenJson_m.put("integerBit", npInt.getValue());
                frequenJson_m.put("decimalBit", npDec.getValue());

                radioJson_m.put("rand", rand);
                radioJson_m.put("frequency", frequenJson_m);

                audioJson_m.put("sourceType", sourceType);
                audioJson_m.put("trafficRadio", radioJson_m);
                audioJson_m.put("musicName", etMisc.getText().toString().trim());

                obj_sendJson_m.put("iaAudio", audioJson_m);
                obj_sendJson_m.put("iaVideo", "");

                protocolData.setSendJson(obj_sendJson_m.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Resource.pageInfoBean.getLists().remove(lists_index);
        Resource.pageInfoBean.getLists().add(lists_index,protocolData);
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.btn_commit:
                miscName = etMisc.getText().toString().trim();
                makeJson();
                showSendDialog(message);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
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

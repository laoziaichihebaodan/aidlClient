package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.JsonKey;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TmcActivity extends BaseActivity {

    @BindView(R.id.sp_boadcast_type)
    Spinner spBoadcastType;
    @BindView(R.id.et_road)
    EditText etRoad;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_direct)
    EditText etDirect;
    @BindView(R.id.et_radius)
    EditText etRadius;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int boadcastType;
    private String roadName = "";
    private String city = "";
    private int radius = 1500;
    private String direction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmc);
        ButterKnife.bind(this);
        tvTitle.setText("Tmc查询");
        spBoadcastType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boadcastType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                roadName = etRoad.getText().toString().trim();
                city = etCity.getText().toString().trim();
                direction = etDirect.getText().toString().trim();
                String strRadius = etRadius.getText().toString().trim();
                if (!TextUtils.isEmpty(strRadius) && TextUtils.isDigitsOnly(strRadius)) {
                    radius = Integer.valueOf(strRadius);
                }
                makeJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void makeJson(){
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("broadcastType",boadcastType);
            jsonObject.put("roadName", roadName);
            jsonObject.put("city", city);
            jsonObject.put("direction", direction);
            jsonObject.put("radius", radius);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_TMC_BROADCAST);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

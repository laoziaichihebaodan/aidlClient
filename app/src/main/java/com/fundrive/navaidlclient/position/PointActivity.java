package com.fundrive.navaidlclient.position;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.modules.RouteByConditionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointActivity extends AppCompatActivity {

    @BindView(R.id.et_poi_type)
    EditText etPoiType;
    @BindView(R.id.et_poi_long)
    EditText etPoiLong;
    @BindView(R.id.et_poi_lat)
    EditText etPoiLat;
    @BindView(R.id.et_dis_long)
    EditText etDisLong;
    @BindView(R.id.et_dis_lat)
    EditText etDisLat;
    @BindView(R.id.et_poi_id)
    EditText etPoiId;
    @BindView(R.id.et_child_num)
    EditText etChildNum;
    @BindView(R.id.et_compound_id)
    EditText etCompoundId;
    @BindView(R.id.et_poi_name)
    EditText etPoiName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_region_name)
    EditText etRegionName;
    @BindView(R.id.et_type_name)
    EditText etTypeName;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String strPoiType;
    private int poiType;
    private long longitude;
    private long latitude;
    private long disLong;
    private long disLat;
    private long poiId;
    private int childNum;
    private int compoundId;
    private String poiName;
    private String poiAddress;
    private String poiPhone;
    private String region;
    private String typeName;
    public String TAG = "PointActivity";
    private JSONObject point;
    private JSONObject poiJson;
    private JSONObject disPoiJson;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);
        tvTitle.setText("位置信息");
        intent = getIntent();
        String str_point = intent.getStringExtra("point");
        if (!str_point.isEmpty()){
            try {
                point = new JSONObject(str_point);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            switch (intent.getIntExtra("json", 1)) {
                case 0:
                    point = RouteByConditionActivity.startPoint;
                    break;
                case 1:
                    point = RouteByConditionActivity.endPoint;
                    break;
                case 2:
                    point = RouteByConditionActivity.wayPoint1;
                    break;
                case 3:
                    point = RouteByConditionActivity.wayPoint2;
                    break;
                case 4:
                    point = RouteByConditionActivity.wayPoint3;
                    break;
            }
        }

        try {
            poiJson = point.getJSONObject("iaPoiPos");
            disPoiJson = point.getJSONObject("iaPoiDisPos");
            poiType = point.getInt("iaPoiType");
            longitude = poiJson.getLong("longitude");
            latitude = poiJson.getLong("latitude");
            disLong = disPoiJson.getLong("longitude");
            disLat = disPoiJson.getLong("latitude");
            poiId = point.getLong("iaPoiId");
            childNum = point.getInt("iaChildPoiNum");
            compoundId = point.getInt("iaCompoundId");
            poiName = point.getString("iaPoiName");
            poiAddress = point.getString("iaPoiAddress");
            poiPhone = point.getString("iaPoiPhone");
            region = point.getString("iaRegionName");
            typeName = point.getString("iaPoiTypeName");

            etPoiType.setText(poiType + "");
            etPoiType.setSelection(String.valueOf(poiType+"").length());
            etPoiLong.setText(longitude + "");
            etPoiLat.setText(latitude + "");
            etDisLong.setText("" + disLong);
            etPoiLat.setText("" + disLat);
            etPoiId.setText("" + poiId);
            etChildNum.setText("" + childNum);
            etCompoundId.setText("" + compoundId);
            etPoiName.setText(poiName);
            etAddress.setText(poiAddress);
            etPhone.setText(poiPhone);
            etRegionName.setText(region);
            etTypeName.setText(typeName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_commit:
                makeJson();
                point = null;
                finish();
                break;
            case R.id.btn_return:
                makeJson();
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            makeJson();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void makeJson() {
        strPoiType = etPoiType.getText().toString().trim();
        poiType = Integer.decode(strPoiType);
        longitude = Long.decode(etPoiLong.getText().toString().trim());
        latitude = Long.decode(etPoiLat.getText().toString().trim());
        disLong = Long.decode(etDisLong.getText().toString().trim());
        disLat = Long.decode(etDisLat.getText().toString().trim());
        poiId = Long.decode(etPoiId.getText().toString().trim());
        childNum = Integer.decode(etChildNum.getText().toString().trim());
        compoundId = Integer.decode(etCompoundId.getText().toString().trim());
        poiName = etPoiName.getText().toString().trim();
        poiAddress = etAddress.getText().toString().trim();
        poiPhone = etPhone.getText().toString().trim();
        region = etRegionName.getText().toString().trim();
        typeName = etTypeName.getText().toString().trim();

        point = new JSONObject();
        poiJson = new JSONObject();
        disPoiJson = new JSONObject();

        try {
            poiJson.put("longitude", longitude);
            poiJson.put("latitude", latitude);

            disPoiJson.put("longitude", disLong);
            disPoiJson.put("latitude", disLat);

            point.put("iaPoiType", poiType);
            point.put("iaPoiPos", poiJson);
            point.put("iaPoiDisPos", disPoiJson);
            point.put("iaPoiId", poiId);
            point.put("iaChildPoiNum", childNum);
            point.put("iaCompoundId", compoundId);
            point.put("iaPoiName", poiName);
            point.put("iaPoiAddress", poiAddress);
            point.put("iaPoiPhone", poiPhone);
            point.put("iaRegionName", region);
            point.put("iaPoiTypeName", typeName);


            String message = point.toString();

            Intent intent_resulr = new Intent();
            intent_resulr.putExtra("message",message);
            setResult(2,intent_resulr);

            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

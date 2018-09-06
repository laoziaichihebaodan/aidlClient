package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateFavActivity extends BaseActivity {

    @BindView(R.id.sp_type)
    Spinner spType;
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

    private int favType = 1;
    private int cmd  = Constant.IA_CMD_UPDATE_FAVORITE_POINT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fav);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.hasExtra("cmd")) {
            cmd = intent.getIntExtra("cmd", Constant.IA_CMD_UPDATE_FAVORITE_POINT);
        }
        spType.setSelection(1);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                favType = position;
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
    private void makeJson() {
        String strPoiType = etPoiType.getText().toString().trim();
        int poiType = Integer.decode(strPoiType);
        long longtitude = Long.decode(etPoiLong.getText().toString().trim());
        long latitude = Long.decode(etPoiLat.getText().toString().trim());
        long disLong = Long.decode(etDisLong.getText().toString().trim());
        long disLat = Long.decode(etDisLat.getText().toString().trim());
        long poiId = Long.decode(etPoiId.getText().toString().trim());
        int childNum = Integer.decode(etChildNum.getText().toString().trim());
        int compoundId = Integer.decode(etCompoundId.getText().toString().trim());
        String poiName = etPoiName.getText().toString().trim();
        String poiAddress = etAddress.getText().toString().trim();
        String poiPhone = etPhone.getText().toString().trim();
        String region = etRegionName.getText().toString().trim();
        String typeName = etTypeName.getText().toString().trim();

        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject favorJson = new JSONObject();
        JSONObject poiJson = new JSONObject();
        JSONObject disPoiJson = new JSONObject();

        try {
            poiJson.put("longitude", longtitude);
            poiJson.put("latitude", latitude);

            disPoiJson.put("longitude", disLong);
            disPoiJson.put("latitude", disLat);

            favorJson.put("iaPoiType", poiType);
            favorJson.put("iaPoiPos", poiJson);
            favorJson.put("iaPoiDisPos", disPoiJson);
            favorJson.put("iaPoiId", poiId);
            favorJson.put("iaChildPoiNum", childNum);
            favorJson.put("iaCompoundId", compoundId);
            favorJson.put("iaPoiName", poiName);
            favorJson.put("iaPoiAddress", poiAddress);
            favorJson.put("iaPoiPhone", poiPhone);
            favorJson.put("iaRegionName", region);
            favorJson.put("iaPoiTypeName", typeName);

            jsonObject.put("iaFavType", favType);
            jsonObject.put("favContent", favorJson);

            cmdJson.put(Constant.CMD_KEY, cmd);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetPonitInfoActivity extends BaseActivity {

    @BindView(R.id.et_long)
    EditText etLong;
    @BindView(R.id.et_lat)
    EditText etLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ponit_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        makeJson();
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            String strLong = etLong.getText().toString().trim();
            Long longitude = Long.decode(strLong);
            jsonObject.put("longitude", longitude);
            String strNum = etLat.getText().toString().trim();
            Long latitude = Long.decode(strNum);
            jsonObject.put("latitude",latitude);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_GET_SPECIFIC_POINT_INFO);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

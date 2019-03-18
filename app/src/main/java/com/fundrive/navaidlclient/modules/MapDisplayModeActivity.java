package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置白天黑夜模式
 */
public class MapDisplayModeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display_mode);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_day, R.id.btn_night,R.id.btn_auto,R.id.btn_get_mode,R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_day://白天
                makeJson(1);
                break;
            case R.id.btn_night://黑夜
                makeJson(2);
                break;
            case R.id.btn_auto:
                makeJson(3);
                break;
            case R.id.btn_get_mode:
                makeJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        try {
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_GET_MAP_DISPLAY_MODE);
            cmdJson.put(Constant.JSON_KEY, "");

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makeJson(int num) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mode", num);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_MAP_DISPLAY_MODE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

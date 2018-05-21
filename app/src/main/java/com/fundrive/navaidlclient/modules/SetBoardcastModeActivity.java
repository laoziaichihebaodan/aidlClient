package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetBoardcastModeActivity extends BaseActivity {

    @BindView(R.id.sp_mode)
    Spinner spMode;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_boardcast_mode);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        if (mode == 0) {
            Toast.makeText(this, "请选择导航播报模式", Toast.LENGTH_SHORT).show();
            return;
        }
        makeJson();
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("boardcastMode", mode);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_BOARDCAST_MODE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

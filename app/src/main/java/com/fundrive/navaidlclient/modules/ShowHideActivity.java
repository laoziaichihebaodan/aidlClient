package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowHideActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.switch_mode)
    Switch switchMode;
    private int state = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hide);
        ButterKnife.bind(this);
        switchMode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (switchMode.isChecked()) {
            state = 1;
        } else {
            state = 2;
        }
        makeJson();

    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("operationType", state);
            cmdJson.put(Constant.CMD_KEY,Constant.IA_CMD_SHOW_OR_HIDE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

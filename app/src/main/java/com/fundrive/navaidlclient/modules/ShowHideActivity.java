package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowHideActivity extends BaseActivity {

    @BindView(R.id.switch_mode)
    Switch switchMode;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int state = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hide);
        ButterKnife.bind(this);
        tvTitle.setText("显示隐藏NavAPP");
    }

    @OnClick({R.id.switch_mode, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.switch_mode:
                if (switchMode.isChecked()) {
                    state = 1;
                } else {
                    state = 2;
                }
                makeJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
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

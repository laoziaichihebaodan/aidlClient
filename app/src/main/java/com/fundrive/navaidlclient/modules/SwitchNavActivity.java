package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchNavActivity extends BaseActivity {


    @BindView(R.id.switch_on_off)
    Switch switchOnOff;
    @BindView(R.id.switch_real_virtual)
    Switch switchRealVirtual;
    @BindView(R.id.tv_nav)
    TextView tvNav;
    @BindView(R.id.tv_real)
    TextView tvReal;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private int opType = 1;
    private int guideType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_nav);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.switch_on_off, R.id.switch_real_virtual, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_on_off:
                if (switchOnOff.isChecked()) {
                    opType = 2;
                    tvNav.setText("关闭导航");
                } else {
                    opType = 1;
                    tvNav.setText("开启导航");
                }
                break;
            case R.id.switch_real_virtual:
                if (switchRealVirtual.isChecked()) {
                    guideType = 2;
                    tvReal.setText("真实导航");
                } else {
                    guideType = 1;
                    tvReal.setText("模拟导航");
                }
                break;
            case R.id.btn_commit:
                makeJson();
                break;
        }
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("operationType", opType);
            jsonObject.put("guideType", guideType);
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

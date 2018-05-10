package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteConditionActivity extends BaseActivity {

    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.sp_route_rule)
    Spinner spRouteRule;
    @BindView(R.id.sp_switch)
    Spinner spSwitch;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    //避让类型取值范围
    private int[] types = {0x00, 0x10 | 0x20, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
    //避让规则取值范围
    private int[] rules = {0, 1, 2, 3, 4};
    //开启关闭
    private boolean enabled = false;

    private int type;
    private int rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_condition);
        ButterKnife.bind(this);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spRouteRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rule = rules[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSwitch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) {
                    enabled = false;
                } else {
                    enabled = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        makeJson(type,rule,enabled);
    }

    private void makeJson(int type, int rule, boolean on) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("avoidType", type);
            jsonObject.put("routeRule", rule);
            jsonObject.put("operation", on);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_ROUTE_CONDITION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

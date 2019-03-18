package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectRouteGuideActivity extends BaseActivity {

    @BindView(R.id.sp_route)
    Spinner spRoute;
    @BindView(R.id.sp_mode)
    Spinner spMode;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int navMode;
    private int routeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_route_guide);
        ButterKnife.bind(this);
        tvTitle.setText("指定路线导航");
        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                navMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                routeNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
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
            jsonObject.put("routeNumber", routeNum);
            jsonObject.put("guideType", navMode);
            cmdJson.put(Constant.CMD_KEY,Constant.IA_CMD_START_GUIDING_WITH_ROUTE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

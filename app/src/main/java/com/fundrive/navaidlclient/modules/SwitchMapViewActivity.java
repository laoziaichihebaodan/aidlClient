package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwitchMapViewActivity extends BaseActivity {

    @BindView(R.id.np_id)
    NumberPicker npId;

    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.switch_mode)
    Spinner switchMode;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int mode = 1;
    private int screenId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_map_view);
        ButterKnife.bind(this);
        tvTitle.setText("切换地图模式");
        npId.setValue(1);
        npId.setMaxValue(3);
        npId.setMinValue(0);
        switchMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = position;
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
                screenId = npId.getValue();
                makeJson(screenId, mode);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    //组装json
    private void makeJson(int id, int mode) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("screenId", id);
            jsonObject.put("viewMode", mode);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_MAP_VIEW_MODE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

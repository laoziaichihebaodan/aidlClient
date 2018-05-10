package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScaleMapActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.switch_mode)
    Switch switchMode;
    @BindView(R.id.tv_scal)
    TextView tvScal;
    @BindView(R.id.num_pick)
    NumberPicker numPick;
    private int opType = 1;
    private int screenId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_map);
        ButterKnife.bind(this);
        switchMode.setOnClickListener(this);
        numPick.setMaxValue(3);
        numPick.setMinValue(0);
        numPick.setValue(1);

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        screenId = numPick.getValue();
        makeJson();
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("operationType", opType);
            jsonObject.put("screenId", screenId);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_ZOOM_MAP);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (switchMode.isChecked()) {
            opType= 2;
            tvScal.setText("缩小");
        } else {
            opType = 1;
            tvScal.setText("放大");
        }
    }
}
